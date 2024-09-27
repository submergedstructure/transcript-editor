(ns com.submerged-structure.mutations
  "Client-side mutations.
   
   'Server-side' mutations could normally be also defined here, only with
   `#?(:clj ...)` but here even the 'server' runs in the browser so we must
   define them in another ns, which we do in `...pathom`."
  (:require
   [com.fulcrologic.fulcro.mutations :refer [defmutation]]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
   [com.fulcrologic.fulcro.data-fetch :as df]

   [com.submerged-structure.components.transcript :as transcript]))

(defn get-current-transcript-id-from-state
  [state-deref]
  (get-in state-deref [:root/current-transcript 1]))

(defn get-current-segment-word-tree-from-state
  [state-deref]
  (get-in
   (fdn/db->tree
    [#:root{:current-transcript
            [:transcript/id
             #:transcript{:segments
                          [:segment/id :segment/start :segment/end
                           #:segment{:words [:word/id :word/start :word/end]}]}]}]
    state-deref state-deref)
   [:root/current-transcript :transcript/segments]))

(defn words-with-unique-time-stamps [segment-word-tree]
  (->>
   segment-word-tree
   (mapcat :segment/words)
   (filter #(not= (:word/start %) (:word/end %))))) ;; where start times are the same take the last one.


(defn get-current-translation-tree [state-deref]
  (fdn/db->tree
   [{:root/current-transcript [#:transcript{:segments
                                            [#:segment{:translations [:translation/id :translation/start :translation/end :translation/lang :translation/visible?]}]}]}] ; or any component
   state-deref
   state-deref))

(defn get-all-translations-in-current-transcript [state-deref]
  (mapcat :segment/translations (get-in (get-current-translation-tree state-deref) [:root/current-transcript :transcript/segments])))

(defn languages-in-current-transcript [state-deref]
  (set (map :translation/lang (get-all-translations-in-current-transcript state-deref))))

(defn language-translations-in-current-transcript
  "Reurn all translations in `lang` in current transcript."
  [state-deref lang]
  (filter #(= (:translation/lang %) lang) (get-all-translations-in-current-transcript state-deref)))


(defn changes-to-make-to-transcript-keys-in-local-db-when-time-changes-related-to-current-translations
  "Return the id of current word to highlight or nil if no word to highlight.
   And return the period for t outside which this function should be called again."
  [state-deref t]
  (let [transcript-duration (get-in state-deref [:transcript/id (get-current-transcript-id-from-state state-deref) :transcript/duration])
        changes-for-each-lang (for [lang (languages-in-current-transcript state-deref)
                                    :let [translations-in-lang (language-translations-in-current-transcript state-deref lang)
                                          translations-started-before-t-in-lang (filter #(<= (:translation/start %) t) translations-in-lang)
                                          translations-starting-after-t-in-lang (filter #(> (:translation/start %) t) translations-in-lang)
                                          last-started-translation-in-lang (last translations-started-before-t-in-lang)
                                          next-translation-in-lang (first translations-starting-after-t-in-lang)]]
                                (cond (and last-started-translation-in-lang (<= t (:translation/end last-started-translation-in-lang))) ;; currently in translation
                                      {:transcript/current-translation [:translation/id (:translation/id last-started-translation-in-lang)]
                                       :ui-period/start (:translation/start last-started-translation-in-lang)
                                       :ui-period/end (:translation/end last-started-translation-in-lang)}
                                      (and last-started-translation-in-lang next-translation-in-lang) ;; currently in pause after this translation
                                      {:transcript/current-translation [:translation/id nil]
                                       :ui-period/start (:translation/end last-started-translation-in-lang)
                                       :ui-period/end (:translation/start next-translation-in-lang)}
                                      last-started-translation-in-lang ;; currently in pause after last translation in transcript
                                      {:transcript/current-translation [:translation/id nil]
                                       :ui-period/start (:translation/end last-started-translation-in-lang)
                                       :ui-period/end transcript-duration};; if this is last translation then end of period is end of transcript
                                      next-translation-in-lang
                                      {:transcript/current-translation [:translation/id nil] ;; before first translation
                                       :ui-period/start 0
                                       :ui-period/end (:translation/start next-translation-in-lang)}
                                      :else ;; no timestamped translations in transcript!!
                                      {:transcript/current-translation [:translation/id nil]
                                       :ui-period/start 0
                                       :ui-period/end transcript-duration}))]
        {:transcript/current-translations (into [] (map :transcript/current-translation changes-for-each-lang))
         :ui-period/start (apply max (map :ui-period/start changes-for-each-lang)) ;; smallest period for which translations do not change
         :ui-period/end (apply min (map :ui-period/end changes-for-each-lang))}))

(comment 
  (def state-deref *1)
  (changes-to-make-to-transcript-keys-in-local-db-when-time-changes-related-to-current-translations state-deref 0.5)
  (languages-in-current-transcript state-deref))


(defn changes-to-make-to-transcript-keys-in-local-db-when-time-changes-related-to-current-word
  "Return the id of current word to highlight or nil if no word to highlight.
   And return the period for t outside which this function should be called again."
  [state-deref t]
  (let [transcript-id (get-current-transcript-id-from-state state-deref)
        transcript-duration (get-in state-deref [:transcript/id transcript-id :transcript/duration])
        
        segment-word-tree (get-current-segment-word-tree-from-state state-deref)
        
        all-words (words-with-unique-time-stamps segment-word-tree) 
        words-started-before-t (filter #(<= (:word/start %) t) all-words)
        words-starting-after-t (filter #(> (:word/start %) t) all-words)
        last-started-word (last words-started-before-t)
        next-word (first words-starting-after-t)
        
        segments-starting-before-t (filter #(<= (:segment/start %) t) segment-word-tree)
        segments-starting-after-t (filter #(> (:segment/start %) t) segment-word-tree)]
    (merge {:ui-player-controls/prev-word-start (:word/start (last (drop-last words-started-before-t)))
            :ui-player-controls/prev-segment-start (:segment/start (last (drop-last segments-starting-before-t)))

            :ui-player-controls/current-word-start (:word/start last-started-word)
            :ui-player-controls/current-segment-start (:segment/start (last segments-starting-before-t))
            
            :ui-player-controls/next-word-start (:word/start next-word)
            :ui-player-controls/next-segment-start (:segment/start (first segments-starting-after-t))}
           (cond
             (and last-started-word (<= t (:word/end last-started-word))) ;; currently in word
             {:transcript/current-word [:word/id (:word/id last-started-word)]
              :ui-period/start (:word/start last-started-word)
              :ui-period/end (:word/end last-started-word)}
             (and last-started-word next-word) ;; currently in pause after this word
             {:transcript/current-word [:word/id nil]
              :ui-period/start (:word/end last-started-word)
              :ui-period/end (:word/start next-word)}
             last-started-word ;; currently in pause after last word in transcript
             {:transcript/current-word [:word/id nil]
              :ui-period/start (:word/end last-started-word)
              :ui-period/end transcript-duration} ;; if this is last word then end of period is end of transcript
             next-word
             {:transcript/current-word [:word/id nil] ;; before first word
              :ui-period/start 0
              :ui-period/end (:word/start next-word)}
             :else ;; no timestamped words in transcript!!
             {:transcript/current-word [:word/id nil] ;; before first word
              :ui-period/start 0
              :ui-period/end transcript-duration}))))

(defn changes-to-make-to-transcript-keys-in-local-db-when-time-changes-related-to-current-word-and-translations
  "Return the id of current word to highlight or nil if no word to highlight.
   And return the period for t outside which this function should be called again."
  [state-deref t]
  (let [changes-related-to-translations (changes-to-make-to-transcript-keys-in-local-db-when-time-changes-related-to-current-translations state-deref t)
        changes-related-to-current-word (changes-to-make-to-transcript-keys-in-local-db-when-time-changes-related-to-current-word state-deref t)] 
   (merge
    changes-related-to-current-word
    changes-related-to-translations

    {:ui-period/start ((comp min :ui-period/start) changes-related-to-current-word changes-related-to-translations)
     :ui-period/end ((comp max :ui-period/end) changes-related-to-current-word)})))

(defmutation update-transcript-current-time
  "Sets the currently active word, if there is one, and also sets the start and end time of the time period that the word or pause covers."

  [{:transcript/keys [current-time]}]
  (action [{:keys [state]}]
          (let [transcript-id (get-current-transcript-id-from-state @state)
                transcript-keys-to-update (changes-to-make-to-transcript-keys-in-local-db-when-time-changes-related-to-current-word-and-translations @state current-time)
                last-current-translation-idents (get-in @state [:transcript/id transcript-id :transcript/current-translations])
                last-current-word-id (get-in @state [:transcript/id transcript-id :transcript/current-word 1])]
            (do
              (js/console.log "update-transcript-current-time" current-time last-current-word-id transcript-keys-to-update)
              (doall (map (fn [[k v]] (swap! state assoc-in (conj [:transcript/id transcript-id] k) v)) transcript-keys-to-update))
              ;; deactivate last translations
              (doall
               (map (fn [[_ last-translation-id]]
                      (when last-translation-id
                        (swap! state assoc-in [:translation/id last-translation-id :translation/active] false))) last-current-translation-idents))
              ;; activate new translations
              (doall
               (map (fn [[_ new-translation-id]]
                      (when new-translation-id
                        (swap! state assoc-in [:translation/id new-translation-id :translation/active] true)))
                    (get-in transcript-keys-to-update [:transcript/current-translations])))
              ;; deactivate last word
              (when last-current-word-id
                (swap! state assoc-in [:word/id last-current-word-id :word/active] false))
              (when-let [new-current-word-id (get-in transcript-keys-to-update [:transcript/current-word 1])]
                ;; activate new active word
                (swap! state assoc-in [:word/id new-current-word-id :word/active] true)))))
  (remote [_] false))

(defmutation hide-transcript-help [_]
  (action [{:keys [state]}]
          (swap! state assoc :ui/help-hidden true)))

(defmutation update-ui-player-doing [{:keys [ui-player/doing]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id (get-current-transcript-id-from-state @state) :ui-player/doing] doing)))

(defmutation transcript-display-type-menu [#:transcript{:keys [id display-type]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id id :transcript/display-type] display-type)))


(defn language-translations-in-current-transcript-visible?
  "Return true if any translation in `lang` is visible in current transcript."
  [state-deref lang]
  (some? (some :translation/visible? (language-translations-in-current-transcript state-deref lang))))


(comment
  ;;run (app/current-state app) in ns com.submerged-structure.client to get the state then:
  (def state-deref *1)
  (get-all-translations-in-current-transcript state-deref))

;; :ui-translation-controls/languages [{:ui-translation-control/language "en"
;;                                      :ui-translation-control/visible-translations? false}]

(defmutation toggle-visibility-of-all-translations-in-lang
  "If any translation visible in `lang` in current transcript then hide all translations in `lang` in current transcript.
   Otherwise show all translations in `lang` in current transcript."
  [{:keys [:ui-translation-control/language]}]
  (action [{:keys [state]}]
          (let [state-deref @state
                any-visible-in-transcript-idx [:ui-translation-control/language language :ui-translation-control/visible-translations?]]
            (do
              (swap! state update-in any-visible-in-transcript-idx not)
              (doseq [translation-id (map :translation/id (language-translations-in-current-transcript state-deref language))]
                (swap! state assoc-in
                       [:translation/id translation-id :translation/visible?]
                       (get-in @state any-visible-in-transcript-idx)))))))
(comment
  ;;run (app/current-state app) in ns com.submerged-structure.client to get the state then:
  (def state-deref *1)

  (def language "en")

  (get-in state-deref [:transcript/id (get-current-transcript-id-from-state state-deref) :ui-translation-controls/languages])
  ;; => true
  (get-in state-deref [:ui-translation-control/language language :ui-translation-control/visible-translations?])
  (languages-in-current-transcript state-deref))


(defmutation toggle-visibility-of-translation [{:keys [:translation/id]}]
  (action [{:keys [state]}]
          (swap! state update-in [:translation/id id :translation/visible?] not)
          (doall (map (fn [lang]
                        (swap! state assoc-in
                               [:ui-translation-control/language lang :ui-translation-control/visible-translations?]
                               (language-translations-in-current-transcript-visible? @state lang)))
                      (languages-in-current-transcript @state)))))


(defn get-all-morphological-details-items-in-current-transcript-and-their-visibility [state-deref]
  (remove #(#{"SYM" "PUNCT"} (:token/pos %))
          (mapcat
           :word/tokens
           (mapcat
            :segment/words
            (get-in
             (fdn/db->tree
              [#:root{:current-transcript
                      [:transcript/id
                       #:transcript{:segments
                                    [:segment/id
                                     #:segment{:words [:word/id
                                                       #:word{:tokens
                                                              [:token/id :token/pos :token/morphological-details-visible?]}]}]}]}]
              state-deref state-deref)
             [:root/current-transcript :transcript/segments])))))

(comment 
  (def state-deref *1)
  (get-all-morphological-details-items-in-current-transcript-and-their-visibility state-deref))

(defn any-morphological-details-item-in-current-transcript-visible? [state-deref]
  (some :token/morphological-details-visible? (get-all-morphological-details-items-in-current-transcript-and-their-visibility state-deref)))


(defmutation toggle-visibility-of-morphological-details-for-token [{:keys [:token/id]}]
  (action [{:keys [state]}]
          (swap! state update-in [:token/id id :token/morphological-details-visible?] not)
          (swap!
           state
           assoc-in
           [:transcript/id (get-current-transcript-id-from-state @state) :ui-morphological-info-grid-control/any-visible?]
           (any-morphological-details-item-in-current-transcript-visible? @state))))


(defmutation toggle-visibility-of-morphological-details-for-transcript [{:keys [:transcript/id]}]
  (action [{:keys [state]}]
          (swap! state update-in [:transcript/id id :ui-morphological-info-grid-control/any-visible?] not)
          (doall (map (fn [id-of-token-with-morph-analysis]
                        (swap! state
                               assoc-in
                               [:token/id id-of-token-with-morph-analysis :token/morphological-details-visible?]
                               (get-in @state [:transcript/id id :ui-morphological-info-grid-control/any-visible?])))
                      (map :token/id
                           (get-all-morphological-details-items-in-current-transcript-and-their-visibility @state)
                           )))))



(defmutation update-transcript-duration [{:transcript/keys [duration]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id (get-current-transcript-id-from-state @state) :transcript/duration] duration))
  (remote [_] false))


(defmutation load-transcript [{:transcript/keys [id]}]
  (action [{:keys [app state]}]
          (let [next-transcript-ident [:transcript/id id]]
            (swap! state assoc-in [:root/current-transcript] next-transcript-ident)
            (df/load! app next-transcript-ident transcript/Transcript)))
  (remote [_] false))

(defmutation toggle-transcript-scroll-to-active [_]
  (action [{:keys [state]}]
          (swap! state update-in [:transcript/id (get-current-transcript-id-from-state @state) :ui-player/scroll-to-active] not))
  (remote [_] false))
(ns com.submerged-structure.mutations.words-and-segments
  "Client-side mutations.
   
   'Server-side' mutations could normally be also defined here, only with
   `#?(:clj ...)` but here even the 'server' runs in the browser so we must
   define them in another ns, which we do in `...pathom`."
  (:require
   [com.fulcrologic.fulcro.mutations :refer [defmutation]]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
   [com.submerged-structure.mutations.common :as common]
   [com.submerged-structure.mutations.translations :as translations]))


(defn get-current-segment-word-tree-from-state
  [state-deref]
  (get-in
   (fdn/db->tree
    [#:root{:current-transcript
            [:transcript/id
             #:transcript{:segments
                          [:segment/id :segment/start :segment/end :segment/autopause?
                           #:segment{:words [:word/id :word/start :word/end]}]}]}]
    state-deref state-deref)
   [:root/current-transcript :transcript/segments]))

(defn words-with-unique-time-stamps [segment-word-tree]
  (->>
   segment-word-tree
   (mapcat :segment/words)
   (filter #(not= (:word/start %) (:word/end %)))))


(defn changes-to-make-to-transcript-keys-in-local-db-when-time-changes-related-to-current-translations
  "Return the id of current word to highlight or nil if no word to highlight.
   And return the period for t outside which this function should be called again."
  [state-deref t]
  (let [transcript-duration (get-in state-deref [:transcript/id (common/get-current-transcript-id-from-state state-deref) :transcript/duration])
        changes-for-each-lang (for [lang (translations/languages-in-current-transcript state-deref)
                                    :let [translations-in-lang (translations/language-translations-in-current-transcript state-deref lang)
                                          [translations-started-before-t-in-lang translations-starting-after-t-in-lang] (split-with #(<= (:translation/start %) t) translations-in-lang)
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
  )


(defn changes-to-make-to-transcript-keys-in-local-db-when-time-changes-related-to-current-word
  "Return the id of current word to highlight or nil if no word to highlight.
   And return the period for t outside which this function should be called again."
  [state-deref t]
  (let [transcript-id (common/get-current-transcript-id-from-state state-deref)
        transcript-duration (get-in state-deref [:transcript/id transcript-id :transcript/duration])
        
        segment-word-tree (get-current-segment-word-tree-from-state state-deref)
        
        all-words (words-with-unique-time-stamps segment-word-tree) 
        [words-started-before-t words-starting-after-t] (split-with #(<= (:word/start %) t) all-words)
        last-started-word (last words-started-before-t)
        next-word (first words-starting-after-t)
        
        [segments-starting-before-t segments-starting-after-t] (split-with #(<= (:segment/start %) t) segment-word-tree)
        last-started-segment (last segments-starting-before-t)
        next-segment (first segments-starting-after-t)
        prev-segments (drop-last segments-starting-before-t)
        ]
    (merge {:ui-player-controls/prev-prev-segment-end (:segment/end (last (drop-last prev-segments)))
            :ui-player-controls/prev-segment-start (:segment/start (last prev-segments))
            :ui-player-controls/prev-segment-end (:segment/end (last prev-segments))
            :ui-player-controls/current-segment-start (:segment/start last-started-segment)
            :ui-player-controls/current-segment-end (:segment/end last-started-segment)
            :transcript/current-or-last-segment [:segment/id (:segment/id last-started-segment)] ;; differs from current-word as still set between segments.
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
             :else ;; no timestamped words in transcript!! no more upates needed.
             {:transcript/current-word [:word/id nil] 
              :ui-period/start 0 
              :ui-period/end transcript-duration})
           ;;segments start and end at the same time as words so no need to adjust ui-period/start and ui-period/end
           (if (and last-started-segment
                    (:segment/autopause? last-started-segment)
                    (<= t (:segment/end last-started-segment))) ;; currently in segment with autopause
             {:ui-transcript-autopause/next-period-start (:segment/end last-started-segment) ;; if we hit this time period on subsequent throttled calls then we should pause
              :ui-transcript-autopause/next-period-end (if (not= (:segment/end last-started-segment) (:segment/start next-segment))
                                                 (:segment/start next-segment)
                                                 (+ (:segment/end last-started-segment) 0.2))}  ;;want to pause just after this segment ends on first call throttled by :ui-period/end
             {:ui-transcript-autopause/next-period-start nil
              :ui-transcript-autopause/next-period-end nil}))))


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
  "Sets the currently active word, if there is one, and also sets the start and end time of the time period in which no more updates to state necessary for segment, word or translation."
  [{:transcript/keys [current-time]}]
  (action [{:keys [state]}]
          (let [transcript-id (common/get-current-transcript-id-from-state @state)
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


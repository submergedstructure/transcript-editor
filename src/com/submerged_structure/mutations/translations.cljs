(ns com.submerged-structure.mutations.translations
  (:require
   [com.fulcrologic.fulcro.mutations :refer [defmutation]]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
  
   [com.submerged-structure.mutations.common :as common]))



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

  (get-in state-deref [:transcript/id (common/get-current-transcript-id-from-state state-deref) :ui-translation-controls/languages])
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





(comment
  (def state-deref *1)
  (languages-in-current-transcript state-deref))

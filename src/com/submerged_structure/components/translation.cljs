(ns com.submerged-structure.components.translation
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [span]]

            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.semantic-ui.elements.label.ui-label :refer [ui-label]]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]

            [clojure.string :as str]))



(defsc Translation
  "`visible?` is a boolean. css class makes the translation appear to the right of a segment when translation hidden or below when shown."
  [this {:translation/keys [id text lang visible?]}]
  {:ident :translation/id
   :initial-state {}
   :query [:translation/id :translation/text :translation/start :translation/end :translation/lang :translation/visible?]}
  (span
   {:classes ["translation" (if visible? "translation-visible" "translation-hidden")]}
   (let [translation-failed (= (str/lower-case (:segment/transcription-text (comp/get-computed this))) (str/lower-case text))
         toggle-func (fn [e & args]
                       (. e stopPropagation) ;; necessary to prevent the toggle from happening twice when both onRemove and onClick are called.
                       (js/console.log "Toggle show translation:" e args id)
                       (comp/transact!
                        this
                        `[(com.submerged-structure.mutations/toggle-visibility-of-translation {:translation/id ~id})]))]
     (ui-popup
      {:size "tiny"
       :position "top center"
       :hideOnScroll true
       :header (str "\"" lang "\" translation")
       :content (str
                 (if visible? "Click to hide translation" "Click to show translation of this setence. You can also use the button above to the right of the player controls to show or hide all translations.")
                 (when translation-failed " ... Sorry the AI failed to translate this."))
       :trigger (ui-label {:onRemove (when visible? toggle-func)
                           :color (when translation-failed "red")
                           :onClick toggle-func
                           :pointing (if visible? :above :left)
                           :detail (when visible? text)
                           :size :large
                           :content lang})}))))

(def ui-translation (comp/computed-factory Translation {:keyfn :translation/id}))


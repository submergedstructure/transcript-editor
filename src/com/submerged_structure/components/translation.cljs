(ns com.submerged-structure.components.translation
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [span]]

            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.semantic-ui.elements.label.ui-label :refer [ui-label]]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]

            [clojure.string :as str]))



(defsc Translation
  "`visible?` is a boolean. css class makes the translation appear to the right of a segment when translation hidden or below when shown."
  [this {:translation/keys [text lang visible? active]}]
  {:ident :translation/id
   :initial-state {}
   :query [:translation/id :translation/text :translation/start :translation/end :translation/lang :translation/visible? :translation/active]}
  (when visible?
    (span
     {:classes ["translation" (if visible? "translation-visible" "translation-hidden")]}
     (let [translation-failed (= (str/lower-case (:segment/transcription-text (comp/get-computed this))) (str/lower-case text))]
       (ui-label {:active active
                  :color (when translation-failed "red")
                  :pointing :above
                  :detail text
                  :size (if visible? :big :large)
                  :content lang})))))

(def ui-translation (comp/computed-factory Translation {:keyfn :translation/id}))


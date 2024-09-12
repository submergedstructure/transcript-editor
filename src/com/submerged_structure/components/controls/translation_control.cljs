(ns com.submerged-structure.components.controls.translation-control
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
            [com.fulcrologic.semantic-ui.elements.button.ui-button :refer [ui-button]]
            [com.fulcrologic.semantic-ui.icons :as i]
            [com.submerged-structure.components.controls.common :as common-to-controls]
            ))


(defsc TranslationControl [this {:ui-translation-control/keys [language visible-translations?]}]
  {:ident :ui-translation-control/language
   :query [:ui-translation-control/language
           :ui-translation-control/visible-translations?]}
  (ui-popup
   (merge
    {:header (if visible-translations? (str "Hide ALL \"" language "\" translations.") (str "Show ALL \"" language "\" translations."))
     :content (str "If any \"" language "\" translations for the transcript setences are shown clicking will hide them all, if none are clicking will reveal them all.")
     :trigger (ui-button
               {:icon i/language-icon
                :onClick
                (fn [& _args]
                  (comp/transact!
                   this
                   `[(com.submerged-structure.mutations/toggle-visibility-of-all-translations-in-lang {:ui-translation-control/language ~language})]))
                :positive visible-translations?
                :labelPosition "right"
                :label {:pointing "left"
                        :content language}})}
    common-to-controls/common-options-for-popups-of-controls)))

(def ui-translation-control (comp/factory TranslationControl {:keyfn :ui-translation-control/language}))

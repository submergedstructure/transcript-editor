(ns com.submerged-structure.components.controls.morphological-info-control
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
            [com.fulcrologic.semantic-ui.elements.button.ui-button :refer [ui-button]]
            [com.fulcrologic.semantic-ui.icons :as i]
            [com.submerged-structure.components.controls.common :as common-to-controls]
            ))


(defsc MorphologicalInfoControl [this {:transcript/keys [id]
                                       :ui-morphological-info-grid-control/keys [any-visible?]}]
  {:ident :transcript/id
   :query [:transcript/id
           :ui-morphological-info-grid-control/any-visible?]}
  (ui-popup
   (merge
    {:header (if any-visible? (str "Hide ALL word information boxes.") (str "Show ALL word information boxes."))
     :content (str "If any word information boxes are shown clicking will hide them all. If none are, clicking will reveal them all.")
     :trigger (ui-button
               {:icon i/eye-icon
                :onClick
                (fn [& _args]
                  (comp/transact!
                   this
                   `[(com.submerged-structure.mutations.controls/toggle-visibility-of-morphological-details-for-transcript {:transcript/id ~id})]))
                :positive any-visible?})}
    common-to-controls/common-options-for-popups-of-controls)))

(def ui-morphological-info-control (comp/factory MorphologicalInfoControl {:keyfn :transcript/id}))

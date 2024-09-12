(ns com.submerged-structure.components.controls.translation-controls
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            
            [com.fulcrologic.semantic-ui.elements.button.ui-button-group :refer [ui-button-group]]
            
            [com.submerged-structure.components.controls.translation-control :refer [TranslationControl ui-translation-control]]))

(defsc TranslationControls [_this {:ui-translation-controls/keys [languages]}]
  {:ident :transcript/id
   :query [:transcript/id
           {:ui-translation-controls/languages (comp/get-query TranslationControl)}]}
  (ui-button-group
   nil
   (mapv ui-translation-control languages)))

(def ui-translation-controls (comp/factory TranslationControls))
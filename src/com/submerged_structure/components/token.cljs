(ns com.submerged-structure.components.token
  (:require
   [com.fulcrologic.fulcro.dom :as dom  :refer [span]]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc fragment]]

   [clojure.string]
   
   [com.submerged-structure.components.token-morphological-info :as token-morphological-info]))


(defsc WordToken [this {:token/keys [id morph pos text whitespace]} {:word/keys [active]}]
  {:ident :token/id
   :initial-state (fn [_] {})
   :query [:token/id :token/text :token/pos
           :token/morph :token/lemma :token/pos :token/is_morphed :token/norm :token/whitespace]}
  
  (fragment
   (span {:classes (concat [(when active "active") "word"] (token-morphological-info/morph-html-css-classes morph))
         :onClick (fn [e & args]
                    (. e stopPropagation) ;; necessary to prevent the toggle from happening twice when both onRemove and onClick are called.
                    (js/console.log "Show morph details:" e args id)
                    (when-not (#{"SYM" "PUNCT"} pos)
                      (comp/transact!
                       this
                       `[(com.submerged-structure.mutations/toggle-visibility-of-morphological-details-for-token {:token/id ~id})])))
         
         }
        text)
   whitespace))


(def ui-word-token (comp/computed-factory WordToken {:keyfn :token/id}))
         


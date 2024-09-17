(ns com.submerged-structure.components.word
  (:require
   [com.fulcrologic.fulcro.dom :as dom  :refer [span]]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]

   [clojure.string]

   [com.submerged-structure.confidence-to-color :as c-to-c]
   [com.submerged-structure.components.player :as player]
   
   [com.submerged-structure.components.word-morphological-info :as word-morphological-info]))


(defsc Word [this {:word/keys [id word start active score morph]}
                           {:transcript/keys [display-type]}]
  {:ident :word/id
   :initial-state (fn [_] {})
   :query [:word/id :word/word :word/start :word/end :word/active :word/score
           :word/morph :word/lemma :word/pos :word/is_morphed :word/norm]}
  
  (span {:classes (concat [(when active "active") "word"] (word-morphological-info/morph-html-css-classes morph))
         :onClick (if (= display-type :grammar)
                    (fn [e & args]
                      (. e stopPropagation) ;; necessary to prevent the toggle from happening twice when both onRemove and onClick are called.
                      (js/console.log "Show morph details:" e args id)
                      (comp/transact!
                       this
                       `[(com.submerged-structure.mutations/toggle-visibility-of-morphological-details-for-word {:word/id ~id})]))
                    (fn [ws] (player/on-word-click ws start)))
         
         #_(when-not (is-touch-device) (fn [ws] (player/on-word-click ws start)))
         :style (when (= display-type :confidence) (c-to-c/confidence-to-style score))}
        word))


(def ui-word (comp/computed-factory Word {:keyfn :word/id}))
         


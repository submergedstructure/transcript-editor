(ns com.submerged-structure.components.word
  (:require [clojure.string]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.dom :as dom  :refer [span]]
            [com.submerged-structure.components.player :as player]
            [com.submerged-structure.components.token :as word-token]
            [com.submerged-structure.confidence-to-color :as c-to-c]))


(defsc Word [_this
             {:word/keys [start active score tokens]}
             {:transcript/keys [display-type]}]
  {:ident :word/id
   :initial-state (fn [_] {})
   :query [:word/id :word/word :word/start :word/end :word/active :word/score
          {:word/tokens (comp/get-query word-token/WordToken)}]}
  
  (span
   {:classes [(when active "active")]
    :onClick (fn [ws] (player/on-word-click ws start))

    #_(when-not (is-touch-device) (fn [ws] (player/on-word-click ws start)))
    :style (when (= display-type :confidence) (c-to-c/confidence-to-style score))}
        (map #(word-token/ui-word-token % {:word/active active}) tokens)))


(def ui-word (comp/computed-factory Word {:keyfn :word/id}))
         


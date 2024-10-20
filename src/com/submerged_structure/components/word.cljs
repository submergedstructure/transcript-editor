(ns com.submerged-structure.components.word
  (:require [clojure.string]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.dom :as dom  :refer [span]]
            [com.submerged-structure.components.player :as player]
            [com.submerged-structure.components.token :as word-token]))


(defsc Word [_this
             {:word/keys [start active tokens]}
             computed-for-token]
  {:ident :word/id
   :initial-state (fn [_] {})
   :query [:word/id :word/word :word/start :word/end :word/active
          {:word/tokens (comp/get-query word-token/WordToken)}]}
  
  (span
   {:classes [(when active "active")]
    :onClick (fn [ws] (player/on-word-click ws start))}
        (map #(word-token/ui-word-token % (merge {:word/active active} computed-for-token)) tokens)))


(def ui-word (comp/computed-factory Word {:keyfn :word/id}))
         


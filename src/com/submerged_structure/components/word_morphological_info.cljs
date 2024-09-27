(ns com.submerged-structure.components.word-morphological-info
  (:require
   [com.fulcrologic.fulcro.components :as comp :refer [defsc fragment]]
   [com.submerged-structure.components.token-morphological-info :as token-morphological-info]))

(defsc WordMorphologicalInfo [_this {:word/keys [active tokens]}]
  {:ident :word/id
   :initial-state (fn [_] {:word/tokens (comp/get-initial-state token-morphological-info/TokenMorphologicalInfo)})
   :query [:word/id :word/active {:word/tokens (comp/get-query token-morphological-info/TokenMorphologicalInfo)}]}
  (fragment {} (map #(token-morphological-info/ui-token-morphological-info % {:word/active active}) tokens)))

(def ui-word-morphological-info (comp/factory WordMorphologicalInfo {:keyfn :word/id}))
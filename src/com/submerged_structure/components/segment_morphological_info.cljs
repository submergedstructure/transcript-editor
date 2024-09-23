(ns com.submerged-structure.components.segment-morphological-info
  (:require
   [com.fulcrologic.fulcro.dom :as dom  :refer [div]]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]

   [clojure.string]
   
   [com.submerged-structure.components.word-morphological-info :as word-morphological-info]))

(defsc WordMorphologicalInfo [_this {:segment/keys [words]}]
  {:ident :segment/id
   :query [:segment/id :segment/words {:segment/words (comp/get-query word-morphological-info/WordMorphologicalInfo)}]}
  (div
   (div :.ui.horizontal.list
        (map (fn [morphological-info] (word-morphological-info/ui-word-morphological-info morphological-info)) words))))

(def ui-word-morphological-info (comp/factory WordMorphologicalInfo {:keyfn :segment/id}))

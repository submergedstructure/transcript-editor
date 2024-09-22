(ns com.submerged-structure.components.segment-morphological-info-grid
  (:require
   [com.fulcrologic.fulcro.dom :as dom  :refer [div]]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]

   [clojure.string]
   
   [com.submerged-structure.components.word-morphological-info :as word-morphological-info]))

(defsc WordMorphologicalInfoGrid [_this {:segment/keys [words]}]
  {:ident :segment/id
   :query [:segment/id :segment/words {:segment/words (comp/get-query word-morphological-info/WordMorphologicalInfo)}]}
  (div :.ui.grid.stackable
       (div :.doubling.ten.column.row
            (map (fn [morphological-info] (word-morphological-info/ui-word-morphological-info morphological-info)) words))))

(def ui-word-morphological-info-grid (comp/factory WordMorphologicalInfoGrid {:keyfn :segment/id}))

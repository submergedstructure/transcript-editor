(ns com.submerged-structure.components.segment-morphological-info
  (:require
   [com.fulcrologic.fulcro.dom :as dom  :refer [div]]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]

   [clojure.string]
   
   [com.submerged-structure.components.word-morphological-info :as word-morphological-info]))

(defsc SegmentMorphologicalInfo [_this {:segment/keys [words]}]
  {:ident :segment/id
   :initial-state (fn [_] {:segment/words (comp/get-initial-state word-morphological-info/WordMorphologicalInfo {})})
   :query [:segment/id {:segment/words (comp/get-query word-morphological-info/WordMorphologicalInfo)}]}
  (div
   {}
   (div :.ui.horizontal.list
        {}
        (map word-morphological-info/ui-word-morphological-info words))))

(def ui-segment-morphological-info (comp/factory SegmentMorphologicalInfo {:keyfn :segment/id}))

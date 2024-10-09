(ns com.submerged-structure.components.home
  (:require [com.fulcrologic.fulcro.dom :as dom]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]))

(defsc Home [_this {:keys []}]
  {:ident (fn [] [:component/id ::Home])
   :query []
   :initial-state {}
   :route-segment ["home"]}
  (dom/div
   (dom/h1 "Submerged Structure")))
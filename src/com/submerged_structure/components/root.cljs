(ns com.submerged-structure.components.root
  (:require [com.fulcrologic.fulcro.dom :as dom]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.ui-state-machines :as uism]
            
            [com.submerged-structure.components.transcript-router :as transcript-router]))

(defsc Root [this {:ui/keys [router]}]
  {;:query [{:ui/router (comp/get-query MyRouter)}]
   :query [{:ui/router (comp/get-query transcript-router/TranscriptRouter)}
           [::uism/asm-id ::transcript-router/TranscriptRouter]]
   :initial-state {:ui/router {}}}
  (let [router-state (or (uism/get-active-state this ::transcript-router/TranscriptRouter) :initial)]
    (if (= :initial router-state)
      (dom/div :.loading "Loading...")
      (transcript-router/ui-transcript-router router))))
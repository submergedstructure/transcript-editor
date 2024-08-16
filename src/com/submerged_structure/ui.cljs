(ns com.submerged-structure.ui
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div h1 h2 h3 li p ul span i b a]]
            [com.fulcrologic.fulcro.algorithms.normalize :as fn]
            
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.submerged-structure.components.player :as player]
            
            
            [com.submerged-structure.mock-data :as mock-data]
            
            [com.submerged-structure.components.transcript :as transcript]
            [com.submerged-structure.components.transcript-switcher :as transcript-switcher]))





(defsc Root [_this {:keys [:root/current-transcript]}]
  {:initial-state
   (fn [_]
     {:root/current-transcript (comp/get-initial-state transcript/Transcript {})})
   :query [{:root/current-transcript (comp/get-query transcript/Transcript)}]}
  (dom/div (transcript/ui-transcript current-transcript)))


(comment
  (.pause (player/get-player))
  (.play (player/get-player))
  (.getDuration (player/get-player))

  (.getCurrentTime (player/get-player))
  (comp/get-query Root)



  (comp/get-computed transcript-switcher/TranscriptSwitcher :current-transcript)

  (comp/get-initial-state Root {})

  (-> (comp/get-query Root) :root/current-transcript  vals first meta)

  (require '[clojure.walk :as w])
  (w/postwalk
   (fn [x]
     (cond
       (map-entry? x) x
       (vector? x) (list
                    (get-in (meta x)
                            [:component :displayName]
                            :NO-COMPONENT)
                    (filterv map? x))

       :default x))
   (comp/get-query Root))
  ;; => (:NO-COMPONENT
  ;;     [#:root{:current-transcript
  ;;             (:NO-COMPONENT [#:transcript{:segments (:NO-COMPONENT [#:segment{:words (:NO-COMPONENT [])}])}])}])

  (defn list-all-properties [obj]
    (let [props (atom #{})]
      (loop [proto obj]
        (when proto
          (do
            (.forEach (js/Object.getOwnPropertyNames proto)
                      (fn [prop] (swap! props conj prop)))
            (recur (.-prototype (js/Object.getPrototypeOf proto))))))
      @props))

  (list-all-properties (-.media (player/get-player)))



  (require 'clojure.spec.alpha 'edn-query-language.core)
  (clojure.spec.alpha/explain
   :edn-query-language.core/query
   (comp/get-query com.submerged-structure.ui/Root))

  (comp/get-initial-state transcript/Transcript {})
  mock-data/transcript
  (comp/get-query com.submerged-structure.ui/Transcript)
  (com.fulcrologic.fulcro.components/get-initial-state Root {})
  
  (fn/tree->db  Root (com.fulcrologic.fulcro.components/get-initial-state Root) true)
  

  
  (comp/transact! com.submerged-structure.app/app '[(api/update-current-transcript {:transcript/id "48658c6e-6508-48f3-b581-eccb13e18082"})])
  )
  



  
  
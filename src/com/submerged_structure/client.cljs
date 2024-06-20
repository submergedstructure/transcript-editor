(ns com.submerged-structure.client
  (:require
   [com.submerged-structure.app :refer [app]]
   [com.submerged-structure.ui :as ui]
   [com.fulcrologic.fulcro.application :as app]
   [com.fulcrologic.fulcro.components :as comp]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
   [com.fulcrologic.fulcro.routing.dynamic-routing :as dr]
   [com.fulcrologic.fulcro.data-fetch :as df]))

(defn ^:export init
  "Called by shadow-cljs upon initialization, see shadow-cljs.edn"
  []
  (println "Initializing the app...")
  (app/set-root! app ui/Root {:initialize-state? true})
  (comp/transact! app `[(com.submerged-structure.mutations/load-transcript {:transcript/id "71f28d76-ba2e-46da-be47-8d2162b20803"})])
  (app/mount! app (app/root-class app) "app"))

(defn ^:export refresh 
  "Called by shadow-cljs upon hot code reload, see shadow-cljs.edn"
  []
  (println "Refreshing after a hot code reload...")
  (comp/transact! app `[(com.submerged-structure.mutations/load-transcript)])
  (app/mount! app (app/root-class app) "app")
  
  
  )

(comment
  (df/load! app :>/transcript-switcher ui/TranscriptSwitcher)
  (app/force-root-render! app)
  (app/current-state app)
  (com.fulcrologic.fulcro.components/get-initial-state com.submerged-structure.ui/Root)

  (let [state (app/current-state app)]
    (fdn/db->tree
     (comp/get-query ui/Root) ; or any component
          ;; Starting entity, state itself for Root
          ;; otherwise st. like (get-in state-map [:thing/id 1]):
     state
     state))
  ;; => #:root{:current-transcript {}}

  ;; => nil

  )
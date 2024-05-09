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
  (df/load! app :root/current-transcript ui/Transcript)
  #_(dr/initialize! app) ; make ready, if you want to use dynamic routing...
  (app/mount! app ui/Root "app"))

(defn ^:export refresh 
  "Called by shadow-cljs upon hot code reload, see shadow-cljs.edn"
  []
  (println "Refreshing after a hot code reload...")
  #_(comp/refresh-dynamic-queries! app)
  (df/load! app :root/current-transcript ui/Transcript)
  (app/mount! app (app/root-class app) "app")
  
  
  )

(comment
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
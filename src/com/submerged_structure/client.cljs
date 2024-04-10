(ns com.submerged-structure.client
  (:require
   [com.submerged-structure.app :refer [app]]
   [com.submerged-structure.ui :as ui]
   [com.fulcrologic.fulcro.application :as app]
   [com.fulcrologic.fulcro.components :as comp]
   [com.fulcrologic.fulcro.routing.dynamic-routing :as dr]
   [com.fulcrologic.fulcro.data-fetch :as df]))

(defn ^:export init
  "Called by shadow-cljs upon initialization, see shadow-cljs.edn"
  []
  (println "Initializing the app...")
  #_(app/set-root! app ui/Root {#_#_:initialize-state? true})
  #_(dr/initialize! app) ; make ready, if you want to use dynamic routing...
  (app/mount! app
              (app/root-class app)
              "app"
              {:initialize-state? true}))

(defn ^:export refresh 
  "Called by shadow-cljs upon hot code reload, see shadow-cljs.edn"
  []
  (println "Refreshing after a hot code reload...")
  #_(comp/refresh-dynamic-queries! app)
  (app/mount! app (app/root-class app) "app")
  #_(df/load! app :transcript ui/Transcript))

(comment
  
  )
(ns com.submerged-structure.client
  (:require
   [com.submerged-structure.app :refer [app]]
   [com.submerged-structure.ui :as ui]
   
   [com.fulcrologic.fulcro.application :as app]
   [com.fulcrologic.fulcro.components :as comp]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
   [com.fulcrologic.fulcro.routing.dynamic-routing :as dr]
   [com.fulcrologic.fulcro.data-fetch :as df]
   [com.submerged-structure.mock-data :as mock-data]))

(defn ^:export init
  "Called by shadow-cljs upon initialization, see shadow-cljs.edn"
  []
  (println "Initializing the app...")
  (app/set-root! app ui/Root {:initialize-state? true})
  (comp/transact! app `[(com.submerged-structure.mutations/load-transcript {:transcript/id ~(mock-data/nth-transcript-id 2)})])
  (app/mount! app (app/root-class app) "app"))

(defn ^:export refresh
  "Called by shadow-cljs upon hot code reload, see shadow-cljs.edn"
  []
  (println "Refreshing after a hot code reload...")
  #_(comp/transact! app `[(com.submerged-structure.mutations/load-transcript {:transcript/id ~(mock-data/nth-transcript-id 2)})])
  (app/mount! app (app/root-class app) "app"))

(comment
  (df/load! app :>/transcript-switcher ui/TranscriptSwitcher)
  (app/force-root-render! app)
  (app/current-state app)
  (com.fulcrologic.fulcro.components/get-query com.submerged-structure.ui/Root)

  (let [state (app/current-state app)]
    (fdn/db->tree
     [{:root/current-transcript [#:transcript{:segments
                                              [:segment/id :segment/start :segment/end #:segment{:words [:word/id :word/start :word/end]}]}]}] ; or any component
          ;; Starting entity, state itself for Root
          ;; otherwise st. like (get-in state-map [:thing/id 1]):
     state
     state))
  ;; => #:root{:current-transcript {}}
  (require '[com.submerged-structure.components.word-with-morphological-features-popup :as word-with-morphological-features-popup])
  (let [state (app/current-state app)]
    (fdn/db->tree
     (comp/get-query ui/Root
                     )
            ;; Starting entity, state itself for Root
            ;; otherwise st. like (get-in state-map [:thing/id 1]):
     state
     state))
  )

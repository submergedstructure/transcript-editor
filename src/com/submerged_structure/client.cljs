(ns com.submerged-structure.client
  (:require
   [com.submerged-structure.app :refer [app]]
   [com.submerged-structure.components.root :as root]
   [com.submerged-structure.components.home :as home]
   
   [com.fulcrologic.fulcro.application :as app]
   [com.fulcrologic.fulcro.components :as comp]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
   [com.fulcrologic.fulcro.routing.dynamic-routing :as dr]
   [com.fulcrologic.fulcro.data-fetch :as df]

   [com.fulcrologic.rad.routing.html5-history :as hist5 :refer [new-html5-history]]
   [com.fulcrologic.rad.routing.history :as history]
   [clojure.string]))


(defn route-only-no-params->url
  "Construct URL from route and params
   (same as default but without the params encoded.)"
  [route _params _hash-based?]
  (str "/" (clojure.string/join "/" (map str route))))

(defn ^:export init
  "Called by shadow-cljs upon initialization, see shadow-cljs.edn"
  []
  (println "Initializing the app...")
  (app/set-root! app root/Root {:initialize-state? true})
  (dr/initialize! app)
  (history/install-route-history!
   app
   (new-html5-history {:hash-based? false
                       :route->url route-only-no-params->url}))
  #_(comp/transact! app `[(com.submerged-structure.mutations/load-transcript {:transcript/id ~(mock-data/nth-transcript-id 2)})])
  (app/mount! app (app/root-class app) "app")
  (hist5/restore-route! app home/Home {}))

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
  
  (let [state (app/current-state app)]
    (fdn/db->tree
     (comp/get-query ui/Root
                     )
            ;; Starting entity, state itself for Root
            ;; otherwise st. like (get-in state-map [:thing/id 1]):
     state
     state))
  
  (let [state (app/current-state app)
        segments-with-words (get-in
                             (fdn/db->tree
                              [#:root{:current-transcript
                                      [:transcript/id
                                       #:transcript{:segments
                                                    [:segment/id :segment/start :segment/end :segment/autopause? #:segment{:words [:word/id :word/start :word/end]}]}]}]
                              state state)
                             [:root/current-transcript :transcript/segments])]
    (filter (fn [{:segment/keys [start end words]}]
              (or (not= (:word/start (first words)) start)
                   (not= (:word/end (last words)) end))) segments-with-words))
  (require '[com.submerged-structure.mutations :as mutations])

  (mutations/words-with-unique-time-stamps (mutations/get-current-segment-word-tree-from-state (app/current-state app)))

  (require '[com.submerged-structure.components.word :as word-with-morphological-features-popup])
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
  
       :else x))
   (comp/get-query word-with-morphological-features-popup/Word))
  (:component (meta *1))
  (:displayName *1)
  (-> (comp/get-query word-with-morphological-features-popup/WordMorphologicalInfo)
      vals
      first
      )
  )

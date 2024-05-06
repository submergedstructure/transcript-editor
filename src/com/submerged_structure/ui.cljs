(ns com.submerged-structure.ui
   (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div h1 li p ul span i]]
             [com.fulcrologic.fulcro.algorithms.normalize :as fn]
             [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
             [com.fulcrologic.fulcro.application :as app]
             [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
             [com.fulcrologic.fulcro.data-fetch :as df]
             [com.fulcrologic.fulcro.raw.components :as rc]
             [com.submerged-structure.mock-data :as mock-data]
             [com.fulcrologic.fulcro.algorithms.react-interop :as interop]
             ["@wavesurfer/react" :default WavesurferPlayer]
             ["wavesurfer.js/dist/plugins/minimap.esm.js" :default Minimap]
             [com.submerged-structure.mutations :as api]
             [com.submerged-structure.app :as ss]
             [goog.functions :as gf]))

 (defsc Word [this {:keys [word/word word/active]}]
   {:ident :word/id
    :query [:word/id :word/word :word/start :word/end :word/active]}
   (span (when active {:className "active"}) word))

 (def ui-word (comp/factory Word {:keyfn :word/id}))


 (defsc Segment [this {:keys [segment/words]}]
   {:ident :segment/id
    :query [:segment/id {:segment/words (comp/get-query Word)}]}
   (p (interleave (map ui-word words) (repeat " ")))) ;; space between words is language dependent may need to change to support eg. Asian languages.

 (def ui-segment (comp/factory Segment {:keyfn :segment/id}))

 (def ui-wavesurfer-player (interop/react-factory WavesurferPlayer))

 (defonce player-local (atom {:player nil
                           ;; :period-update-no is the number of the last update that has been sent to fulcro for the time period,
                           ;; so that we can be sure that the ui-period/start and end have been updated before we check them to see if current-time has again
                           ;; moved out of the window of this period. Necessary as the onTimeupdate event can overwhelm fulcro with updates but we want it to update as fast
                           ;; as possible.
                              :period-update-no 0}))


 (defsc PlayerComponent [this {:transcript/keys [id audio-filename]}]
   {:ident :transcript/id
    :query [:transcript/id
            :transcript/audio-filename]
    :shouldComponentUpdate
    (fn [this next-props next-state]
      (js/setTimeout (js/console.log "shouldComponentUpdate" this next-props next-state) 0)
      (not= (:transcript/id next-props) (:transcript/id (comp/props this))))}
   (js/console.log "PlayerComponent" (comp/get-computed this :onTimeupdate) id audio-filename)
   (ui-wavesurfer-player
    {:url (str "audio_and_transcript/" audio-filename ".mp3")
     :height 100
     :minPxPerSec 50,
     :waveColor "violet"
     :normalize? true,
     :interact? true,

     :backend 'MediaElement'

     :onReady (fn [^js player]
                (js/console.log "onReady" player)
                (swap! player-local assoc :player player)
                (comp/transact! this [(api/update-ui-player-doing {:transcript/id id :ui-player/doing :paused})
                                      (api/update-transcript-duration {:transcript/id id :transcript/duration (.getDuration player)})
                                      (api/update-transcript-current-time {:transcript/id id :transcript/current-time (.getCurrentTime player)})]))
     :onTimeupdate (comp/get-computed this :onTimeupdate)
     :hideScrollbar true,
     :autoCenter false,

     :onPause (fn [_]
                (js/console.log "onPause")
                (comp/transact! this [(api/update-ui-player-doing {:transcript/id id :ui-player/doing :paused})]))

     :onPlay (fn [_]
               (js/console.log "onPlay")
               (comp/transact! this [(api/update-ui-player-doing {:transcript/id id :ui-player/doing :playing})]))

     :plugins [(.create Minimap
                        {:height 20,
                         :normalize? true,
                         :waveColor "#ddd",
                         :progressColor "#999"})]}))

 (def ui-player (comp/computed-factory PlayerComponent {:keyfn :transcript/id}))

 (defn update-current-word [this id t]
   (comp/transact! this [(api/update-transcript-current-time {:transcript/id id :transcript/current-time t})])
   #_(js/console.log "update-current-word" this id t)
   #_(js/setTimeout (fn [] (.scrollIntoView (js/document.querySelector ".active") #js {:block "center" :behavior "smooth"})) 0))
 
 (defn update-current-word-debounced [this id start end]
   (when-let [player (:player @player-local)]
     (let [t (.getCurrentTime player)]
       (if (some nil? [start end]);check if either start or end are nil
         ((gf/rateLimit update-current-word 1000) this id t)
         (when-not (<= start t end)
           (js/setTimeout (js/console.log "current period update" t start end) 0)
           (update-current-word this id t))))))
 

(defsc Transcript [this {:transcript/keys [id
                                           label
                                           segments]
                         :ui-period/keys  [start end]
                         :ui-player/keys  [doing]
                         :>/keys        [player]}]
  {:ident :transcript/id
   :query [:transcript/id
           :transcript/label
           :ui-player/doing
           :ui-period/start
           :ui-period/end
           {:transcript/segments (comp/get-query Segment)}
           {:>/player (comp/get-query PlayerComponent)}]}
  (let [onTimeupdate (fn [& args] 
                       #_(js/setTimeout (js/console.log "onTimeupdate" args this id start end) 0)
                       (update-current-word-debounced this id start end))]
    (div
     (h1 {:onClick onTimeupdate} label)
     (div :.player_and_transcript_and_transcript
          (ui-player player {:onTimeupdate onTimeupdate})
          (dom/div
           (if (= doing :loading)
             (div "Loading...")
             (dom/button :.ui.icon.button
                         {:tabindex 0
                          :onClick
                          (fn [_]
                            (js/console.log "clicked" doing)
                            (if (= doing :playing)
                              (.pause (:player @player-local))
                              (.play (:player @player-local))))}

                         (if (= doing :playing)
                           (dom/i :.pause.icon)
                           (dom/i :.play.icon))))))
     (div :#transcript
          (map ui-segment segments)))))

(def ui-transcript (comp/factory Transcript {:keyfn :transcript/id}))

(defsc Root [this {:keys [:root/current-transcript]}]
  {:query (fn [_] [{:root/current-transcript (comp/get-query Transcript)}])}
  (div
   (ui-transcript current-transcript)))


(comment
  (.pause (:player @player-local))
  (.play (:player @player-local))
  (.getDuration (:player @player-local))


  (comp/get-query Root)

  (comp/get-computed Transcript)

  (comp/get-initial-state Root {})

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

  (list-all-properties (-.media (:player @player-local)))



  (require 'clojure.spec.alpha 'edn-query-language.core)
  (clojure.spec.alpha/explain
   :edn-query-language.core/query
   (comp/get-query com.submerged-structure.ui/Root))

  (comp/get-initial-state Transcript {})
  mock-data/transcript
  (comp/get-query com.submerged-structure.ui/Transcript)
  (com.fulcrologic.fulcro.components/get-initial-state Root {})
  (app/current-state Root)
  (fn/tree->db  Root (com.fulcrologic.fulcro.components/get-initial-state Root) true)
  (df/load! com.submerged-structure.app/app :transcript (rc/nc '[*]))
  (df/load! com.submerged-structure.app/app [:transcript/id "2221f28c-0f2d-479b-b4a7-80924c80721c"] Transcript)
  ;; Load this => 
  ;; Pathom 2: will get back from server {:sequence [{..}, ..]} - i.e. a *vector*
  ;; even though the resolver returns a lazy seq
  ;; Pathom 3: returns a list: {:sequence ({..}, ..)}
  (df/load! com.submerged-structure.app/app :sequence (rc/nc [:tst/id :tst/val]))


  (require '[com.submerged-structure.app :as ss])
  (api/words-from-state (app/current-state ss/app))
  (fdn/db->tree [#:root{:current-transcript
                        [:transcript/id
                         #:transcript{:segments
                                      [:segment/id #:segment{:words [:word/id :word/start :word/end]}]}]}]
                (app/current-state ss/app) (app/current-state ss/app))

  (get (app/current-state ss/app) :word/id)
  (api/find-current-period (app/current-state ss/app) 27.91 "2221f28c-0f2d-479b-b4a7-80924c80721c")
  (api/find-first-word-not-ended (app/current-state ss/app) 0.0)
  (api/find-last-word-ended (app/current-state ss/app) 0.0)

  (fdn/db->tree [#:root{:current-transcript
                        [:transcript/id
                         #:transcript{:segments
                                      [:segment/id #:segment{:words [:word/id :word/start :word/end]}]}]}]
                (app/current-state ss/app) (app/current-state ss/app)))
  



  
  
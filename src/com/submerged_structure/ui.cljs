(ns com.submerged-structure.ui
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div h1 li p ul span]]
            [com.fulcrologic.fulcro.algorithms.normalize :as fn]
            [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
            [com.fulcrologic.fulcro.application :as app]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.data-fetch :as df]
            [com.fulcrologic.fulcro.raw.components :as rc]
            [com.submerged-structure.mock-data :as mock-data]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]
            ["@wavesurfer/react" :refer [useWavesurfer Wavesurfer]]
            ["wavesurfer.js/dist/plugins/minimap.esm.js" :default Minimap]
            [com.submerged-structure.mutations :as api]
            [com.fulcrologic.fulcro.react.hooks :as hooks]))

(defsc Word [this {:keys [word/start word/end word/word]}]
  {:ident :word/id
   :query [:word/id :word/start :word/end :word/word]}
  (span word))

(def ui-word (comp/factory Word {:keyfn :word/id}))


(defsc Segment [this {:keys [segment/words]}]
  {:ident :segment/id
   :query [:segment/id {:segment/words (comp/get-query Word)}]}
  (p (interleave (map ui-word words) (repeat " ")))) ;; space between words is language dependent may need to change to support eg. Asian languages.

(def ui-segment (comp/factory Segment {:keyfn :segment/id}))



(defsc Transcript [this {:keys [transcript/id
                                transcript/label
                                transcript/segments
                                transcript/current-time
                                transcript/audio-filename]}]
  {:use-hooks? true
   :initial-state {}
   :ident :transcript/id
   :query [:transcript/id
           :transcript/label
           :transcript/audio-filename
           :transcript/current-time
           {:transcript/segments (comp/get-query Segment)}]
   #_#_:componentDidMount (fn [this]
                        (useWavesurfer
                         {:container (js/document.getElementById "wavesurfer-container")
                          :url (str "audio_and_transcript/" "realpolish-hint1" ".mp3")
                          :waveColor "violet"
                          :height 100
                          :minPxPerSec 50,
                          :plugins [(.create Minimap
                                             {:height 20,
                                              :waveColor "#ddd",
                                              :progressColor "#999"})]}))}
  (div
   (h1 label)  
   (let [container-ref
         ;;called every time ref changes
         (hooks/use-callback
           (fn [node]
             (js/console.log "container-ref before when" node)
             (when node
               ;;only reached when ref is not nil
               (js/console.log "container-ref after when" node)
               (let [{:keys [^Wavesrufer wavesurfer ready? playing? current-time]}
                     (useWavesurfer
                      {:container node
                       :url (str "audio_and_transcript/" audio-filename ".mp3")
                       :waveColor "violet"
                       :height 100
                       :minPxPerSec 50,
                       :plugins [(.create Minimap
                                          {:height 20,
                                           :waveColor "#ddd",
                                           :progressColor "#999"})]})
                     #_#_play-pause (fn [] (when wavesurfer (.playPause wavesurfer)))]))))]
    (div {:ref container-ref} "Wavesurfer container"))
    (dom/div
     (dom/button #_{:on-click play-pause}
      #_(if playing? "Pause" "Play") "Play/Pause"))
    
    (div "current-time:" current-time)
    #_(div "ready?:" ready?)
    (div :#transcript
         (map ui-segment segments))
    #_(div
       (ui-wavesurfer
        {:url (str "audio_and_transcript/" audio-filename ".mp3")
         :height 100
         :minPxPerSec 50,
         :waveColor "violet"
         :onReady (fn [player]
                    (js/console.log "Wavesurfer ready" player)
                    (swap! wavesurfer assoc :player player)
                    #_(comp/transact! this [(api/update-ui-player-doing {:ui-player/doing :paused})]))
         :onTimeupdate (fn [player] (let [current-time (.getCurrentTime player)]
                                      (comp/transact! this [(api/update-transcript-current-time {:transcript/id id :transcript/current-time current-time})])))
         :hideScrollbar true,
         :autoCenter false,

         #_#_:onPause (fn [_] (comp/transact! this [(api/update-ui-player-doing {:ui-player/doing :paused})]))

         #_#_:onPlay (fn [_] (comp/transact! this [(api/update-ui-player-doing {:ui-player/doing :playing})]))

         :plugins [(.create Minimap
                            {:height 20,
                             :waveColor "#ddd",
                             :progressColor "#999"})]})
       (dom/button {:onClick
                    (fn [_] (.play (:player @wavesurfer)))}
                   "Play")
       (dom/button {:onClick
                    (fn [_] (.pause (:player @wavesurfer)))}
                   "Pause"))))

(def ui-transcript (comp/factory Transcript {:keyfn :transcript/id}))

(defsc Root [this {:keys [:root/current-transcript]}]
  {:initial-state (fn [_] {:root/current-transcript (comp/get-initial-state Transcript {})})
   :query (fn [_] [{:root/current-transcript (comp/get-query Transcript)}])}
  (div
   (ui-transcript current-transcript)))


(comment
  (.pause (:player @wavesurfer))
  (.play (:player @wavesurfer))

  (comp/get-query Transcript)

  (comp/get-query Root)
  ;; => [#:root{:current-transcript
  ;;            [:transcript/id
  ;;             :transcript/label
  ;;             :transcript/audio-filename
  ;;             #:transcript{:segments
  ;;                          [:segment/id
  ;;                           :segment/start
  ;;                           :segment/end
  ;;                           :segment/text
  ;;                           #:segment{:words [:word/id :word/start :word/end :word/word]}]}]}]


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







  (require 'clojure.spec.alpha 'edn-query-language.core)
  (clojure.spec.alpha/explain
   :edn-query-language.core/query
   (comp/get-query com.submerged-structure.ui/Root))

  (comp/get-initial-state Transcript {})
  mock-data/transcript
  (comp/get-query com.submerged-structure.ui/Transcript)
  (com.fulcrologic.fulcro.components/get-initial-state Root {})
  (app/current-state app/app)
  (fn/tree->db  Root (com.fulcrologic.fulcro.components/get-initial-state Root) true)
  (df/load! com.submerged-structure.app/app :transcript (rc/nc '[*]))
  (df/load! com.submerged-structure.app/app [:transcript/id "2221f28c-0f2d-479b-b4a7-80924c80721c"] Transcript)
  ;; Load this => 
  ;; Pathom 2: will get back from server {:sequence [{..}, ..]} - i.e. a *vector*
  ;; even though the resolver returns a lazy seq
  ;; Pathom 3: returns a list: {:sequence ({..}, ..)}
  (df/load! com.submerged-structure.app/app :sequence (rc/nc [:tst/id :tst/val])))
  
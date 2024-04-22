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
   :ident :transcript/id
   :query [:transcript/id
           :transcript/label
           :transcript/audio-filename
           :transcript/current-time
           {:transcript/segments (comp/get-query Segment)}]}
  (let [^:js container-ref (hooks/use-ref nil)
        mm-plugin (.create Minimap
                           #js {:height 20,
                                :waveColor "#ddd",
                                :progressColor "#999"})
        ws (useWavesurfer
            #js {:container container-ref
                 :url (str "audio_and_transcript/" audio-filename ".mp3")
                 :waveColor "violet"
                 :height 100
                 :minPxPerSec 50,
                 #_#_:plugins [mm-plugin]})
        wavesurfer (.-wavesurfer ws)
        is-ready (.-isReady ws)
        is-playing (.-isPlaying ws)
        current-time (.-currentTime ws)
        play-pause (fn []
                     (js/console.log "play-pause" wavesurfer is-ready is-playing current-time)
                     (when wavesurfer (.playPause wavesurfer)))]
    (div
     (js/console.log "initiated?" wavesurfer is-ready is-playing current-time)
     (h1 label)
     (div {:ref container-ref} "Wavesurfer container")
     (dom/div
      (dom/button {:onClick play-pause} (if is-playing "Pause" "Play")))

     (div "current-time:" current-time)
     (div "ready?:" is-ready)
     (div :#transcript
          (map ui-segment segments)))))

(def ui-transcript (comp/factory Transcript {:keyfn :transcript/id}))

(defsc Root [this {:keys [:root/current-transcript]}]
  {:query [{:root/current-transcript (comp/get-query Transcript)}]}
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
  
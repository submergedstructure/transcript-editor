(ns com.submerged-structure.ui-player
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.submerged-structure.mutations :as api]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]
            ["@wavesurfer/react" :default WavesurferPlayer]
            ["wavesurfer.js/dist/plugins/minimap.esm.js" :default Minimap]))

(defonce player-local (atom {:player nil}))

(defn ^js get-player []
  (:player @player-local))

(defn set-player! [player]
  (swap! player-local assoc :player player))

(defn player-height [transcript-id] (.-clientHeight (js/document.querySelector (str "#player-" transcript-id))))


(def ui-wavesurfer-player (interop/react-factory WavesurferPlayer))

(defsc PlayerComponent [this {:transcript/keys [id audio-filename]}]
  {:ident :transcript/id
   :initial-state {}
   :query [:transcript/id
           :transcript/audio-filename
           :ui-period/end
           :ui-period/start]
   :shouldComponentUpdate
   (fn [this next-props next-state]
     (js/setTimeout (js/console.log "shouldComponentUpdate" this next-props next-state) 0)
     (not= (select-keys next-props [:transcript/id])
           (select-keys (comp/props this) [:transcript/id])))}
  (js/console.log "PlayerComponent" (comp/get-computed this :onTimeupdate) id audio-filename)
  (ui-wavesurfer-player
   {:url (js/encodeURI audio-filename)
    :height 100
    :minPxPerSec 50,
    :waveColor "violet"
    :normalize? true,
    :interact? true,

    :backend 'MediaElement'

    :onReady (fn [^js player]
               (js/console.log "onReady" player)
               (set-player! player)
               (comp/transact! this [(api/update-ui-player-doing {:transcript/id id :ui-player/doing :paused})
                                     (api/update-transcript-duration {:transcript/id id :transcript/duration (.getDuration player)})
                                     (api/update-transcript-current-time {:transcript/id id :transcript/current-time (.getCurrentTime player)})]))

    :onError (fn [^js error]
               (js/console.log "onError" error)
               #_(ui-modal-dimmer
                  {:open true
                   :header "Error"
                   :content "An error occurred while playing the audio."
                   :actions [{:key :ok :content "OK" :onClick (fn [_] (.close player))}]}))
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

(def ui-player
  "Third param will be a computed function."
  (comp/computed-factory PlayerComponent {:keyfn :transcript/id}))

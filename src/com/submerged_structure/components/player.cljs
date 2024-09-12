(ns com.submerged-structure.components.player
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]
            ["@wavesurfer/react" :default WavesurferPlayer]
            ["wavesurfer.js/dist/plugins/minimap.esm.js" :default Minimap]
            ["wavesurfer.js/dist/plugins/regions.esm.js" :default Regions]

            [com.submerged-structure.player-atom :as player-atom]))


(defn ^js get-player-regions-plugin [& [^js ws-player]]
  (-> (or ws-player (player-atom/get-player))
      (.getActivePlugins)
      (.find #(instance? Regions %))))


(comment (get-player-regions-plugin)
         (js/console.log "get-player-regions-plugin" (get-player-regions-plugin)))


(defn player-height [transcript-id] (.-clientHeight (js/document.querySelector (str "#player-" transcript-id))))

(defn on-word-click [_ start]
  (let [player (player-atom/get-player)]
    (when player
      (.setTime player start)
      (.play player))))

(def ui-wavesurfer-player (interop/react-factory WavesurferPlayer))

(defn player-on-current-word-update [start-current-word end-current-word current-word]
  (let [rp (get-player-regions-plugin)]
    (.clearRegions rp)
    (.addRegion rp
                #js {:content current-word
                     :start start-current-word
                     :end end-current-word
                     :drag true
                     :resize true
                     :color "rgba(0, 0, 128, 0.5)"})))

(comment (player-on-current-word-update 5 15 "test"))

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
  (js/console.log "PlayerComponent" (comp/get-computed this :onTimeupdate) audio-filename)
  (ui-wavesurfer-player
   {:url (js/encodeURI audio-filename)
    :height 150
    :minPxPerSec 100,
    :waveColor "violet"
    :normalize? true,
    :interact? true,


    :onDecode (fn [^js ws]
                (player-atom/set-player! ws))

    :onReady (fn [^js player]
               (js/console.log "onReady" player)
               (comp/transact! this `[(com.submerged-structure.mutations/update-ui-player-doing {:ui-player/doing :paused})
                                      (com.submerged-structure.mutations/update-transcript-duration {:transcript/duration ~(.getDuration player)})
                                      (com.submerged-structure.mutations/update-transcript-current-time {:transcript/current-time ~(.getCurrentTime player)})]))

    :onError (fn [^js & args]
               (js/console.log "onError" args))
    :onTimeupdate (comp/get-computed this :onTimeupdate)
    :hideScrollbar true,
    :autoCenter false,

    :onPause (fn [_]
               (js/console.log "onPause")
               (comp/transact! this `[(com.submerged-structure.mutations/update-ui-player-doing {:ui-player/doing :paused})]))

    :onPlay (fn [_]
              (js/console.log "onPlay")
              (comp/transact! this `[(com.submerged-structure.mutations/update-ui-player-doing {:ui-player/doing :playing})]))

    :plugins [(.create Minimap
                       #js {:height 40,
                            :normalize? true})
              (.create Regions
                       #js {:on (fn [& args] (js/console.log "Regions event" args))})]}))

(def ui-player
  "Third param will be a computed function."
  (comp/computed-factory PlayerComponent {:keyfn :transcript/id}))


(comment (player-atom/get-player)
         (js/console.log (player-atom/get-player))
         (player-on-current-word-update 10, 15, "hello"))
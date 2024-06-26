(ns com.submerged-structure.ui-player
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.algorithms.react-interop :as interop]
            ["@wavesurfer/react" :default WavesurferPlayer]
            ["wavesurfer.js/dist/plugins/minimap.esm.js" :default Minimap]

            [com.fulcrologic.fulcro.dom :as dom]
            [com.fulcrologic.semantic-ui.elements.button.ui-button-group :refer [ui-button-group]]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup-content :refer [ui-popup-content]]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup-header :refer [ui-popup-header]]
            [com.fulcrologic.semantic-ui.elements.button.ui-button :refer [ui-button]]
            [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
            [com.fulcrologic.semantic-ui.icons :as i]
            [com.fulcrologic.semantic-ui.elements.label.ui-label :refer [ui-label]]
            
            [goog.string :as gstring]))

(defonce player-local (atom {:player nil}))

(defn ^js get-player []
  (:player @player-local))

(defn set-player! [player]
  (swap! player-local assoc :player player))

(defn player-height [transcript-id] (.-clientHeight (js/document.querySelector (str "#player-" transcript-id))))

(defn on-word-click [_ start]
  (let [player (get-player)]
    (when player
      (.setTime player start)
      (.play player))))

(def ui-wavesurfer-player (interop/react-factory WavesurferPlayer))

(defn time-float-to-string [t duration]
  (let [max-t-minutes-length (count (str (quot duration 60)))
        t-2dp (.toFixed t 2)]
    (str  (gstring/padNumber (quot t-2dp 60) max-t-minutes-length 0)
          ":" (gstring/padNumber (mod t-2dp 60) 2 1))))

(defn player-on-timeupdate [^js ws]
    (let [current-time (.getCurrentTime ws)
          player-time-el (js/document.querySelector "span#player-time")]
      (set! (.-textContent player-time-el) (time-float-to-string current-time (.getDuration ws)))))


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
               (comp/transact! this `[(com.submerged-structure.mutations/update-ui-player-doing {:transcript/id ~id :ui-player/doing :paused})
                                      (com.submerged-structure.mutations/update-transcript-duration {:transcript/id ~id :transcript/duration ~(.getDuration player)})
                                      (com.submerged-structure.mutations/update-transcript-current-time {:transcript/id ~id :transcript/current-time ~(.getCurrentTime player)})]))
  
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
               (comp/transact! this `[(com.submerged-structure.mutations/update-ui-player-doing {:transcript/id ~id :ui-player/doing :paused})]))
  
    :onPlay (fn [_]
              (js/console.log "onPlay")
              (comp/transact! this `[(com.submerged-structure.mutations/update-ui-player-doing {:transcript/id ~id :ui-player/doing :playing})]))
  
    :plugins [(.create Minimap
                       {:height 20,
                        :normalize? true,
                        :waveColor "#ddd",
                        :progressColor "#999"})]}))

(def ui-player
  "Third param will be a computed function."
  (comp/computed-factory PlayerComponent {:keyfn :transcript/id}))


(defn ui-popup-for-controls [header content trigger]
  (ui-popup
   {:size "tiny"
    :position "bottom center"
    :hideOnScroll true
    :header header
    :content content
    :trigger trigger}))

(defn ui-control-button [icon-name on-click & [options]]
  (ui-button
   (merge
    {:icon icon-name
     :onClick on-click}
    options)))

(defn ui-player-controls [transcript-comp id doing scroll-to-active]
  (if (or (= doing :loading) (nil? (get-player)))
    (ui-icon {:name "loading spinner"})
    (dom/div
     (ui-button-group
      nil
      (ui-control-button
       i/reply-all-icon
       (fn [_]
         (when-let [player (get-player)]
           (js/console.log "Replay all")
           (.seekTo player 0))))
      (ui-popup-for-controls
       "Rewind 5 seconds."
       "Or press the left arrow key."
       (ui-control-button
         i/chevron-left-icon
         (fn [_]
           (when-let [player (get-player)]
             (.skip player -5)))))
      (ui-popup-for-controls
       "Play/Pause"
       "Or press the space bar."
       (ui-control-button
        (if (= doing :playing) i/pause-icon i/play-icon)
        (fn [_]
          (when (get-player)
            (if (= doing :playing) (.pause (get-player)) (.play (get-player)))))
        {:labelPosition "left"
         :label (ui-label
                 {:pointing "right"
                  :content (dom/span
                            (dom/span :#player-time (time-float-to-string 0 (.getDuration (get-player))))
                            " of "
                            (time-float-to-string (.getDuration (get-player)) (.getDuration (get-player))))})}))
      
      (ui-popup-for-controls
       "Fast forward 5 seconds."
       "Or press the right arrow key."
       (ui-control-button
        i/chevron-right-icon
        (fn [_]
            (when-let [player (get-player)]
              (.skip player 5))))))
       (ui-button-group
        nil
        (ui-popup-for-controls
         nil
         "Toggle the transcript automatically scrolling to current spoken word."
         (ui-control-button
          i/crosshairs-icon
          (fn [& _args]
            (comp/transact! transcript-comp `[(com.submerged-structure.mutations/toggle-transcript-scroll-to-active {:transcript/id ~id})]))
          {:positive scroll-to-active}))))))

(comment (get-player))
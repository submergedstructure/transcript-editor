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
  (js/console.log "time-float-to-string" t duration)
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

(defn ui-play-button [doing]
  (js/console.log "ui-play-button" doing (get-player) (.getDuration (get-player)))
  (ui-button
   {:icon (ui-icon
           {:name
            (cond
              (or (= doing :loading) (nil? (get-player)))
              i/spinner-icon

              (= doing :playing)
              i/pause-icon

              :else
              i/play-icon)})
    :onClick
    (fn [_]
      (js/console.log "clicked" doing)
      (when (get-player)
        (if (= doing :playing)
          (.pause (get-player))
          (.play (get-player)))))
    :labelPosition "left"
    :label (ui-label
            {:pointing "right"
             :content (dom/span
                       (dom/span :#player-time (time-float-to-string 0 (.getDuration (get-player))))
                       " of "
                       (time-float-to-string (.getDuration (get-player)) (.getDuration (get-player))))})}))

(defn ui-player-controls [doing]
  (if (or (= doing :loading) (nil? (get-player)))
    (ui-icon {:name "loading spinner"})
    (dom/div
     (ui-button-group
      nil
      (ui-popup
       {:size "tiny"
        :position "bottom center"
        :trigger
        (ui-button
         {:icon true
          :onClick
          (fn [_]
            (js/console.log "clicked" doing)
            (when-let [player (get-player)]
              (.skip player -5)))}
         (ui-icon {:name i/chevron-left-icon}))}
       (ui-popup-header {:content "Rewind 5 seconds."})
       (ui-popup-content {:content "Or press the left arrow key."}))
      (ui-popup
       {:size "tiny"
        :position "bottom center"
        :trigger (ui-play-button doing)}
       (ui-popup-header {:content "Play/Pause"})
       (ui-popup-content {:content "Or press the space bar."}))
      (ui-popup
       {:size "tiny"
        :position "bottom center"
        :trigger
        (ui-button
         {:icon true
          :onClick
          (fn [_]
            (js/console.log "clicked" doing)
            (when-let [player (get-player)]
              (.skip player 5)))}
         (ui-icon {:name i/chevron-right-icon}))}
       (ui-popup-header {:content "Fast forward 5 seconds."})
       (ui-popup-content {:content "Or press the right arrow key."}))))))

(comment (get-player))
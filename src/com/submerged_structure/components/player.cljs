(ns com.submerged-structure.components.player
   (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
             [com.fulcrologic.fulcro.algorithms.react-interop :as interop]
             ["@wavesurfer/react" :default WavesurferPlayer]
             ["wavesurfer.js/dist/plugins/minimap.esm.js" :default Minimap]
             ["wavesurfer.js/dist/plugins/regions.esm.js" :default Regions]

             [com.fulcrologic.fulcro.dom :as dom]
             [com.fulcrologic.semantic-ui.elements.button.ui-button-group :refer [ui-button-group]]
             [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
             [com.fulcrologic.semantic-ui.elements.button.ui-button :refer [ui-button]]
             [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
             [com.fulcrologic.semantic-ui.icons :as i]

             [goog.string :as gstring]))

 (defonce player-local (atom {:player nil}))

 (defn ^js get-player []
   (:player @player-local))

 (defn ^js get-player-regions-plugin [& [^js ws-player]]
   (-> (or ws-player (get-player))
       (.getActivePlugins)
       (.find #(instance? Regions %))))
 
 

 (comment (get-player-regions-plugin)
          (js/console.log "get-player-regions-plugin" (get-player-regions-plugin)))

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

(defsc PlayerComponent [this {:transcript/keys [audio-filename]}]
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
                (set-player! ws))

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


(defn ui-popup-for-controls [header content trigger]
  (ui-popup
   {:size "tiny"
    :position "bottom center"
    :hideOnScroll true
    :header header
    :content content
    :trigger trigger}
   ))

(defn ui-control-button [icon-name on-click & [options]]
  (ui-button
   (merge
    {:icon icon-name
     :onClick on-click}
    options)))

(defsc TranslationControl [this {:ui-translation-control/keys [language visible-translations?]}]
  {:ident :ui-translation-control/language
   :query [:ui-translation-control/language
           :ui-translation-control/visible-translations?]}
  (ui-popup-for-controls
   (if visible-translations? (str "Hide ALL \"" language "\" translations.") (str "Show ALL \"" language "\" translations."))
   (str "If any \"" language "\" translations for the transcript setences are shown clicking will hide them all, if none are clicking will reveal them all.")
   (ui-control-button
    i/language-icon
    (fn [& _args]
      (comp/transact!
       this
       `[(com.submerged-structure.mutations/toggle-visibility-of-all-translations-in-lang {:ui-translation-control/language ~language})]))
    {:positive visible-translations?
     :labelPosition "right"
     :label {:pointing "left"
             :content language}})))

(def ui-translation-control (comp/factory TranslationControl {:keyfn :ui-translation-control/language}))

(defsc TranslationControls [_this {:ui-translation-controls/keys [languages]}]
  {:ident :transcript/id
   :query [:transcript/id
           {:ui-translation-controls/languages (comp/get-query TranslationControl)}]}
  (ui-button-group
   nil
   (mapv ui-translation-control languages)))

(def ui-translation-controls (comp/factory TranslationControls))

(defsc PlayerControls [this {:ui-player/keys  [doing scroll-to-active]
                             
                             prev-segment-start :ui-prev-segment/start
                             current-word-start  :ui-current-word/start
                             current-segment-start  :ui-current-segment/start
                             next-segment-start :ui-next-segment/start
                             prev-word-start :ui-prev-word/start
                             next-word-start :ui-next-word/start
                             
                             :>/keys [language-controls]}]
  
  {:ident :transcript/id
   :query [:transcript/id
           :ui-player/doing
           :ui-player/scroll-to-active

           :ui-prev-segment/start
           :ui-current-word/start
           :ui-current-segment/start
           :ui-next-segment/start
           :ui-prev-word/start
           :ui-next-word/start
           
           {:>/language-controls (comp/get-query TranslationControls)}]}
  (if (or (= doing :loading) (nil? (get-player)))
    (ui-icon {:name "loading spinner"})
    (dom/div {}
     (ui-button-group
      nil
      (ui-popup-for-controls
       "Rewind 15 seconds."
       "Or press the left arrow key."
       (ui-control-button
        i/chevron-left-icon
        (fn [_]
          (when-let [player (get-player)]
            (.skip player -15)))
        ;only gets updated when the ui-period changes but that's OK
        {:disabled (< (.getCurrentTime (get-player)) 15)}))
      (ui-popup-for-controls
       "Back one line."
       ""
       (ui-control-button
        i/angle-double-left-icon
        (fn [_]
          (when-let [player (get-player)]
            (.setTime player prev-segment-start)))
        {:disabled (nil? prev-segment-start)}))
      (ui-popup-for-controls
       "Back one word."
       ""
       (ui-control-button
        i/angle-left-icon
        (fn [_]
          (when-let [player (get-player)]
            (.setTime player prev-word-start)))
        {:disabled (nil? prev-word-start)}))
      (ui-popup-for-controls
       "Back to start of line and play."
       ""
       (ui-control-button
        i/reply-all-icon
        (fn [_]
          (when-let [player (get-player)]
            (.setTime player current-segment-start)
            (.play player)))
        {:disabled (nil? current-segment-start)}))
      (ui-popup-for-controls
       "Back to start of word and play."
       ""
       (ui-control-button
        i/reply-icon
        (fn [_]
          (when-let [player (get-player)]
            (.setTime player current-word-start)
            (.play player)))
        {:disabled (nil? current-word-start)}))
      (ui-popup-for-controls
       "Play/Pause"
       ""
       (ui-control-button
        (if (= doing :playing) i/pause-icon i/play-icon)
        (fn [_]
          (when (get-player)
            (if (= doing :playing) (.pause (get-player)) (.play (get-player)))))
        {:labelPosition "left"
         :label {:pointing "right"
                 :content (dom/span
                           (dom/span :#player-time (time-float-to-string 0 (.getDuration (get-player))))
                           " of "
                           (time-float-to-string (.getDuration (get-player)) (.getDuration (get-player))))}}))
      (ui-popup-for-controls
       "Forward one word and play."
       ""
       (ui-control-button
        i/share-icon
        (fn [_]
          (when-let [player (get-player)]
            (.setTime player next-word-start)
            (.play player)))
        {:disabled (nil? next-word-start)}))
      (ui-popup-for-controls
       "Forward one word."
       ""
       (ui-control-button
        i/angle-right-icon
        (fn [_]
          (when-let [player (get-player)]
            (.setTime player next-word-start)))
        {:disabled (nil? next-word-start)}))
      (ui-popup-for-controls
       "Forward one line."
       ""
       (ui-control-button
        i/angle-double-right-icon
        (fn [_]
          (when-let [player (get-player)]
            (.setTime player next-segment-start)))
        {:disabled (nil? next-segment-start)}))
      (ui-popup-for-controls
       "Fast forward 15 seconds."
       ""
       (ui-control-button
        i/chevron-right-icon
        (fn [_]
          (when-let [player (get-player)]
            (.skip player 15)))
        ;only gets updated when the ui-period changes but that's OK
        {:disabled (let [time-remaining (- (.getDuration (get-player)) (.getCurrentTime (get-player)))] (< time-remaining 15))})))
     (ui-button-group
      nil
      (ui-popup-for-controls
       nil
       "Toggle the transcript automatically scrolling to current spoken word."
       (ui-control-button
        i/crosshairs-icon
        (fn [& _args]
          (comp/transact! this `[(com.submerged-structure.mutations/toggle-transcript-scroll-to-active {})]))
        {:positive scroll-to-active
         :labelPosition "right"
         :label {:pointing "left"
                 :content (str "Auto scroll " (if scroll-to-active "on" "off"))}})))
      (ui-translation-controls language-controls))))
(def ui-player-controls (comp/factory PlayerControls))


(comment (get-player)
         (js/console.log (:player @player-local))
         (player-on-current-word-update 10, 15, "hello"))
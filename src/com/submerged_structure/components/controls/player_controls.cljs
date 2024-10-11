(ns com.submerged-structure.components.controls.player-controls
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc fragment]]
            [com.fulcrologic.fulcro.dom :as dom]

            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
            [com.fulcrologic.semantic-ui.elements.button.ui-button :refer [ui-button]]
            [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
            [com.fulcrologic.semantic-ui.icons :as i]
            
            [com.submerged-structure.player-atom :as player-atom]
            [com.submerged-structure.components.controls.common :as common-to-controls]
            [com.submerged-structure.components.controls.translation-controls :refer [TranslationControls ui-translation-controls]]
            [com.submerged-structure.components.controls.morphological-info-control :refer [MorphologicalInfoControl ui-morphological-info-control]]
            
            [goog.string :as gstring]))

(defn time-float-to-string [t duration]
  (let [max-t-minutes-length (count (str (quot duration 60)))
        t-2dp (.toFixed t 2)]
    (str  (gstring/padNumber (quot t-2dp 60) max-t-minutes-length 0)
          ":" (gstring/padNumber (mod t-2dp 60) 2 1))))

(defn player-on-timeupdate [^js ws]
  (let [current-time (.getCurrentTime ws)
        player-time-el (js/document.querySelector "span#player-time")]
    (set! (.-textContent player-time-el) (time-float-to-string current-time (.getDuration ws)))))

(defsc PlayerControls [this {:transcript/keys [id]
                             :ui-player/keys  [doing scroll-to-active]
                             :ui-transcript-autopause-control/keys [any-segment?]
                             :ui-player-controls/keys [prev-segment-start current-segment-start next-segment-start
                                                       prev-word-start current-word-start next-word-start]

                             :>/keys [language-controls morphological-info-grid]}]

  {:ident :transcript/id
   :query [:transcript/id
           :ui-player/doing
           :ui-player/scroll-to-active

           :ui-player-controls/prev-word-start
           :ui-player-controls/prev-segment-start

           :ui-player-controls/current-word-start
           :ui-player-controls/current-segment-start

           :ui-player-controls/next-word-start
           :ui-player-controls/next-segment-start

           :ui-transcript-autopause-control/any-segment?

           {:>/language-controls (comp/get-query TranslationControls)}
           {:>/morphological-info-grid (comp/get-query MorphologicalInfoControl)}]}
  (let [next-word-start-plus-ms (+ next-word-start 0.001) ;; after ws .setTime, getCurrentTime is off by a negative fraction of a ms.
        current-word-start-plus-ms (+ current-word-start 0.001)
        prev-word-start-plus-ms (+ prev-word-start 0.001)

        next-segment-start-plus-ms (+ next-segment-start 0.001)
        current-segment-start-plus-ms (+ current-segment-start 0.001)
        prev-segment-start-plus-ms (+ prev-segment-start 0.001)
        ]
    (if (or (= doing :loading) (nil? (player-atom/get-player)))
      (ui-icon {:name i/spinner-icon})
      (dom/div :.ui.segment.basic 
       (dom/div
        :.ui.container.grid.centered.player-controls
        (dom/span ; time controls
         :.item
         {}
         #_(ui-popup
            (merge
             {:header "Rewind 15 seconds."
              :content ""
              :trigger
              (ui-button
               {:icon i/chevron-left-icon
                :onClick (fn [_]
                           (when-let [player (player-atom/get-player)]
                             (.skip player -15)))
                  ;only gets updated when the ui-period changes but that's OK
                :disabled (< (.getCurrentTime (player-atom/get-player)) 15)})}
             common-to-controls/common-options-for-popups-of-controls))
         #_(ui-popup
            (merge
             {:header "Back one sentence."
              :content ""
              :trigger (ui-button
                        {:icon i/angle-double-left-icon
                         :onClick (fn [_]
                                    (when-let [player (player-atom/get-player)]
                                      (.setTime player prev-segment-start-plus-ms)))
                         :disabled (nil? prev-segment-start)})}
             common-to-controls/common-options-for-popups-of-controls))
         #_(ui-popup
            (merge
             {:header "Back one word."
              :content ""
              :trigger (ui-button
                        {:icon i/angle-left-icon
                         :onClick (fn [_]
                                    (when-let [player (player-atom/get-player)]
                                      (.setTime player prev-word-start-plus-ms)))
                         :disabled (nil? prev-word-start)})}
             common-to-controls/common-options-for-popups-of-controls))
         (ui-popup
          (merge
           {:header "Back to beginning of sentence."
            :content "Repeat to skip back to previous sentences."
            :trigger (ui-button
                      {:icon i/reply-icon
                       :onClick (fn [_]
                                  (when-let [player (player-atom/get-player)]
                                    (if (and prev-segment-start
                                             (.isPlaying player) ;; if we're playing the beginning of the segment
                                                                 
                                             (> 1.0 (- (.getCurrentTime (player-atom/get-player))
                                                       current-segment-start-plus-ms)))
                                      ;;go back to the previous segment
                                      (.setTime player prev-segment-start-plus-ms)
                                      (.setTime player current-segment-start-plus-ms))
                                    (.play player)))
                       :disabled (nil? current-segment-start)})}
           common-to-controls/common-options-for-popups-of-controls))
         #_(ui-popup
            (merge
             {:header "Back to start of word and play."
              :content ""
              :trigger (ui-button
                        {:icon i/reply-icon
                         :onClick
                         (fn [_]
                           (when-let [player (player-atom/get-player)]
                             (.setTime player current-word-start-plus-ms)
                             (.play player)))
                         :disabled (nil? current-word-start)})}
             common-to-controls/common-options-for-popups-of-controls))

         (ui-popup
          (merge
           {:header "Play/Pause"
            :content ""
            :trigger (ui-button
                      {:icon (if (= doing :playing) i/pause-icon i/play-icon)
                       :onClick (fn [_]
                                  (when (player-atom/get-player)
                                    (if (= doing :playing) (.pause (player-atom/get-player)) (.play (player-atom/get-player)))))
                       :labelPosition "left"
                       :label {:pointing "right"
                               :content (dom/span
                                         (dom/span :#player-time (time-float-to-string 0 (.getDuration (player-atom/get-player))))
                                         " of "
                                         (time-float-to-string (.getDuration (player-atom/get-player)) (.getDuration (player-atom/get-player))))}})}
           common-to-controls/common-options-for-popups-of-controls))
         (ui-popup
          (merge
           {:header (str "Autopause: " (if any-segment? (str "Don't pause after ANY sentences.") (str "Pause after ALL sentences.")))
            :content (str "If ANY autopause after sentence is on, clicking will turn ALL autopauses off. If NONE are, clicking will autopause after EVERY sentence. You can also turn on and off autopause individually with the button at the end of each sentence.")
            :trigger (ui-button
                      {;; :label {:pointing "left" :content (if any-autopause? "on" "off")}
                      ;; :labelPosition "right"
                       :size "tiny"
                       :icon (fragment
                              (ui-icon {:name i/pause-icon})
                              (ui-icon {:name i/clock-icon}))
                       :onClick
                       (fn [& _args]
                         (comp/transact!
                          this
                          `[(com.submerged-structure.mutations/toggle-autopause-for-transcript {:transcript/id ~id})]))
                       :positive any-segment?})}
           common-to-controls/common-options-for-popups-of-controls))
        
         #_(ui-popup
            (merge
             {:header "Forward one word and play."
              :content ""
              :trigger (ui-button
                        {:icon i/share-icon
                         :onClick (fn [_]
                                    (when-let [player (player-atom/get-player)]
                                      (.setTime player next-word-start-plus-ms)
                                      (.play player)))
                         :disabled (nil? next-word-start)})}
             common-to-controls/common-options-for-popups-of-controls))
         #_(ui-popup
            (merge
             {:header "Forward one word."
              :content ""
              :trigger (ui-button
                        {:icon i/angle-right-icon
                         :onClick (fn [_]
                                    (when-let [player (player-atom/get-player)]
                                      (.setTime player next-word-start-plus-ms)))
                         :disabled (nil? next-word-start)})}
             common-to-controls/common-options-for-popups-of-controls))
         (ui-popup
          (merge
           {:header "Forward one sentence."
            :content ""
            :trigger (ui-button
                      {:icon i/share-icon
                       :onClick (fn [_]
                                  (when-let [player (player-atom/get-player)]
                                    (.setTime player next-segment-start-plus-ms)
                                    (.play player)))
                       :disabled (nil? next-segment-start)})}
           common-to-controls/common-options-for-popups-of-controls))
         #_(ui-popup
            (merge
             {:header "Fast forward 15 seconds."
              :content ""
              :trigger (ui-button
                        {:icon i/chevron-right-icon
                         :onClick (fn [_]
                                    (when-let [player (player-atom/get-player)]
                                      (.skip player 15)))
                 ;only gets updated when the ui-period changes but that's OK
                         :disabled (let [time-remaining (- (.getDuration (player-atom/get-player)) (.getCurrentTime (player-atom/get-player)))]
                                     (< time-remaining 15))})}
             common-to-controls/common-options-for-popups-of-controls)))
        (dom/span
         :.item
         {}
         (ui-popup
          (merge
           {:content "Toggle the transcript automatically scrolling to current spoken word."
            :trigger (ui-button
                      {:icon i/crosshairs-icon
                       :onClick (fn [& _args]
                                  (comp/transact! this `[(com.submerged-structure.mutations/toggle-transcript-scroll-to-active {})]))
                       :positive scroll-to-active
                       :labelPosition "right"
                       :label {:pointing "left"
                               :content (str "Auto scroll " (if scroll-to-active "on" "off"))}})}
           common-to-controls/common-options-for-popups-of-controls)))
        (dom/span
         :.item
         {}
         (ui-translation-controls language-controls))
        (dom/span
         :.item
         {} (ui-morphological-info-control morphological-info-grid)))))))

(def ui-player-controls (comp/factory PlayerControls))




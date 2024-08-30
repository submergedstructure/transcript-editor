(ns com.submerged-structure.confidence-to-color
  (:require [goog.color :as color]
            [com.fulcrologic.fulcro.dom :as dom  :refer [div p span h3]]))

(def color-transitions [0.0 0.5 0.8 1.0]); expected to be in ascending order
(def colors ["#FF0000" ; light red
             "#FFA500" ; orange
             "#008000" ; green
             "#000000"]) ; black

(defn color-zones []
  (let [confidence-zones (partition 2 1 color-transitions)
        color-pairs (partition 2 1 colors)]
    (map (fn [[color-zone-low color-zone-high] [color-low color-high]]
           {:color-zone-low color-zone-low
            :color-zone-high color-zone-high
            :color-low color-low
            :color-high color-high
            :factor-in-zone (fn [confidence] (/ (- confidence color-zone-low) (- color-zone-high color-zone-low)))
            :in-zone? (fn [confidence] (<= confidence color-zone-high))})
         confidence-zones
         color-pairs)))

(defn confidence-to-color
  "Converts a confidence value (0.0 to 1.0) to a CSS color string.
    Maps confidence: 0.0-0.5 -> dark red to orange, 0.5-0.8 -> orange to green,
    0.8-1.0 -> green to black."
  [confidence]
  (->> (filter #((:in-zone? %) confidence) (color-zones))
       (first)
       ((fn [{:keys [color-low color-high factor-in-zone]}]
          (color/rgbArrayToHex (color/blend (color/hexToRgb color-high) (color/hexToRgb color-low) (factor-in-zone confidence)))))))

(defn confidence-to-style
  "Converts a confidence value (0.0 to 1.0) to an html attribute object."
  [confidence]
  {:color (confidence-to-color confidence)})

(defn confidence-key []
  (div
   (h3 :.ui.center.aligned.header "AI's confidence of each word: ")
   (p :.ui.justified.container#confidence-key
      (for [c (map #(js/Number.parseFloat (.toFixed % 2)) (range 1.0 -0.05 -0.05))] ; make sure that we get floats to 2 decimal places
        (span {:style (confidence-to-style c)} (str c "  "))))
   (p :.ui.center.aligned.container "(1.0 = very high 0.0 = none)")))

(comment
  ;; Confidence at 0 (expected to be dark red)
  (confidence-to-color 0.0)

  ;; Confidence at 0.25 (expected to be between dark red and orange)
  (confidence-to-color 0.25)

  ;; Confidence at 0.5 (expected to transition to orange)
  (confidence-to-color 0.5)

  ;; Confidence at 0.65 (expected to be between orange and green)
  (confidence-to-color 0.65)

  ;; Confidence at 0.8 (expected to transition to green)
  (confidence-to-color 0.8)

  ;; Confidence at 0.9 (expected to be between green and black)
  (confidence-to-color 0.9)

  ;; Confidence at 1.0 (expected to be black)
  (confidence-to-color 1.0))


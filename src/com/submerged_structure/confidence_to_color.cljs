(ns com.submerged-structure.confidence-to-color)

(defn rgb-to-hex [rgb]
  (apply str "#" (map #(.padStart (.toString % 16) 2 "0") rgb)))

(comment
  (.toString 0 16)
  ;; => "0"

  (apply str "#" (map #(.padStart (.toString % 16) 2 "0") [0 255 0]))
  ;; => "00"


  (rgb-to-hex [255 0 0])
    ;; => "#FF0000"
  (rgb-to-hex [0 255 0])
  ;; => "#0ff0"

    ;; => "#00FF00"
  (rgb-to-hex [0 0 255])
    ;; => "#0000FF"
  )

(defn interpolate [r1 g1 b1 r2 g2 b2 t]
  [(int (+ (* (- r2 r1) t) r1))
   (int (+ (* (- g2 g1) t) g1))
   (int (+ (* (- b2 b1) t) b1))])


;; this function was written by copilot but is overly complex and doesn't produce the colours I want.
(defn confidence-to-color' [confidence]
  (let [; Define the RGB values for the gradient
        gradient [(interpolate 255 0 0 255 105 0 confidence) ; From red to orange
                  (interpolate 255 165 0 255 105 0 confidence) ; From dark orange to orange
                  (interpolate 255 105 0  0 100 0 confidence) ; From orange to green
                  (interpolate 0 100 0 0 128 0 confidence) ; From green to dark green
                  (interpolate 0 128 0 0 0 0 confidence)   ; From dark green to black
        ]
        ; Calculate RGB based on confidence
        rgb (gradient (js/Math.floor (* 4 confidence)))
        ; Convert RGB to hexadecimal
        hex (rgb-to-hex rgb)
        ; Calculate background color based on brightness
        brightness (/ (+ (* 0.299 (nth rgb 0)) (* 0.587 (nth rgb 1)) (* 0.114 (nth rgb 2))) 255)
        bg-color (if (< brightness 0.8) "white" "lightgrey")]
    [hex bg-color]))

(defn confidence-to-color
  "Converts a continuous `confidence` value between 0 and 1, to a color and background color pair.
   Colour is on a gradient from red through orange and green and then black, with a white background
   or with a background color that enhances contrast and readability."
  [confidence]
  (let [[hue lightness]
        (condp >= confidence
          0.2 [0 (* confidence 200)] ; Red 
          0.4 [30 (* (- confidence 0.2) 200)] ; Orange
          0.6 [60 (* (- confidence 0.4) 200)] ; Yellow
          0.8 [120 (* (- confidence 0.6) 200)] ; Green
          [0 (* (- 100 (- confidence 0.8) 200))]) ;
        
        hsl (str "hsl(" hue ", 100%, " lightness "%)")]
    [hsl "white"]))

(comment
  ;; Test with maximum confidence
  (confidence-to-color 1.0)
  ;; => ["#000000" "white"]

  ; Black with white background

  ;; Test with minimum confidence
  (confidence-to-color 0.0)
  ;; => ["#ff0000" "white"]

  ; Dark green with white background

  ;; Test with mid-range confidence
  (confidence-to-color 0.5)  ; Should provide a middle value in the gradient spectrum
  )

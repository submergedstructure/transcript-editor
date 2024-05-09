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

(defn confidence-to-color [confidence]
  (let [; Define the RGB values for the gradient
        gradient [(interpolate 255 0 0 255 105 0 confidence) ; From red to orange
                  (interpolate 255 105 0 255 165 0 confidence) ; From orange to dark orange
                  (interpolate 255 165 0  0 128 0 confidence) ; From dark orange to green
                  (interpolate 0 128 0 0 100 0 confidence) ; From green to dark green
                  (interpolate 0 100 0 0 0 0 confidence)   ; From dark green to black
        ]
        ; Calculate RGB based on confidence
        rgb (gradient (js/Math.floor (* 4 confidence)))
        ; Convert RGB to hexadecimal
        hex (rgb-to-hex rgb)
        ; Calculate background color based on brightness
        brightness (/ (+ (* 0.299 (nth rgb 0)) (* 0.587 (nth rgb 1)) (* 0.114 (nth rgb 2))) 255)
        bg-color (if (< brightness 0.6) "white" "lightgrey")]
    [hex bg-color]))

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

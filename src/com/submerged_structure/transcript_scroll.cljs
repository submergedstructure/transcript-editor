(ns com.submerged-structure.transcript-scroll
  (:require [com.submerged-structure.components.player :as player]))


(defn scroll-element-to-vertical-middle
  "Assuming player is a sticky at the top of the screen, scroll element 
   to the vertical center of the screen below the player."
  [dom-element]
  (let [element-y-in-viewport (.. dom-element
                                  (getBoundingClientRect)
                                  -top)
        current-top-of-viewport (.. js/window -pageYOffset)
        element-y-in-document (+ element-y-in-viewport current-top-of-viewport)
        scroll-to (- element-y-in-document
                     (/ (+ (player/player-height) js/window.innerHeight) 2))]
    (js/window.scrollTo  (clj->js {:left 0
                                   :top scroll-to
                                   :behavior "smooth"}))))

(defn scroll-to-active-element-after-time-out [ & {:keys [fallback-to-segment]
                                                  :or {fallback-to-segment true}}]
  (js/setTimeout
   (fn []
     (when-let [active-element
                (or (js/document.querySelector ".word.active")
                    (and fallback-to-segment 
                         (js/document.querySelector ".segment-transcription-and-translation.active")))]
       (scroll-element-to-vertical-middle active-element)))
   0))
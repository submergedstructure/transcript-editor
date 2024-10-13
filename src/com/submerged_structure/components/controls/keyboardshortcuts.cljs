(ns com.submerged-structure.components.controls.keyboardshortcuts)

(defn handle-keydown [event]
  (let [key (.-key event)]
    (case key
      ;; ids in com.submerged-structure.components.controls.player-controls
      "a"
      (when-let [button (.getElementById js/document "sentence-back")]
        (.click button))

      "s"
      (when-let [button (.getElementById js/document "play-pause")]
        (.click button))

      "d"
      (when-let [button (.getElementById js/document "sentence-forward")]
        (.click button))
      "w"
      (when-let [button (.getElementById js/document "autopause-all")]
        (.click button)))))
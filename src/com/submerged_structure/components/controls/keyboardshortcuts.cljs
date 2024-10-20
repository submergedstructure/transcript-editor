(ns com.submerged-structure.components.controls.keyboardshortcuts)

(def shortcut-key->button-id
  {"a" "sentence-back"
   "s" "play-pause"
   "d" "sentence-forward"
   "f" "progressive-reveal-segments-upto-current"
   "w" "autopause-all"
   "e" "reset-reveal-state-of-all"})

(defn handle-keydown [event]
  (let [key (.-key event)]
    (when-let [button-id (get shortcut-key->button-id key)]
      (when-let [button (.getElementById js/document button-id)]
        (.click button)))))
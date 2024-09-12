(ns com.submerged-structure.player-atom)

(defonce player-local (atom {:player nil}))

(defn ^js get-player []
  (:player @player-local))

(defn set-player! [player]
  (swap! player-local assoc :player player))

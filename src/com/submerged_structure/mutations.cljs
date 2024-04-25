(ns com.submerged-structure.mutations
  "Client-side mutations.
   
   'Server-side' mutations could normally be also defined here, only with
   `#?(:clj ...)` but here even the 'server' runs in the browser so we must
   define them in another ns, which we do in `...pathom`."
  (:require
   [com.fulcrologic.fulcro.mutations :refer [defmutation]]
   #_[com.wsscode.pathom3.connect.operation :as pco]))

(defmutation update-transcript-current-word [{:transcript/keys [id current-word]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id id :transcript/current-word] current-word))
  (remote [_] false))

(defmutation update-ui-player-doing [{:keys [transcript/id ui-player/doing]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id id :ui-player/doing] doing)))
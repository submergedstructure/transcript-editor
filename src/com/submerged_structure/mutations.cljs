(ns com.submerged-structure.mutations
  "Client-side mutations.
   
   'Server-side' mutations could normally be also defined here, only with
   `#?(:clj ...)` but here even the 'server' runs in the browser so we must
   define them in another ns, which we do in `...pathom`."
  (:require
   [com.fulcrologic.fulcro.mutations :refer [defmutation]]))

(defmutation create-random-thing [{:keys [tmpid]}]
  (action [{:keys [state] :as env}]
          (swap! state assoc-in [:new-thing tmpid] {:id tmpid, :txt "A new thing!"}))
  (remote [_] true))

(defmutation update-transcript-current-time [{:keys [transcript/id current-time]}]
  (action [{:keys [state] :as env}]
          (swap! state assoc-in [:transcript/id id :transcript/current-time] current-time))
  (remote [_] false))
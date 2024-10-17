(ns com.submerged-structure.mutations.load
  (:require
   [com.fulcrologic.fulcro.mutations :refer [defmutation]]
   [com.submerged-structure.mutations.common :as common]
   [com.fulcrologic.fulcro.data-fetch :as df]
   
   [com.submerged-structure.components.transcript-page :as transcript]))


(defmutation update-transcript-duration [{:transcript/keys [duration]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id (common/get-current-transcript-id-from-state @state) :transcript/duration] duration))
  (remote [_] false))


(defmutation load-transcript [{:transcript/keys [id]}]
  (action [{:keys [app state]}]
          (let [next-transcript-ident [:transcript/id id]]
            (swap! state assoc-in [:root/current-transcript] next-transcript-ident)
            (df/load! app next-transcript-ident transcript/TranscriptPage)))
  (remote [_] false))
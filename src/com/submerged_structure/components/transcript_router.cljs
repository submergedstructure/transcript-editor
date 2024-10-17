(ns com.submerged-structure.components.transcript-router
  (:require [com.fulcrologic.fulcro.components :as comp]

            [com.submerged-structure.components.transcript-page :as transcript]

            [com.fulcrologic.fulcro.routing.dynamic-routing :as dr]
            
            [com.submerged-structure.components.home :as home]))

(dr/defrouter TranscriptRouter [_ _]
  {:router-targets [home/Home transcript/TranscriptPage]})

(def ui-transcript-router (comp/factory TranscriptRouter))
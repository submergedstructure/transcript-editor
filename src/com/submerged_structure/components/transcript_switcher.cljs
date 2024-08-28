(ns com.submerged-structure.components.transcript-switcher
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.semantic-ui.modules.dropdown.ui-dropdown :refer [ui-dropdown]]
            [com.fulcrologic.semantic-ui.modules.dropdown.ui-dropdown-item :refer [ui-dropdown-item]]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]))


(defsc TranscriptSwitcherOption
  "db only, for normalisation"
  [_this {}]
  {:ident :transcript/id
   :query [:transcript/id :transcript/label]
   :initial-state {}})

#_(def ui-transcript-switcher-option (comp/computed-factory TranscriptSwitcherOption {:keyfn :transcript/id}))

(defsc TranscriptSwitcher [this {:transcript-switcher/keys [all-transcripts]}]
  {:query [{:transcript-switcher/all-transcripts (comp/get-query TranscriptSwitcherOption)}]
   :ident :transcript-switcher/all-transcripts
   :initial-state (fn [_] {:transcript-switcher/all-transcripts
                           (comp/get-initial-state TranscriptSwitcherOption {})})}
  (let [current-transcript (comp/get-computed this :current-transcript)]
    (ui-popup
     {:content "Select a transcript to view"
      :size "large"
      :position "bottom left"
      :trigger
      (ui-dropdown
       {:as "h2"
        :color "black"
        :basic true
        :fluid true
        :direction "left"
        :upward false
        :scrolling true
        :options (mapv (fn [{:transcript/keys [id label]}] (ui-dropdown-item {:text label :value id :key id :active (= id (comp/get-computed this :current-transcript))})) all-transcripts)

        :onChange (fn [_ev data]
                    (js/console.log "TranscriptSwitcher onChange" data)
                    (comp/transact! this `[(com.submerged-structure.mutations/load-transcript {:transcript/id ~(.-value data)})]))
        :text (:transcript/label (first (filter #(= (:transcript/id %) current-transcript) all-transcripts)))})})))

(def ui-transcript-switcher (comp/computed-factory TranscriptSwitcher))

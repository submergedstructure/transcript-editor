(ns com.submerged-structure.components.segment
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div span]]

            [com.fulcrologic.fulcro.components :as comp :refer [defsc fragment]]
            [com.submerged-structure.components.translation :as translation]
            [com.submerged-structure.components.word :as word]

            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
            [com.fulcrologic.semantic-ui.elements.button.ui-button :refer [ui-button]]
            [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
            [com.fulcrologic.semantic-ui.icons :as i]

            [com.submerged-structure.components.controls.common :as common-to-controls]

            [com.submerged-structure.player-atom :as player-atom]))


(defsc Segment [this {:segment/keys [id words translations start text autopause?]} transcript-display-type]
  {:ident :segment/id
   :initial-state (fn [_] {:segment/words (comp/get-initial-state word/Word {})
                           :segment/translations (comp/get-initial-state translation/Translation {})})
   :query [:segment/id :segment/start :segment/end :segment/text :segment/autopause?
           {:segment/words (comp/get-query word/Word)}
           {:segment/translations (comp/get-query translation/Translation)}]}
  (div :.segment-transcription-and-translation
       (span :.transcription
             (ui-popup
              (merge
               {:header "Play from beginning of sentence"
                :content ""
                :trigger (ui-button
                          {:icon i/play-icon
                           :size "mini"
                           :style {:verticalAlign "middle"
                                   :marginRight "0.7em"}
                           :compact true
                           :onClick (fn [_]
                                      (when-let [player (player-atom/get-player)]
                                        (.setTime player (+ start 0.001))
                                        (.play player)))})}
               common-to-controls/common-options-for-popups-of-controls))
             (map #(word/ui-word % transcript-display-type) words)
             (ui-popup
              (merge
               {:header "Auto pause at end of sentence"
                :content ""
                :trigger (ui-button
                          {:size "mini"
                           :style {:verticalAlign "middle"
                                   :marginLeft "0.5em"}
                           :compact true
                           :icon (fragment
                                  (ui-icon {:name i/pause-icon})
                                  (ui-icon {:name i/clock-icon}))



                           :positive autopause?
                           :onClick (fn [_]
                                      (comp/transact!
                                       this
                                       `[(com.submerged-structure.mutations.controls/toggle-autopause-for-segment {:segment/id ~id})]))})}
              common-to-controls/common-options-for-popups-of-controls))
             (map (fn [translation] (translation/ui-translation translation {:segment/transcription-text text})) translations))))



(def ui-segment (comp/computed-factory Segment {:keyfn :segment/id}))
(ns com.submerged-structure.components.segment
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div span]]

            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.submerged-structure.components.translation :as translation]
            [com.submerged-structure.components.word :as word]

            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
            [com.fulcrologic.semantic-ui.elements.button.ui-button :refer [ui-button]]
            [com.fulcrologic.semantic-ui.icons :as i]

            [com.submerged-structure.components.controls.common :as common-to-controls]

            [com.submerged-structure.player-atom :as player-atom]

            [com.submerged-structure.components.segment-morphological-info :as segment-morphological-info]))


(defsc Segment [_this {:segment/keys [words translations start text] :>/keys [segment-morphological-info]} transcript-display-type]
  {:ident :segment/id
   :initial-state (fn [_] {:segment/words (comp/get-initial-state word/Word {})
                           :segment/translations (comp/get-initial-state translation/Translation {})
                           :>/segment-morphological-info (comp/get-initial-state segment-morphological-info/SegmentMorphologicalInfo {})})
   :query [:segment/id :segment/start :segment/end :segment/text
           {:segment/words (comp/get-query word/Word)}
           {:segment/translations (comp/get-query translation/Translation)}
           {:>/segment-morphological-info (comp/get-query segment-morphological-info/SegmentMorphologicalInfo)}]}
  (div :.segment-transcription-and-translation
       (segment-morphological-info/ui-segment-morphological-info segment-morphological-info)
       (span :.transcription
             (ui-popup
              (merge
               {:header "Play from beginning of sentence"
                :content ""
                :trigger (ui-button
                          {:icon i/play-icon
                           :size "mini"
                           :style {:vertical-align "middle"
                                   :margin-right "0.7em"}
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
                          {:icon i/pause-icon
                           :size "mini"
                           :style {:vertical-align "middle"
                                   :margin-left "0.5em"}
                           :compact true
                           :onClick (fn [_]
                                      (when-let [player (player-atom/get-player)]
                                        (.pause player)))})}
               common-to-controls/common-options-for-popups-of-controls)))
       (map (fn [translation] (translation/ui-translation translation {:segment/transcription-text text})) translations)
       
       )) ;; space between words is language dependent may need to change to support eg. Asian languages.



(def ui-segment (comp/computed-factory Segment {:keyfn :segment/id}))
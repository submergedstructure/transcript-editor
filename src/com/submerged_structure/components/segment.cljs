(ns com.submerged-structure.components.segment
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div span]]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.submerged-structure.components.translation :as translation]
            [com.submerged-structure.components.word-with-morphological-features-popup :as word-with-morphological-features-popup]))


(defsc Segment [_this {:segment/keys [words translations text]} computed]
  {:ident :segment/id
   :initial-state (fn [_] {:segment/words (comp/get-initial-state word-with-morphological-features-popup/WordWithMorphPopup {})
                           :segment/translations (comp/get-initial-state translation/Translation {})})
   :query [:segment/id :segment/start :segment/end :segment/text
           {:segment/words (comp/get-query word-with-morphological-features-popup/WordWithMorphPopup)}
           {:segment/translations (comp/get-query translation/Translation)}]}
  (div :.segment-transcription-and-translation
       (span :.transcription
             (interleave
              (map #(word-with-morphological-features-popup/ui-word-with-morph-popup % computed) words) (repeat " ")))
       (map (fn [translation] (translation/ui-translation translation {:segment/transcription-text text})) translations))) ;; space between words is language dependent may need to change to support eg. Asian languages.


(def ui-segment (comp/computed-factory Segment {:keyfn :segment/id}))
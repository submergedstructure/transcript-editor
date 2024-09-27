(ns com.submerged-structure.components.segment
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div span]]
            
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.submerged-structure.components.translation :as translation]
            [com.submerged-structure.components.word :as word]
            [com.submerged-structure.components.segment-morphological-info :as segment-morphological-info]))


(defsc Segment [_this {:segment/keys [words translations text] :>/keys [segment-morphological-info]} computed]
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
             (map #(word/ui-word % computed) words))
       (map (fn [translation] (translation/ui-translation translation {:segment/transcription-text text})) translations)
       
       )) ;; space between words is language dependent may need to change to support eg. Asian languages.



(def ui-segment (comp/computed-factory Segment {:keyfn :segment/id}))
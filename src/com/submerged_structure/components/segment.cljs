(ns com.submerged-structure.components.segment
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div span]]
            
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.submerged-structure.components.translation :as translation]
            [com.submerged-structure.components.word :as word-with-morphological-features-popup]
            [com.submerged-structure.components.word-morphological-info-grid :as word-morphological-info-grid]))


(defsc Segment [_this {:segment/keys [words translations text] :>/keys [morphological-info-grid]} computed]
  {:ident :segment/id
   :initial-state (fn [_] {:segment/words (comp/get-initial-state word-with-morphological-features-popup/Word {})
                           :segment/translations (comp/get-initial-state translation/Translation {})
                           :>/morphological-info-grid (comp/get-initial-state word-morphological-info-grid/WordMorphologicalInfoGrid {})})
   :query [:segment/id :segment/start :segment/end :segment/text
           {:segment/words (comp/get-query word-with-morphological-features-popup/Word)}
           {:segment/translations (comp/get-query translation/Translation)}
           {:>/morphological-info-grid (comp/get-query word-morphological-info-grid/WordMorphologicalInfoGrid)}]}
  (div :.segment-transcription-and-translation
       (word-morphological-info-grid/ui-word-morphological-info-grid morphological-info-grid)
       (span :.transcription
             (interleave
              (map #(word-with-morphological-features-popup/ui-word % computed) words) (repeat " ")))
       (map (fn [translation] (translation/ui-translation translation {:segment/transcription-text text})) translations)
       
       )) ;; space between words is language dependent may need to change to support eg. Asian languages.



(def ui-segment (comp/computed-factory Segment {:keyfn :segment/id}))
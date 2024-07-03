(ns com.submerged-structure.mutations
  "Client-side mutations.
   
   'Server-side' mutations could normally be also defined here, only with
   `#?(:clj ...)` but here even the 'server' runs in the browser so we must
   define them in another ns, which we do in `...pathom`."
  (:require
   [com.fulcrologic.fulcro.mutations :refer [defmutation]]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
   [com.fulcrologic.fulcro.data-fetch :as df]
   [com.submerged-structure.ui :as ui]))

(defn get-current-transcript-id-from-state
  [state-deref]
  (get-in state-deref [:root/current-transcript 1]))

(defn get-current-segment-word-tree-from-state
  [state-deref]
  (get-in
   (fdn/db->tree
    [#:root{:current-transcript
            [:transcript/id
             #:transcript{:segments
                          [:segment/id :segment/start :segment/end
                           #:segment{:words [:word/id :word/start :word/end]}]}]}]
    state-deref state-deref)
   [:root/current-transcript :transcript/segments]))

(defn get-word-from-segment-word-tree [segment-word-tree {:keys [segment-idx word-idx]}]
  (get-in segment-word-tree [segment-idx :segment/words word-idx]))

(defn find-index-of-last-obj-with-ns-start-time-less-than-or-equal-t
  "Returns the index of last object in objs with a start time less than or equal to t.
   objs is a list of map with a key start namespaced by `ns`."
  [objs ns t]
  (last (keep-indexed (fn [idx obj] (when (<= ((keyword ns "start") obj) t) idx)) objs)))

(comment 
  (let [words [#:word{:id "2a39170a-6b10-4d71-8f3b-eba33c5427f8", :start 380.499, :end 380.919}
               #:word{:id "d0f7c756-8edd-46ed-a898-e85ceaf0f19b", :start 380.999, :end 381.54}
               #:word{:id "a46da4f6-d836-40f8-a507-d2989d317d69", :start 381.58, :end 381.66}
               #:word{:id "af58afb5-5eb3-4241-8ccc-a722439902c3", :start 381.7, :end 382.02}
               #:word{:id "93ef0fb7-5694-4752-a84b-303115d79811", :start 382.08, :end 382.18}]]
    (get-in words [(find-index-of-last-obj-with-ns-start-time-less-than-or-equal-t words "word" 382.0)]))
  ;; => #:word{:id "d0f7c756-8edd-46ed-a898-e85ceaf0f19b", :start 380.999, :end 381.54}

)
  

(defn find-index-of-last-started-segment-and-word [segment-word-tree t]
  (let [segment-idx (find-index-of-last-obj-with-ns-start-time-less-than-or-equal-t
                     segment-word-tree
                     "segment"
                     t)]
    (if segment-idx
      {:segment-idx segment-idx
       :word-idx (find-index-of-last-obj-with-ns-start-time-less-than-or-equal-t
                  (get-in segment-word-tree [segment-idx :segment/words])
                  "word"
                  t)}
      {:segment-idx nil :word-idx nil} ;; no segment started yet; or no time stamps
      )))

(defn find-next-word-that-has-time-stamp [segment-word-tree segment-word-idx]
  (loop [idx-next-word-to-check (update segment-word-idx :word-idx inc)]
   (let [next-word-to-check (get-word-from-segment-word-tree segment-word-tree idx-next-word-to-check)]
     (if next-word-to-check
       (if (:word/start next-word-to-check)
         next-word-to-check
         (recur (update idx-next-word-to-check :word-idx inc))) ;; try next word
       (let [next-segment-idx (inc (:segment-idx idx-next-word-to-check))] ;; is there a next segment?
         (if (nth segment-word-tree next-segment-idx)
           (recur {:segment-idx next-segment-idx :word-idx 0}) ;; try first word of next segment
           nil ; no more words with time stamps
           ))
       ))))

(defn find-start-of-next-word-or-return-x-when-none [segment-word-tree segment-word-idx x]
  (let [next-word (find-next-word-that-has-time-stamp segment-word-tree segment-word-idx)]
    (if next-word
      (:word/start next-word)
      x)))

(comment
  (def segment-word-tree-short [#:segment{:id "91bf9164-8b55-4f32-abda-839100183a50",
                                          :start 0.449,
                                          :end 2.311,
                                          :words
                                          [#:word{:id "fb663d8a-947e-4a60-80e9-44d95217d431", :start 0.449, :end 0.89}
                                           #:word{:id "a65e89e1-4122-4bed-b3b3-89a06c8b51ad", :start 0.99, :end 1.11}
                                           #:word{:id "ab435781-ad27-45ca-b558-11b0b071774e", :start 1.17, :end 2.311}]}
                                #:segment{:id "989d2f7d-ec22-41ca-971b-060ad31de92d",
                                          :start 2.331,
                                          :end 6.293,
                                          :words
                                          [#:word{:id "1d0a2f01-b3dd-45c1-a911-04e6aa7da3c6", :start 2.331, :end 2.491}
                                           #:word{:id "870e7926-6060-48ee-a5f2-336aecbfc71c", :start 2.531, :end 3.031}
                                           #:word{:id "6c0aa568-869c-49d3-833f-fc9b637e4508", :start 3.091, :end 3.371}
                                           #:word{:id "605160e4-f0e2-442d-a9f4-01ac509ff2c9", :start 3.431, :end 3.812}
                                           #:word{:id "3900cf7a-db28-4063-82a0-2eec7cc36587", :start 3.872, :end 4.532}
                                           #:word{:id "5a22677a-2745-400d-8d2e-478fd29f3eea", :start 4.592, :end 4.792}
                                           #:word{:id "5d9a16bc-f536-4d4f-852c-44c2c8574ad4", :start 4.852, :end 6.293}]}])
  (find-index-of-last-started-segment-and-word segment-word-tree-short 0.0)
  ;; => {:segment-idx nil, :word-idx nil}
  (find-index-of-last-started-segment-and-word segment-word-tree-short 2.0)
  ;; => {:segment-idx 0, :word-idx 2}

  (def next-word (find-next-word-that-has-time-stamp segment-word-tree-short {:segment-idx 0, :word-idx 2})
                 ;; => #:word{:id "1d0a2f01-b3dd-45c1-a911-04e6aa7da3c6", :start 2.331, :end 2.491}
    )

  (get-word-from-segment-word-tree segment-word-tree-short {:segment-idx 1, :word-idx 0})
;; => #:word{:id "1d0a2f01-b3dd-45c1-a911-04e6aa7da3c6", :start 2.331, :end 2.491}
  (get-word-from-segment-word-tree segment-word-tree (update segment-word-idx :word-idx inc))



  (find-start-of-next-word-or-return-x-when-none segment-word-tree-short {:segment-idx 0, :word-idx 2} :not-found)
  ;; => 2.331



  (find-index-of-last-started-segment-and-word segment-word-tree-short 2.5)
  ;; => {:segment-idx 1, :word-idx 0}
  (find-index-of-last-started-segment-and-word segment-word-tree-short 3.1)
  ;; => {:segment-idx 1, :word-idx 2}
  )
  

(defn find-current-word-and-period-good-for
  "Return the id of current word to highlight or nil if no word to highlight.
   And return the period for t outside which this function should be called again."
  [state-deref t]
  (js/console.log "find-current-word-and-period-good-for" state-deref t)
  (let [transcript-id (get-current-transcript-id-from-state state-deref)
        segment-word-tree (get-current-segment-word-tree-from-state state-deref)
        segment-word-idx (find-index-of-last-started-segment-and-word segment-word-tree t)]
    (if (:segment-idx segment-word-idx)
      (let [last-started-word (get-word-from-segment-word-tree segment-word-tree segment-word-idx)]
             ; there is a started word
        (if (<= t (:word/end last-started-word))
          [(:word/id last-started-word) ; still in the word
           #:ui-period{:start (:word/start last-started-word)
                       :end (:word/end last-started-word)}]
               ; last started word finished
          [nil
           #:ui-period{:start (:word/end last-started-word)
                       :end (find-start-of-next-word-or-return-x-when-none
                             segment-word-tree
                             segment-word-idx
                             (get-in state-deref [:transcript/id transcript-id :transcript/duration]))}]))
      (let [next-word (find-next-word-that-has-time-stamp segment-word-tree {:segment-idx 0 :word-idx -1})] ;first word with time stamp
        (if next-word
          [nil
           #:ui-period{:start 0
                       :end (:word/start next-word)}]
          (do
            (js/console.error "No words with time stamps found in the transcript")
            [nil ; no words with time stamps
             #:ui-period{:start 0
                         :end (get-in state-deref [:transcript/id transcript-id :transcript/duration])}]))))))

(defmutation update-transcript-current-time
  "Sets the currently active word, if there is one, and also sets the start and end time of the time period that the word or pause covers."
  
  [{:transcript/keys [current-time]}]
  (action [{:keys [state]}]
          (let [transcript-id (get-current-transcript-id-from-state @state)
                [new-current-word-id current-period] (find-current-word-and-period-good-for @state current-time)
                last-current-word-id (get-in @state [:transcript/id transcript-id :transcript/current-word 1])]
            (do 
              (js/console.log "update-transcript-current-time" current-time last-current-word-id new-current-word-id current-period)
              (swap! state assoc-in [:transcript/id transcript-id :ui-period/start] (:ui-period/start current-period))
              (swap! state assoc-in [:transcript/id transcript-id :ui-period/end] (:ui-period/end current-period))
              (swap! state assoc-in [:transcript/id transcript-id :transcript/current-word] [:word/id new-current-word-id])
              ;; deactivate last word
              (when last-current-word-id
                (swap! state assoc-in [:word/id last-current-word-id :word/active] false))
              (when new-current-word-id
                ;; activate new active word
                (swap! state assoc-in [:word/id new-current-word-id :word/active] true)))))
  (remote [_] false))

(defmutation update-ui-player-doing [{:keys [ui-player/doing]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id (get-current-transcript-id-from-state @state) :ui-player/doing] doing)))


(defmutation update-transcript-duration [{:transcript/keys [duration]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id (get-current-transcript-id-from-state @state) :transcript/duration] duration))
  (remote [_] false))

(defmutation load-transcript [{:transcript/keys [id]}] 
    (action [{:keys [app state]}]
      (if id
        (let [next-transcript-ident [:transcript/id id]]
          (swap! state assoc-in [:root/current-transcript] next-transcript-ident)
          (df/load! app next-transcript-ident ui/Transcript)
          (swap! state assoc-in (concat next-transcript-ident [:ui-player/doing]) :loading))
        
        (df/load! app :root/current-transcript ui/Transcript)))
  (remote [_] false))

(defmutation toggle-transcript-scroll-to-active [_]
  (action [{:keys [state]}]
          (swap! state update-in [:transcript/id (get-current-transcript-id-from-state @state) :ui-player/scroll-to-active] not))
  (remote [_] false))

(comment
  ;;run (app/current-state app) in ns com.submerged-structure.client to get the state then:
  (def state-deref *1)
  (def segment-word-tree (get-current-segment-word-tree-from-state state-deref))

  (def segment-word-idx (find-index-of-last-started-segment-and-word segment-word-tree 0.))
  (get-word-from-segment-word-tree segment-word-tree segment-word-idx)
  (find-next-word-that-has-time-stamp segment-word-tree segment-word-idx)
  (find-next-word-that-has-time-stamp segment-word-tree {:segment-idx 0 :word-idx -1})
  
  (find-current-word-and-period-good-for state-deref 15.0)
  )
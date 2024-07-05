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

(defn get-word-from-segment-word-tree [segment-word-tree {segment-no :segment/no word-no :word/no}]
  (get-in segment-word-tree [segment-no :segment/words word-no]))         

(defn word-start->segment-word-no'
  [segment-word-tree]
  (map-indexed (fn [word-no-in-transcript word-noed] (assoc word-noed :transcript/word-no word-no-in-transcript))              
   (for [segment-no (range (count segment-word-tree))
         word-no (range (count (get-in segment-word-tree [segment-no :segment/words])))
         :let [segment (get-in segment-word-tree [segment-no])
               word (get-in segment [:segment/words word-no])]
         :when (:word/start word)]
    (assoc (select-keys word [:word/start :word/end :word/id])
           :segment/no segment-no
           :word/no word-no))))

(def word-start->segment-word-no
  "Take the segment word tree and no words and segments that have time stamps.
   Return a list of words with all keys needed later.
       :segment/no, :word/no, :transcript/word-no.
   (memoized)"
  (memoize word-start->segment-word-no'))


(defn find-last-word-started-before-t-with-added-segment-and-word-no-of-word [segment-word-tree t]
  (let [segment-word-idxs (word-start->segment-word-no segment-word-tree)]
    (last (take-while (fn [{:keys [:word/start] :as segment-word-idx}]
                        (when (<= start t) segment-word-idx))
                      segment-word-idxs))))


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
                                           #:word{:id "870e7926-6060-48ee-a5f2-336aecbfc71c"}
                                           #:word{:id "6c0aa568-869c-49d3-833f-fc9b637e4508", :start 3.091, :end 3.371}]}])
                                           

  (word-start->segment-word-no segment-word-tree-short)
  ;; => ({:word/id "fb663d8a-947e-4a60-80e9-44d95217d431",
  ;;      :word/start 0.449,
  ;;      :word/end 0.89,
  ;;      :segment/id "91bf9164-8b55-4f32-abda-839100183a50",
  ;;      :segment/no 0,
  ;;      :word/no 0,
  ;;      :transcript/word-no 0}
  ;;     {:word/id "a65e89e1-4122-4bed-b3b3-89a06c8b51ad",
  ;;      :word/start 0.99,
  ;;      :word/end 1.11,
  ;;      :segment/id "91bf9164-8b55-4f32-abda-839100183a50",
  ;;      :segment/no 0,
  ;;      :word/no 1,
  ;;      :transcript/word-no 1}
  ;;     {:word/id "ab435781-ad27-45ca-b558-11b0b071774e",
  ;;      :word/start 1.17,
  ;;      :word/end 2.311,
  ;;      :segment/id "91bf9164-8b55-4f32-abda-839100183a50",
  ;;      :segment/no 0,
  ;;      :word/no 2,
  ;;      :transcript/word-no 2}
  ;;     {:word/id "1d0a2f01-b3dd-45c1-a911-04e6aa7da3c6",
  ;;      :word/start 2.331,
  ;;      :word/end 2.491,
  ;;      :segment/id "989d2f7d-ec22-41ca-971b-060ad31de92d",
  ;;      :segment/no 1,
  ;;      :word/no 0,
  ;;      :transcript/word-no 3}
  ;;     {:word/id "6c0aa568-869c-49d3-833f-fc9b637e4508",
  ;;      :word/start 3.091,
  ;;      :word/end 3.371,
  ;;      :segment/id "989d2f7d-ec22-41ca-971b-060ad31de92d",
  ;;      :segment/no 1,
  ;;      :word/no 2,
  ;;      :transcript/word-no 4})


  (find-last-word-started-before-t-with-added-segment-and-word-no-of-word segment-word-tree-short 0.0)
  ;; => nil

  (find-last-word-started-before-t-with-added-segment-and-word-no-of-word segment-word-tree-short 0.5)
  ;; => {:word/start 0.449,
  ;;     :word/end 0.89,
  ;;     :word/id "fb663d8a-947e-4a60-80e9-44d95217d431",
  ;;     :segment/no 0,
  ;;     :word/no 0,
  ;;     :transcript/word-no 0}

  (get-word-from-segment-word-tree segment-word-tree-short {:segment/no 0, :word/no 0})
  ;; => #:word{:id "fb663d8a-947e-4a60-80e9-44d95217d431", :start 0.449, :end 0.89}

  (find-last-word-started-before-t-with-added-segment-and-word-no-of-word segment-word-tree-short 2.0)
  ;; => {:word/start 1.17,
  ;;     :word/end 2.311,
  ;;     :word/id "ab435781-ad27-45ca-b558-11b0b071774e",
  ;;     :segment/no 0,
  ;;     :word/no 2,
  ;;     :transcript/word-no 2}

  (get-word-from-segment-word-tree segment-word-tree-short {:segment/no 1, :word/no 0})
  ;; => #:word{:id "1d0a2f01-b3dd-45c1-a911-04e6aa7da3c6", :start 2.331, :end 2.491}
)

(defn changes-to-make-to-transcript-keys-in-local-db-when-time-changes
  "Return the id of current word to highlight or nil if no word to highlight.
   And return the period for t outside which this function should be called again."
  [state-deref t]
  (let [transcript-id (get-current-transcript-id-from-state state-deref)
        segment-word-tree (get-current-segment-word-tree-from-state state-deref)
        last-word-with-nos (find-last-word-started-before-t-with-added-segment-and-word-no-of-word segment-word-tree t)
        all-words-with-nos (word-start->segment-word-no segment-word-tree)
        first-word (first all-words-with-nos)
        transcript-duration (get-in state-deref [:transcript/id transcript-id :transcript/duration])
        next-word (nth all-words-with-nos (inc (:word/no last-word-with-nos)))
        this-segment-start (get-in segment-word-tree [(:segment/no last-word-with-nos) :segment/start])]
    (cond
      (and last-word-with-nos (<= t (:word/end last-word-with-nos))) ;; currently in word
      {:ui-current-segment/start this-segment-start
       :transcript/current-word [:word/id (:word/id last-word-with-nos)]
       :ui-period/start (:word/start last-word-with-nos)
       :ui-period/end (:word/end last-word-with-nos)}
      (and last-word-with-nos next-word) ;; currently in pause after this word
      {:ui-current-segment/start this-segment-start
       :transcript/current-word [:word/id nil]
       :ui-period/start (:word/end last-word-with-nos)
       :ui-period/end (:word/start next-word)}
      (and last-word-with-nos next-word) ;; currently in pause after last word
      {:ui-current-segment/start this-segment-start
       :transcript/current-word [:word/id nil]
       :ui-period/start (:word/end last-word-with-nos)
       :ui-period/end transcript-duration} ;; if this is last word then end of period is end of transcript
      first-word
      {:ui-current-segment/start this-segment-start
       :transcript/current-word [:word/id nil] ;; before first word
       :ui-period/start 0
       :ui-period/end (:word/start first-word)}
      :else ;; no timestamped words in transcript!!
      {:ui-current-segment/start this-segment-start
       :transcript/current-word[:word/id nil] ;; before first word
       :ui-period/start 0
       :ui-period/end transcript-duration})))

(comment
  ;;run (app/current-state app) in ns com.submerged-structure.client to get the state then:
  (def state-deref *1)
  (def segment-word-tree (get-current-segment-word-tree-from-state state-deref))
  (def all-words-with-nos (word-start->segment-word-no segment-word-tree))

  (find-last-word-started-before-t-with-added-segment-and-word-no-of-word segment-word-tree 0.993154)



  (changes-to-make-to-transcript-keys-in-local-db-when-time-changes state-deref 1.2)
  (def segment-word-no (find-last-word-started-before-t-with-added-segment-and-word-no-of-word segment-word-tree 0.0))
  (get-word-from-segment-word-tree segment-word-tree segment-word-no)

  (changes-to-make-to-transcript-keys-in-local-db-when-time-changes state-deref 15.0))

(defmutation update-transcript-current-time
  "Sets the currently active word, if there is one, and also sets the start and end time of the time period that the word or pause covers."
  
  [{:transcript/keys [current-time]}]
  (action [{:keys [state]}]
          (let [transcript-id (get-current-transcript-id-from-state @state)
                transcript-keys-to-update (changes-to-make-to-transcript-keys-in-local-db-when-time-changes @state current-time)
                last-current-word-id (get-in @state [:transcript/id transcript-id :transcript/current-word 1])]
            (do 
              (js/console.log "update-transcript-current-time" current-time last-current-word-id transcript-keys-to-update)
              (doall (map (fn [[k v]] (swap! state assoc-in (conj [:transcript/id transcript-id] k) v)) transcript-keys-to-update))
              ;; deactivate last word
              (when last-current-word-id
                (swap! state assoc-in [:word/id last-current-word-id :word/active] false))
              (when-let [new-current-word-id (get-in transcript-keys-to-update [:transcript/current-word 1])]
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
(ns com.submerged-structure.mutations
  "Client-side mutations.
   
   'Server-side' mutations could normally be also defined here, only with
   `#?(:clj ...)` but here even the 'server' runs in the browser so we must
   define them in another ns, which we do in `...pathom`."
  (:require
   [com.fulcrologic.fulcro.mutations :refer [defmutation]]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]))

(defn all-words-from-state-unsorted
  [state]
  (let [word-tree (fdn/db->tree [#:root{:current-transcript
                                        [:transcript/id
                                         #:transcript{:segments
                                                      [:segment/id #:segment{:words [:word/id :word/start :word/end]}]}]}]
                                state state)]
    (mapcat :segment/words (:transcript/segments (:root/current-transcript word-tree)))))

(defn all-segments-from-state-unsorted
  [state]
  (let [segment-tree (fdn/db->tree [#:root{:current-transcript
                                        [:transcript/id
                                         #:transcript{:segments
                                                      [:segment/id :segment/start :segment/end]}]}]
                                state state)]
    (:transcript/segments (:root/current-transcript segment-tree))))


(defn words-from-state
  [state]
  (sort-by :word/start (filter #(and (:word/start %) (:word/end %)) (all-words-from-state-unsorted state))))

(defn find-current-word [state t]
  (first (filter #(and (<= (:word/start %) t) (<= t (:word/end %))) (words-from-state state))))

(defn find-first-word-not-ended [state t]
  (first (filter #(< t (:word/end %)) (words-from-state state))))

(defn find-last-word-ended [state t]
  (last (filter #(< (:word/end %) t) (words-from-state state))))

(defn find-current-period
  "The current period is the word or break between words that contains the current time.
   
   If the current time is not in a word, :word and :id are nil.
   :start and :end are the start and end times of the period.
   
   If the current time is not in a word, :start and :end are the :end of the previous word
   and :start of the next word except for the first period :start is zero and for the last period :end is the duration of the audio."
  [state t id]
  (if-let [c-w (find-current-word state t)]
    c-w ; in a word
    (if-let [previous-word (find-last-word-ended state t)]
      (if-let [next-word (find-first-word-not-ended state t)]
        #:word{:id nil
               :word nil ;; pause between words
               :start (:word/end previous-word)
               :end (:word/start next-word)}
        (let [duration (get-in state [:transcript/id id :transcript/duration])]
          #:word{:word nil ; pause after last word
                 :id nil
                 :start (:word/end previous-word)
                 :end duration}))
      #:word{:word nil ; pause before first word
             :id nil
             :start 0
             :end (:word/start (find-first-word-not-ended state t))})))


(defmutation update-transcript-current-time
  "Sets the currently active word, if there is one, and also sets the start and end time of the time period that the word or pause covers."
  
  [{:transcript/keys [id current-time]}]
  (action [{:keys [state]}]
          (let [current-period (find-current-period @state current-time id)
                last-current-word-id (get-in @state [:transcript/id id :transcript/current-word 1])]
            (do 
              (js/setTimeout #(js/console.log "update-transcript-current-time" current-time current-period) 0)
              (swap! state assoc-in [:transcript/id id :ui-period/start] (:word/start current-period))
              (swap! state assoc-in [:transcript/id id :ui-period/end] (:word/end current-period))
              (swap! state assoc-in [:transcript/id id :transcript/current-word] [:word/id (:word/id current-period)])
              ;; deactivate last word
              (when last-current-word-id
                (swap! state assoc-in [:word/id last-current-word-id :word/active] false))
              (when-let [current-word-id (:word/id current-period)]
                ;; activate new active word
                (swap! state assoc-in [:word/id current-word-id :word/active] true)))))
  (remote [_] false))

(defmutation update-ui-player-doing [{:keys [transcript/id ui-player/doing]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id id :ui-player/doing] doing)))


(defmutation update-transcript-duration [{:transcript/keys [id duration]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id id :transcript/duration] duration))
  (remote [_] false))


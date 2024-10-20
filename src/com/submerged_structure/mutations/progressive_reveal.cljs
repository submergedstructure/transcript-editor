(ns com.submerged-structure.mutations.progressive-reveal
  (:require
   [com.fulcrologic.fulcro.mutations :refer [defmutation]]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]))

(defn query-local-db [state-deref & {:keys [segment-keys] :or {segment-keys [:segment/id :segment/ui-reveal-state]}}]
  (fdn/db->tree
   [#:root{:current-transcript
           [:transcript/id :transcript/current-or-last-segment
            #:transcript{:segments
                         segment-keys}]}]
   state-deref state-deref))

(defn get-necessary-data-from-state
  [state-deref]
  (let [tree-from-state (query-local-db state-deref state-deref)
        current-segment-id (get-in tree-from-state [:root/current-transcript :transcript/current-or-last-segment 1])
        segment-ids-and-reveal-state (get-in tree-from-state [:root/current-transcript :transcript/segments])
        segment-ids-and-reveal-state-before-current (take-while #(not= (:segment/id %) current-segment-id) segment-ids-and-reveal-state)]
    [current-segment-id segment-ids-and-reveal-state-before-current]))

(comment 
  (def state-deref *1)
  (get-necessary-data-from-state state-deref)
  )

(def text-reveal-progression ["blurred" "un-blurred" "grammar-highlighted"])

(defn next-reveal-state-and-segment-ids-to-set [state-deref]
  (let [reveal-states-for-nil (first text-reveal-progression)
        [current-segment-id segment-ids-and-reveal-state-before-current] (get-necessary-data-from-state state-deref)]
    (if current-segment-id
      (let [current-segment (get-in state-deref [:segment/id current-segment-id])
            current-segment-reveal-state (get current-segment :segment/ui-reveal-state reveal-states-for-nil)
            [reveal-states-lower-than-current [_ next-reveal-state & _]] (split-with (partial not= current-segment-reveal-state) text-reveal-progression)
            reveal-states-lower-than-next-as-set (set (conj reveal-states-lower-than-current current-segment-reveal-state))]
        (if next-reveal-state
          [next-reveal-state
           (keep (fn [{id :segment/id
                       reveal-state :segment/ui-reveal-state :or {reveal-state reveal-states-for-nil}}]
                   (when (reveal-states-lower-than-next-as-set reveal-state)
                     id))
                 (concat segment-ids-and-reveal-state-before-current [current-segment]))]
        [reveal-states-for-nil nil]))
      [reveal-states-for-nil nil])))

(defmutation progressive-reveal-segments-upto-current [{}]
  (action [{:keys [state]}]
          (let [[next-reveal-state segment-ids-to-set] (next-reveal-state-and-segment-ids-to-set @state)]
            (doall
             (map (fn [id]
                    (swap! state assoc-in [:segment/id id :segment/ui-reveal-state] next-reveal-state))
                  segment-ids-to-set))))
  (remote [_] false))

(defn reset-reveal-state-of-all! [state]
  (doall
   (map (fn [{:segment/keys [id]}]
          (swap! state assoc-in [:segment/id id :segment/ui-reveal-state] (first text-reveal-progression)))
        (get-in (query-local-db @state :segment-keys [:segment/id]) [:root/current-transcript :transcript/segments]))))

(defmutation reset-reveal-state-of-all [{}]
  (action [{:keys [state]}]
          (reset-reveal-state-of-all! state))
  (remote [_] false))

(comment
  (let [[reveal-states-lower-than-current _ [next-reveal-state & _]] (partition-by #(= % ::blurred) text-reveal-progression)]
    (prn reveal-states-lower-than-current next-reveal-state)))
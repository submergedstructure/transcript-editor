(ns com.submerged-structure.mutations.progressive-reveal
  (:require
   [com.fulcrologic.fulcro.mutations :refer [defmutation]]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]))

(defn segment-translation-tree [state-deref]
  (fdn/db->tree
   [#:root{:current-transcript
           [:transcript/id :transcript/current-or-last-segment
            #:transcript{:segments
                         [:segment/id :segment/ui-reveal-state
                          {:segment/translations [:translation/id :translation/visible?]}]}]}]
   state-deref state-deref))


(def text-reveal-progression ["blurred" "un-blurred" "grammar-highlighted" "grammar-highlighted"])
(def translation-reveal-progression [false false false true])

(def reveal-progression (map (fn [text-state translation-state] {:text text-state :translations-visible? translation-state}) text-reveal-progression translation-reveal-progression))

(defn next-reveal-state-and-segments-to-set [state-deref]
  (let [tree-from-state (segment-translation-tree state-deref)
        current-segment-id (get-in tree-from-state [:root/current-transcript :transcript/current-or-last-segment 1])]        
    (when current-segment-id
      (let [segments-tree (get-in tree-from-state [:root/current-transcript :transcript/segments])
            [segment-tree-before-current [current-segment & _]] (split-with #(not= (:segment/id %) current-segment-id) segments-tree)
            current-segment-reveal-state {:text (get current-segment :segment/ui-reveal-state)
                                          :translations-visible? (some? (some :translation/visible? (get current-segment :segment/translations)))}
            next-reveal-state (first (drop 1 (drop-while (partial not= current-segment-reveal-state) reveal-progression)))]
        (when next-reveal-state
          [next-reveal-state (concat segment-tree-before-current [current-segment])])))))

(defmutation progressive-reveal-segments-upto-current [{}]
  (action [{:keys [state]}]
          (when-let [[next-reveal-state segments-to-set] (next-reveal-state-and-segments-to-set @state)]
            (doall
             (map (fn [{:segment/keys [id]}]
                    (swap! state assoc-in [:segment/id id :segment/ui-reveal-state] (:text next-reveal-state)))
                  segments-to-set))
            (doall
             (map (fn [{:translation/keys [id]}]
                    (swap! state assoc-in [:translation/id id :translation/visible?] (:translations-visible? next-reveal-state)))
                  (mapcat :segment/translations segments-to-set)))))
  (remote [_] false))

(defn reset-reveal-state-of-all! [state]
  (let [segments-in-current-transcript
        (get-in (segment-translation-tree @state) [:root/current-transcript :transcript/segments])]
    (doall
     (map (fn [{:segment/keys [id]}]
            (swap! state assoc-in [:segment/id id :segment/ui-reveal-state] (:text (first reveal-progression))))
          segments-in-current-transcript))
    (doall
     (map (fn [{:translation/keys [id]}]
            (swap! state assoc-in [:translation/id id :translation/visible?] (:translations-visible? (first reveal-progression))))
          (mapcat :segment/translations segments-in-current-transcript)))))

(defmutation reset-reveal-state-of-all [{}]
  (action [{:keys [state]}]
          (reset-reveal-state-of-all! state))
  (remote [_] false))

(comment
  (let [[reveal-states-lower-than-current _ [next-reveal-state & _]] (partition-by #(= % ::blurred) text-reveal-progression)]
    (prn reveal-states-lower-than-current next-reveal-state))
  
  (def tree-from-state *1)
  (def current-segment-id (get-in tree-from-state [:root/current-transcript :transcript/current-or-last-segment 1]))
  (def segments-tree (get-in tree-from-state [:root/current-transcript :transcript/segments]))
  (let [[segment-tree-before-current [current-segment & _]] (split-with #(not= (:segment/id %) current-segment-id) segments-tree)
        current-segment-reveal-state {:text (get current-segment :segment/ui-reveal-state)
                                      :translations-visible? (some? (some :translation/visible? (get current-segment :segment/translations)))}]
    {:current-reveal-state current-segment-reveal-state
     :next-reveal-state (first (drop 1 (drop-while (partial not= current-segment-reveal-state) reveal-progression)))}
    )
  (def )
  (def next-reveal-state ))
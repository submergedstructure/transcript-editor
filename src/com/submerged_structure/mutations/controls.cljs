(ns com.submerged-structure.mutations.controls
  (:require
   [com.fulcrologic.fulcro.mutations :refer [defmutation]]
   [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
  
   [com.submerged-structure.mutations.common :as common]))


(defmutation hide-transcript-help [_]
  (action [{:keys [state]}]
          (swap! state assoc :ui/help-hidden true)))

(defmutation update-ui-player-doing [{:keys [ui-player/doing]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id (common/get-current-transcript-id-from-state @state) :ui-player/doing] doing)))

(defmutation transcript-display-type-menu [#:transcript{:keys [id display-type]}]
  (action [{:keys [state]}]
          (swap! state assoc-in [:transcript/id id :transcript/display-type] display-type)))

(defn segment-that-includes-token [state-deref token-id-to-find]
  (let [token-tree (get-in
                    (fdn/db->tree
                     [#:root{:current-transcript
                             [:transcript/id
                              #:transcript{:segments
                                           [:segment/id
                                            #:segment{:words [#:word{:tokens [:token/id]}]}]}]}]
                     state-deref state-deref)
                    [:root/current-transcript :transcript/segments])]
    (some
     (fn [[segment-id tokens]]
       (when (some (fn [{:token/keys [id]}] (= id token-id-to-find)) tokens)
         segment-id))
     (map
      (fn [segment] [(:segment/id segment)
                     (mapcat (fn [word] (:word/tokens word)) (:segment/words segment))]) token-tree))))


(defmutation display-morphological-details-for-token [{:token/keys [id]}]
  (action [{:keys [state]}]
          (let [transcript-id (common/get-current-transcript-id-from-state @state)]
            (when-let [last-token-id (get-in @state [:transcript/id transcript-id :ui-morph-display/display-token])]
              (let [segment-that-includes-last-token (segment-that-includes-token @state last-token-id)]
                (swap! state assoc-in [:segment/id segment-that-includes-last-token :ui-morph-display/display-token] [:token/id nil])))
            (swap! state assoc-in [:transcript/id  transcript-id :ui-morph-display/display-token] [:token/id id])
            (swap! state assoc-in [:segment/id (segment-that-includes-token @state id) :ui-morph-display/display-token] [:token/id id]))))


(defn get-all-segments-in-current-transcript-and-autopause? [state-deref]
  (get-in
   (fdn/db->tree
    [#:root{:current-transcript
            [:transcript/id
             #:transcript{:segments
                          [:segment/id :segment/autopause?]}]}]
    state-deref state-deref)
   [:root/current-transcript :transcript/segments]))


(defn any-segments-in-current-transcript-autopaused? [state-deref]
  (some :segment/autopause? (get-all-segments-in-current-transcript-and-autopause? state-deref)))


(defmutation toggle-autopause-for-segment [{:keys [:segment/id]}]
  (action [{:keys [state]}]
          (swap! state update-in [:segment/id id :segment/autopause?] not)
          (swap!
           state
           assoc-in
           [:transcript/id (common/get-current-transcript-id-from-state @state) :ui-transcript-autopause-control/any-segment?]
           (any-segments-in-current-transcript-autopaused? @state))))


(defmutation toggle-autopause-for-transcript [{:keys [:transcript/id]}]
  (action [{:keys [state]}]
          (swap! state update-in [:transcript/id id :ui-transcript-autopause-control/any-segment?] not)
          (doall (map (fn [segment-id]
                        (swap! state
                               assoc-in
                               [:segment/id segment-id :segment/autopause?]
                               (get-in @state [:transcript/id id :ui-transcript-autopause-control/any-segment?])))
                      (map :segment/id
                           (get-all-segments-in-current-transcript-and-autopause? @state))))))


(defmutation toggle-transcript-scroll-to-active [_]
  (action [{:keys [state]}]
          (swap! state update-in [:transcript/id (common/get-current-transcript-id-from-state @state) :ui-player/scroll-to-active] not))
  (remote [_] false))
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


(defn get-all-morphological-details-items-in-current-transcript-and-their-visibility [state-deref]
  (remove #(#{"SYM" "PUNCT"} (:token/pos %))
          (mapcat
           :word/tokens
           (mapcat
            :segment/words
            (get-in
             (fdn/db->tree
              [#:root{:current-transcript
                      [:transcript/id
                       #:transcript{:segments
                                    [:segment/id
                                     #:segment{:words [:word/id
                                                       #:word{:tokens
                                                              [:token/id :token/pos :token/morphological-details-visible?]}]}]}]}]
              state-deref state-deref)
             [:root/current-transcript :transcript/segments])))))

(comment
  (def state-deref *1)
  (get-all-morphological-details-items-in-current-transcript-and-their-visibility state-deref))

(defn any-morphological-details-item-in-current-transcript-visible? [state-deref]
  (some :token/morphological-details-visible? (get-all-morphological-details-items-in-current-transcript-and-their-visibility state-deref)))

(defmutation toggle-visibility-of-morphological-details-for-token [{:keys [:token/id]}]
  (action [{:keys [state]}]
          (swap! state update-in [:token/id id :token/morphological-details-visible?] not)
          (swap!
           state
           assoc-in
           [:transcript/id (common/get-current-transcript-id-from-state @state) :ui-morphological-info-grid-control/any-visible?]
           (any-morphological-details-item-in-current-transcript-visible? @state))))


(defmutation toggle-visibility-of-morphological-details-for-transcript [{:keys [:transcript/id]}]
  (action [{:keys [state]}]
          (swap! state update-in [:transcript/id id :ui-morphological-info-grid-control/any-visible?] not)
          (doall (map (fn [id-of-token-with-morph-analysis]
                        (swap! state
                               assoc-in
                               [:token/id id-of-token-with-morph-analysis :token/morphological-details-visible?]
                               (get-in @state [:transcript/id id :ui-morphological-info-grid-control/any-visible?])))
                      (map :token/id
                           (get-all-morphological-details-items-in-current-transcript-and-their-visibility @state))))))

(defn get-all-segments-in-current-transcript-and-autopause? [state-deref]
  (get-in
   (fdn/db->tree
    [#:root{:current-transcript
            [:transcript/id
             #:transcript{:segments
                          [:segment/id :segment/autopause?]}]}]
    state-deref state-deref)
   [:root/current-transcript :transcript/segments]))

(comment
  (def state-deref *1)
  (get-all-morphological-details-items-in-current-transcript-and-their-visibility state-deref))

(defn any-segments-in-current-transcript-and-autopaused? [state-deref]
  (some :segment/autopause? (get-all-segments-in-current-transcript-and-autopause? state-deref)))


(defmutation toggle-autopause-for-segment [{:keys [:segment/id]}]
  (action [{:keys [state]}]
          (swap! state update-in [:segment/id id :segment/autopause?] not)
          (swap!
           state
           assoc-in
           [:transcript/id (common/get-current-transcript-id-from-state @state) :ui-transcript-autopause-control/any-segment?]
           (any-segments-in-current-transcript-and-autopaused? @state))))


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
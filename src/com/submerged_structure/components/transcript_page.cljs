(ns com.submerged-structure.components.transcript-page
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div a]]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc fragment]]

            [com.fulcrologic.semantic-ui.modules.sticky.ui-sticky :refer [ui-sticky]]
            [com.fulcrologic.semantic-ui.collections.message.ui-message :refer [ui-message]]
            [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
            [com.fulcrologic.semantic-ui.icons :as i]
            [com.fulcrologic.semantic-ui.elements.divider.ui-divider :refer [ui-divider]]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]

            [com.submerged-structure.components.player :as player]
            [com.submerged-structure.components.controls.player-controls :as player-controls]
            [com.submerged-structure.spacy-grammar :as spacy-grammar]
            [com.submerged-structure.components.segment :as segment]
            [com.submerged-structure.components.token-morphological-info :as token-morphological-info]
            #_[com.submerged-structure.components.transcript-switcher :as transcript-switcher]
            [com.submerged-structure.app-help :as app-help]

            [goog.functions :as gf]

            [com.fulcrologic.fulcro.routing.dynamic-routing :as dr]
            [com.submerged-structure.player-atom :as player-atom]

            [com.submerged-structure.transcript-scroll :as transcript-scroll]

            [clojure.string]))


(defn update-current-word [this t id]
  (let [props (comp/props this)
        autopause-start (:ui-transcript-autopause/next-period-start props)
        autopause-end (:ui-transcript-autopause/next-period-end props)]
    ; these have been set on a previous call when within a segment with autopause? true
    (when (and autopause-start (<= autopause-start t autopause-end))
      (.pause (player-atom/get-player)))
    
    (comp/transact!! this `[(com.submerged-structure.mutations.words-and-segments/update-transcript-current-time {:transcript/current-time ~t})])
    (when (:ui-player/scroll-to-active props) 
      (transcript-scroll/scroll-to-active-element-after-time-out {:fallback-to-segment false}))))

(def update-current-word-once-per-frame
  "called when we don't have a start or end time for the current period."
  (gf/rateLimit update-current-word (/ 1000 10))) ; 10 frames per second

(defn update-current-word-throttled [this t id]
  (let [props (comp/props this)
        start (:ui-period/start props)
        end (:ui-period/end props)]
    (if (some nil? [start end]);check if either start or end are nil
      (update-current-word-once-per-frame this t id)
      (when-not (< start t end)
        (update-current-word this t id)))))


(defn transcript-on-timeupdate [this id]
  (fn [^js ws]
    (let [current-time (.getCurrentTime ws)]
      (update-current-word-throttled this current-time id)
      (player-controls/player-on-timeupdate ws))))

(defn change-display-type [this id type & js-args]
  (comp/transact!
     this
     `[(com.submerged-structure.mutations.controls/transcript-display-type-menu
        {:transcript/id ~id
         :transcript/display-type ~type})]))

(defn transcript-blurb [label summary url]
  (fragment
   (div :.ui.header
        label)
   (when summary (div :.ui.meta
                      {}
                      (map-indexed (fn [p-no paragraph] (dom/p {:key p-no} paragraph))
                                   (clojure.string/split-lines summary))))
   (when url (div :.ui.footer " " (a {:href url} "See more here.")))))

(defsc TranscriptPage [this {:ui/keys [help-hidden]
                         player-doing :ui-player/doing
                         :transcript/keys [id
                                           display-type
                                           segments
                                           label
                                           summary
                                           url]
                         :ui-morph-display/keys [display-token]
                         :>/keys          [player
                                           #_transcript-switcher
                                           player-controls]}]
  {:ident :transcript/id

   :route-segment ["transcript" :transcript/id]
   :will-enter
   (fn [app route-params]
     (comp/transact! app `[(com.submerged-structure.mutations.load/load-transcript ~(select-keys route-params [:transcript/id]))])
     (dr/route-immediate
      [:transcript/id
       (:transcript/id route-params)]
      #_#(do 
          (dr/target-ready! app [:transcript/id
                                 (:transcript/id route-params)]))))


   :initial-state (fn [_] {:transcript/segments (comp/get-initial-state segment/Segment {})
                           #_#_:>/transcript-switcher (comp/get-initial-state transcript-switcher/TranscriptSwitcher {})
                           :>/player (comp/get-initial-state player/PlayerComponent {})
                           :>/player-controls (comp/get-initial-state player-controls/PlayerControls {})
                           :ui-morph-display/display-token (comp/get-initial-state token-morphological-info/TokenMorphologicalInfo {})}
                    )
   :query [:transcript/id
           :transcript/label
           :transcript/duration

           :transcript/summary
           :transcript/url

           [:ui/help-hidden '_]

           :ui-player/scroll-to-active

           :ui-period/start
           :ui-period/end

           :ui-player/doing

           :ui-transcript-autopause/next-period-start
           :ui-transcript-autopause/next-period-end

           :transcript/display-type

           {:transcript/current-word [:word/id
                                      :word/word]}

           {:transcript/segments (comp/get-query segment/Segment)}
           #_{:>/transcript-switcher (comp/get-query transcript-switcher/TranscriptSwitcher)}
           {:>/player (comp/get-query player/PlayerComponent)}
           {:>/player-controls (comp/get-query player-controls/PlayerControls)}
           {:ui-morph-display/display-token
            (comp/get-query token-morphological-info/TokenMorphologicalInfo)}]
   :shouldComponentUpdate (fn [_ _ _] true)}
  (fragment
   {}
   (dom/div
    :.ui.inverted.vertical.masthead.center.aligned.segment
   
    (dom/div
     :.ui.text.container
     (dom/h1 :.ui.inverted.header
             (dom/a {:onClick #(dr/change-route-relative! this this [:.. "home"])
                     :title "Home"}
                    (ui-popup {:trigger (ui-icon {:name i/home-icon
                                                  :size "large"} )}
                              "Home of SubmergedStructure.com"))
             "SubmergedStructure.com")
     (dom/h3 "Listen to audio in Polish with all the help you need when you need it.")))
   #_(transcript-switcher/ui-transcript-switcher transcript-switcher {:current-transcript id})
   (ui-sticky
    {:id "media-player"
     :context (.. js/document -body (querySelector "#transcript"))
     :styleElement {:backgroundColor "white"}
     :children
     (fragment
      {}
      (player/ui-player
       player
       {:onTimeupdate (transcript-on-timeupdate this id)})
      (player-controls/ui-player-controls player-controls))})
   (when-not
    help-hidden
     (ui-message
      {:info true
       :className "container"
       :onDismiss (fn [_] (comp/transact! this `[(com.submerged-structure.mutations.controls/hide-transcript-help {})]))}
      app-help/app-help))
   (div :.ui.big.container.visible-on-screen-less-than-1350px
        {:style {:display "none"}}
        (div :.ui.raised.segment
             (transcript-blurb label summary url)))
   

   (div {:classes ["ui" "segment" "big" "container" "text" "grammar_highlighting"]}
        (div :.key (spacy-grammar/grammar-key))
        (ui-divider {:section true})
        (if-not (empty? segments)
          (div :.transcript#transcript
               (when-not (= player-doing :loading)
                 ; Wait until the player has loaded before adding the sticky rails to dom.
                 ; If player is not on the page, we cannot calculate it's height in order to y offset the stick rail content.
                 (fragment
                  {}
                  (div :.ui.left.rail.very.close.hidden-on-screen-less-than-1350px {}
                       (ui-sticky
                        {:context (.. js/document -body (querySelector "#transcript"))
                         :offset (+ (player/player-height) 10)}
                        (div :.ui.raised.segment
                             (transcript-blurb label summary url))))
                  (div :.ui.right.rail.very.close.hidden-on-screen-less-than-1350px  ;; will be outside viewport for small screens.
                       {}
                       (ui-sticky
                        {:context (.. js/document -body (querySelector "#transcript"))
                         :offset (+ (player/player-height) 10)}
                        (token-morphological-info/ui-token-morphological-info display-token)))))
               (map segment/ui-segment segments))
          (div :.ui.placeholder
               (mapv (fn [_] (div :.line)) (range 20)))))))

(def ui-transcript-page (comp/factory TranscriptPage {:keyfn :transcript/id}))
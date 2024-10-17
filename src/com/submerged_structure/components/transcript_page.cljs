(ns com.submerged-structure.components.transcript-page
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div a]]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc fragment]]

            [com.fulcrologic.semantic-ui.modules.sticky.ui-sticky :refer [ui-sticky]]
            [com.fulcrologic.semantic-ui.collections.message.ui-message :refer [ui-message]]
            [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
            [com.fulcrologic.semantic-ui.icons :as i]
            [com.fulcrologic.semantic-ui.elements.divider.ui-divider :refer [ui-divider]]
            

            


            [com.submerged-structure.components.player :as player]
            [com.submerged-structure.components.controls.player-controls :as player-controls]
            [com.submerged-structure.confidence-to-color :as c-to-c]
            [com.submerged-structure.spacy-grammar :as spacy-grammar]
            [com.submerged-structure.components.segment :as segment]
            [com.submerged-structure.components.token-morphological-info :as token-morphological-info]
            #_[com.submerged-structure.components.transcript-switcher :as transcript-switcher]
            [com.submerged-structure.app-help :as app-help]

            [goog.functions :as gf]
            
            [com.fulcrologic.fulcro.routing.dynamic-routing :as dr]
            [com.submerged-structure.player-atom :as player-atom]
            
            [clojure.string]))



(defn scroll-element-to-middle-of-visible-area-below-player
  "Assuming player is a sticky at the top of the screen, scroll element 
   to the vertical center of the screen below the player."
  [element transcript-id]
  (let [element-y-in-viewport (.-top (.. element (getBoundingClientRect)))
        current-top-of-viewport (.. js/window -pageYOffset)
        element-y-in-document (+ element-y-in-viewport current-top-of-viewport)
        scroll-to (- element-y-in-document (/ (+ (player/player-height transcript-id) js/window.innerHeight) 2))]
    (js/window.scrollTo  (clj->js {:left 0
                                   :top scroll-to
                                   :behavior "smooth"}))))

(defn update-current-word [this t id]
  (let [props (comp/props this)
        start (:ui-transcript-autopause/next-period-start props)
        end (:ui-transcript-autopause/next-period-end props)]
    ; these have been set on a previous call when within a segment with autopause? true
    (js/console.log "check for autopause " props start t end)
    (when (and start (<= start t end))
      (.pause (player-atom/get-player))))
    
  (comp/transact!! this `[(com.submerged-structure.mutations.words-and-segments/update-transcript-current-time {:transcript/current-time ~t})])
  (js/console.log "update-current-word" this id t)
  (js/setTimeout
   (fn []
     (when-let [active-word (js/document.querySelector ".word.active")]
       #_(player/player-on-current-word-update (:ui-period/start (comp/props this)) (:ui-period/end (comp/props this)) (get-in (comp/props this) [:transcript/current-word :word/word]))
       (when (:ui-player/scroll-to-active (comp/props this))
         (scroll-element-to-middle-of-visible-area-below-player active-word id))))
   0))

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
        (js/console.log "throttled according to time period " t start end)
        (update-current-word this t id)))))


(defn transcript-on-timeupdate [this id]
  (fn [^js ws]
    (let [current-time (.getCurrentTime ws)]
      (update-current-word-throttled this current-time id)
      (player-controls/player-on-timeupdate ws))))

(defn change-display-type [this id type & js-args]
  (js/console.log "Menu item clicked" this id type js-args)
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
     (js/console.log "Transcript will-enter" route-params)
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
   #_(transcript-switcher/ui-transcript-switcher transcript-switcher {:current-transcript id})
   (ui-sticky
    {:id (str "player-" id)
     :context (.. js/document -body (querySelector (str "#transcript-" id)))
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
   (div :.ui.pointing.menu.stackable.container {}
        (a {:classes [(when (= display-type :plain) "active") "item"]
            :onClick (partial change-display-type this id :plain)}
           (ui-icon {:name i/low-vision-icon}) "Plain Transcript - No Coloring")
        (a {:classes [(when (= display-type :confidence) "active") "item"]
            :onClick (partial change-display-type this id :confidence)}
           (ui-icon {:name i/braille-icon}) "AI's Confidence of Each Word")
        (a {:classes [(when (= display-type :grammar) "active") "item"]
            :onClick (partial change-display-type this id :grammar)}
           (ui-icon {:name i/eye-icon}) "Grammar X-Ray"))

   (div {:classes [(when (= display-type :grammar) "grammar_highlighting") "ui" "segment" "big" "container" "text"]}
        (when-let [key-for-display-type (case display-type
                                          :confidence (c-to-c/confidence-key)
                                          :grammar (spacy-grammar/grammar-key)
                                          nil)]
          (fragment (div :.key key-for-display-type)
                    (ui-divider {:section true})))
        
        (if-not (empty? segments)
          (div :.transcript
               {:id (str "transcript-" id)}
               (when-not (= player-doing :loading)
                 ; Wait until the player has loaded before adding the sticky rails to dom.
                 ; If player is not on the page, we cannot calculate it's height in order to y offset the stick rail content.
                 (fragment
                  {}
                  (div :.ui.left.rail.very.close.hidden-on-screen-less-than-1350px {}
                       (ui-sticky
                        {:context (.. js/document -body (querySelector (str "#transcript-" id)))
                         :offset (+ (player/player-height id) 10)}
                        (div :.ui.raised.segment
                             (transcript-blurb label summary url))))
                  (div :.ui.right.rail.very.close.hidden-on-screen-less-than-1350px  ;; will be outside viewport for small screens.
                       {}
                       (ui-sticky
                        {:context (.. js/document -body (querySelector (str "#transcript-" id)))
                         :offset (+ (player/player-height id) 10)}
                        (token-morphological-info/ui-token-morphological-info display-token)))))
               (map #(segment/ui-segment % {:transcript/display-type display-type}) segments))
          (div :.ui.placeholder
               (mapv (fn [_] (div :.line)) (range 20)))))))

(def ui-transcript-page (comp/factory TranscriptPage {:keyfn :transcript/id}))
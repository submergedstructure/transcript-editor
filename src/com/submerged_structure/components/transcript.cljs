(ns com.submerged-structure.components.transcript
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div h1 h2 h3 li p ul span i b a]]
            
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [goog.functions :as gf]
            [com.submerged-structure.confidence-to-color :as c-to-c]
            [com.fulcrologic.semantic-ui.modules.sticky.ui-sticky :refer [ui-sticky]]
            [com.fulcrologic.semantic-ui.elements.segment.ui-segment :as semantic-ui-segment]
            [com.fulcrologic.semantic-ui.collections.message.ui-message :refer [ui-message]]
            [com.fulcrologic.semantic-ui.collections.message.ui-message-header :refer [ui-message-header]]
            [com.submerged-structure.components.player :as player]
  
            [com.submerged-structure.components.segment :as segment]
            [com.submerged-structure.components.transcript-switcher :as transcript-switcher]))



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
  (comp/transact!! this `[(com.submerged-structure.mutations/update-transcript-current-time {:transcript/current-time ~t})])
  (js/console.log "update-current-word" this id t)
  (js/setTimeout
   (fn []
     (when-let [active-word (js/document.querySelector ".word.active")]
       (player/player-on-current-word-update (:ui-period/start (comp/props this)) (:ui-period/end (comp/props this)) (get-in (comp/props this) [:transcript/current-word :word/word]))
       (when (:player/scroll-to-active (comp/props this))
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

(defn confidence-key []
  (div
   (p :.ui.center.aligned.container "AI's confidence of each word: ")
   (p :.ui.justified.container#confidence-key
      (for [c (map #(js/Number.parseFloat (.toFixed % 2)) (range 1.0 -0.05 -0.05))] ; make sure that we get floats to 2 decimal places
        (span {:style (c-to-c/confidence-to-style c)} (str c "  "))))
   (p :.ui.center.aligned.container "(1.0 = very high 0.0 = none)")))

(defn transcript-on-timeupdate [this id]
  (fn [^js ws]
    (let [current-time (.getCurrentTime ws)]
      (update-current-word-throttled this current-time id)
      (player/player-on-timeupdate ws))))

(defsc Transcript [this {:ui/keys [help-hidden]
                         :transcript/keys [id
                                           segments]
                         :>/keys          [player
                                           transcript-switcher
                                           player-controls]}]
  {:ident :transcript/id
   :initial-state (fn [_] {:transcript/segments (comp/get-initial-state segment/Segment {})
                           :>/transcript-switcher (comp/get-initial-state transcript-switcher/TranscriptSwitcher {})
                           :>/player (comp/get-initial-state player/PlayerComponent {})
                           :>/player-controls (comp/get-initial-state player/PlayerControls {})})
   :query [:transcript/id
           :transcript/label
           :transcript/duration

           [:ui/help-hidden '_]

           :ui-player/scroll-to-active

           :ui-period/start
           :ui-period/end



           {:transcript/current-word [:word/id
                                      :word/word]}

           {:transcript/segments (comp/get-query segment/Segment)}
           {:>/transcript-switcher (comp/get-query transcript-switcher/TranscriptSwitcher)}
           {:>/player (comp/get-query player/PlayerComponent)}
           {:>/player-controls (comp/get-query player/PlayerControls)}]}
  (div :.ui.container
       (transcript-switcher/ui-transcript-switcher transcript-switcher {:current-transcript id})
       (ui-sticky
        {:id (str "player-" id)
         :context (.. js/document -body (querySelector (str "#transcript-" id)))
         :styleElement {:backgroundColor "white"}
         :children
         (div
          (player/ui-player
           player
           {:onTimeupdate (transcript-on-timeupdate this id)})
          (semantic-ui-segment/ui-segment
           {:basic true
            :textAlign "center"}
           (player/ui-player-controls player-controls)))})
       (when-not
        help-hidden
         (ui-message
          {:info true
           :onDismiss (fn [_] (comp/transact! this `[(com.submerged-structure.mutations/hide-transcript-help {})]))}
          (ui-message-header nil "How to use this player")
          (p "As the audio plays you will see the currently spoken word is highlighed.")
          (ul
           (li (b "Several transcripts available: ") "At the very top of the page the heading with the transcript name is actually a dropdown menu, you can use this to switch between different transcripts.")
           (li (b "Colours indicate confidence: ") "These transcripts are generated by whisperx, this AI's confidence in each word is shown by the colour of the word. Directly below this message you can see the confidence / colour key. Black indicates a high confidence, then we use spectrum of \"traffic light\" colours from green to orange to red. Red indicating a low confidence.")
           (li (b "Playback control: ")
               (ul
                (li "You can click on any word in the transcript to skip forward or backward to that word and begin playing.")
                (li "You can also click on the audio waveform itself to skip to that point in the audio.")
                (li "The top of the waveform visualisation is a zoomed in view of the currently playing audio. Below this is a zoomed out view of the entire audio. You can click on either to skip to that point in the audio.")
                (li "Hover over playback control buttons below the waveform with your cursor for more information about what they do.")))
           (li (b "Translations: ")
               (ul
                (li "Click on the button next to a sentence to see a translation of that sentence. You can also click on the translation to hide it again.")
                (li "You can also click on the button to the right of the player controls to show or hide all translations at once.")))
           (li (b "Grammar Analysis:")
               (ul
                (li "Hover over any word to see the root word it is derived from and the grammatical features of that word.")))
           (li (b "Links to dictionary for individual words:")
               (ul
                (li "Click on linked root word and / or the declined word itself in the grammatical analysis pop up to see the definition of the word on " (a {:href "https://www.diki.pl/" :target "_blank"} "diki.pl") "."))))))
       (confidence-key)
       (div :.transcript.ui.container
            {:id (str "transcript-" id)}
            (map segment/ui-segment segments))))

(def ui-transcript (comp/factory Transcript {:keyfn :transcript/id}))
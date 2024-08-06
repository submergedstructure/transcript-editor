(ns com.submerged-structure.ui
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div h1 h2 h3 li p ul span i b]]
            [com.fulcrologic.fulcro.algorithms.normalize :as fn]
            
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.submerged-structure.mock-data :as mock-data]
            [goog.functions :as gf]
            [com.submerged-structure.confidence-to-color :as c-to-c]
            [com.fulcrologic.semantic-ui.modules.sticky.ui-sticky :refer [ui-sticky]]
            [com.fulcrologic.semantic-ui.modules.dropdown.ui-dropdown :refer [ui-dropdown]]
            [com.fulcrologic.semantic-ui.modules.dropdown.ui-dropdown-item :refer [ui-dropdown-item]]
            [com.fulcrologic.semantic-ui.elements.segment.ui-segment :as semantic-ui-segment]
            [com.fulcrologic.semantic-ui.collections.message.ui-message :refer [ui-message]]
            [com.fulcrologic.semantic-ui.collections.message.ui-message-header :refer [ui-message-header]]
            [com.fulcrologic.semantic-ui.elements.label.ui-label :refer [ui-label]]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
            [com.submerged-structure.ui-player :as ui-player]
            [com.fulcrologic.semantic-ui.icons :as i]
            [clojure.string :as str]))



(defsc Word [this {:word/keys [word active score start]}]
  {:ident :word/id
   :initial-state {:word/active false}
   :query [:word/id :word/word :word/start :word/end :word/active :word/score]}
  (span {:data-c score
         :classes [(when active "active") "word"]
         :onClick (fn [ws] (ui-player/on-word-click ws start))
         :style (c-to-c/confidence-to-style score)}
        word))

(def ui-word (comp/factory Word {:keyfn :word/id}))

(defsc Translation
  "`visible?` is a boolean. css class makes the translation appear to the right of a segment when translation hidden or below when shown."
  [this {:translation/keys [id text lang visible?]}]
  {:ident :translation/id
   :initial-state {}
   :query [:translation/id :translation/text :translation/start :translation/end :translation/lang :translation/visible?]}
  (span 
   {:classes ["translation" (if visible? "translation-visible" "translation-hidden")]}
   (let [translation-failed (= (str/lower-case (:segment/transcription-text (comp/get-computed this))) (str/lower-case text))
         toggle-func (fn [e & args]
                       (. e stopPropagation) ;; necessary to prevent the toggle from happening twice when both onRemove and onClick are called.
                       (js/console.log "Toggle show translation:" e args id)
                       (comp/transact!
                        this
                        `[(com.submerged-structure.mutations/toggle-translation {:translation/id ~id})]))]
     (ui-popup
      {:size "tiny"
       :position "top center"
       :hideOnScroll true
       :header (str "\"" lang "\" translation")
       :content (str 
                 (if visible? "Click to hide translation" "Click to show translation")
                 (when translation-failed " ... Sorry the AI failed to translate this."))
       :trigger (ui-label {:onRemove (when visible? toggle-func)
                           :color (when translation-failed "red")
                           :onClick toggle-func
                           :pointing (if visible? :above :left)
                           :detail (when visible? text)
                           :size :large
                           :content lang})}))))

(def ui-translation (comp/computed-factory Translation {:keyfn :translation/id}))


(defsc Segment [_this {:segment/keys [words translations text]}]
  {:ident :segment/id
   :initial-state (fn [_] {:segment/words (comp/get-initial-state Word {})})
   :query [:segment/id :segment/start :segment/end :segment/text
           {:segment/words (comp/get-query Word)}
           {:segment/translations (comp/get-query Translation)}]}
  (div :.segment-transcription-and-translation
       (p (span :.transcription (interleave (map ui-word words) (repeat " ")))
          (map (fn [translation](ui-translation translation {:segment/transcription-text text})) translations)))) ;; space between words is language dependent may need to change to support eg. Asian languages.
       

(def ui-segment (comp/factory Segment {:keyfn :segment/id}))

(defn scroll-element-to-middle-of-visible-area-below-player
  "Assuming player is a sticky at the top of the screen, scroll element 
   to the vertical center of the screen below the player."
  [element transcript-id]
  (let [element-y-in-viewport (.-top (.. element (getBoundingClientRect)))
        current-top-of-viewport (.. js/window -pageYOffset)
        element-y-in-document (+ element-y-in-viewport current-top-of-viewport)
        scroll-to (- element-y-in-document (/ (+ (ui-player/player-height transcript-id) js/window.innerHeight) 2))]
    (js/window.scrollTo  (clj->js {:left 0
                                   :top scroll-to
                                   :behavior "smooth"}))))

(defn update-current-word [this t id]
  (comp/transact!! this `[(com.submerged-structure.mutations/update-transcript-current-time {:transcript/current-time ~t})])
  (js/console.log "update-current-word" this id t)
  (js/setTimeout
   (fn []
     (when-let [active-word (js/document.querySelector ".word.active")]
       (ui-player/player-on-current-word-update (:ui-period/start (comp/props this)) (:ui-period/end (comp/props this)) (get-in (comp/props this) [:transcript/current-word :word/word]))
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
      (ui-player/player-on-timeupdate ws))))

(defsc TranscriptSwitcherOption
  "db only, for normalisation"
  [this {:transcript/keys [id label]}]
  {:ident :transcript/id
   :query [:transcript/id :transcript/label]
   :initial-state {:transcript/id nil
                   :transcript/label ""}})

#_(def ui-transcript-switcher-option (comp/computed-factory TranscriptSwitcherOption {:keyfn :transcript/id}))

(defsc TranscriptSwitcher [this {:transcript-switcher/keys [all-transcripts]}]
  {:query [{:transcript-switcher/all-transcripts (comp/get-query TranscriptSwitcherOption)}]
   :ident :transcript-switcher/all-transcripts
   :initial-state (fn [_] {:transcript-switcher/all-transcripts
                           (comp/get-initial-state TranscriptSwitcherOption {})})}
  (let [current-transcript (comp/get-computed this :current-transcript)]
    (ui-dropdown
     {:as "h2"
      :basic true
      :fluid true
      :direction "left"
      :upward false
      :scrolling true
      :options (mapv (fn [{:transcript/keys [id label]}] (ui-dropdown-item {:text label :value id :key id :active (= id (comp/get-computed this :current-transcript))})) all-transcripts)

      :onChange (fn [_ev data]
                  (js/console.log "TranscriptSwitcher onChange" data)
                  (comp/transact! this `[(com.submerged-structure.mutations/load-transcript {:transcript/id ~(.-value data)})]))
      :text (:transcript/label (first (filter #(= (:transcript/id %) current-transcript) all-transcripts)))})))

(def ui-transcript-switcher (comp/computed-factory TranscriptSwitcher))

(defsc Transcript [this {prev-segment-start :ui-prev-segment/start
                         current-word-start  :ui-current-word/start
                         current-segment-start  :ui-current-segment/start
                         next-segment-start :ui-next-segment/start
                         prev-word-start :ui-prev-word/start
                         next-word-start :ui-next-word/start

                         :ui/keys [help-hidden]
                         
                         :transcript/keys [id
                                           segments]
                         :ui-player/keys  [doing scroll-to-active]
                         :>/keys          [player
                                           transcript-switcher]}]
  {:ident :transcript/id
   :initial-state (fn [_] {:transcript/current-word [:word/id nil]
                           :ui-period/start 0
                           :ui-period/end nil
                           :ui-current-segment/start nil
                           :ui-player/doing :loading

                           :ui-player/scroll-to-active true
                           :transcript/segments (comp/get-initial-state Segment {})
                           :>/transcript-switcher (comp/get-initial-state TranscriptSwitcher {})
                           :>/player (comp/get-initial-state ui-player/PlayerComponent {})})
   :query [:transcript/id
           :transcript/label
           :transcript/duration

           :ui-player/doing

           [:ui/help-hidden '_]

           :ui-player/scroll-to-active

           :ui-period/start
           :ui-period/end

           :ui-prev-segment/start
           :ui-current-word/start
           :ui-current-segment/start
           :ui-next-segment/start
           :ui-prev-word/start
           :ui-next-word/start

           {:transcript/current-word [:word/id
                                      :word/word]}

           {:transcript/segments (comp/get-query Segment)}
           {:>/transcript-switcher (comp/get-query TranscriptSwitcher)}
           {:>/player (comp/get-query ui-player/PlayerComponent)}]}
  (div :.ui.container
       (ui-transcript-switcher transcript-switcher {:current-transcript id})
       (ui-sticky
        {:id (str "player-" id)
         :context (.. js/document -body (querySelector (str "#transcript-" id)))
         :styleElement {:backgroundColor "white"}
         :children
         (div
          (ui-player/ui-player
           player
           {:onTimeupdate (transcript-on-timeupdate this id)})
          (semantic-ui-segment/ui-segment
           {:basic true
            :textAlign "center"}
           (ui-player/ui-player-controls this doing scroll-to-active  prev-word-start prev-segment-start current-word-start current-segment-start next-word-start next-segment-start)))})
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
                (li "Hover over playback control buttons below the waveform with your cursor for more information about what they do."))))))
       (confidence-key)
       (div :.transcript
            {:id (str "transcript-" id)}
            (map ui-segment segments))))

(def ui-transcript (comp/factory Transcript {:keyfn :transcript/id}))


(defsc Root [this {:keys [:root/current-transcript]}]
  {:initial-state
   (fn [_]
     {:root/current-transcript (comp/get-initial-state Transcript {})})
   :query [{:root/current-transcript (comp/get-query Transcript)}]}
  (dom/div (ui-transcript current-transcript)))


(comment
  (.pause (ui-player/get-player))
  (.play (ui-player/get-player))
  (.getDuration (ui-player/get-player))

  (.getCurrentTime (ui-player/get-player))
  (comp/get-query Root)



  (comp/get-computed TranscriptSwitcher :current-transcript)

  (comp/get-initial-state Root {})

  (-> (comp/get-query Root) :root/current-transcript  vals first meta)

  (require '[clojure.walk :as w])
  (w/postwalk
   (fn [x]
     (cond
       (map-entry? x) x
       (vector? x) (list
                    (get-in (meta x)
                            [:component :displayName]
                            :NO-COMPONENT)
                    (filterv map? x))

       :default x))
   (comp/get-query Root))
  ;; => (:NO-COMPONENT
  ;;     [#:root{:current-transcript
  ;;             (:NO-COMPONENT [#:transcript{:segments (:NO-COMPONENT [#:segment{:words (:NO-COMPONENT [])}])}])}])

  (defn list-all-properties [obj]
    (let [props (atom #{})]
      (loop [proto obj]
        (when proto
          (do
            (.forEach (js/Object.getOwnPropertyNames proto)
                      (fn [prop] (swap! props conj prop)))
            (recur (.-prototype (js/Object.getPrototypeOf proto))))))
      @props))

  (list-all-properties (-.media (ui-player/get-player)))



  (require 'clojure.spec.alpha 'edn-query-language.core)
  (clojure.spec.alpha/explain
   :edn-query-language.core/query
   (comp/get-query com.submerged-structure.ui/Root))

  (comp/get-initial-state Transcript {})
  mock-data/transcript
  (comp/get-query com.submerged-structure.ui/Transcript)
  (com.fulcrologic.fulcro.components/get-initial-state Root {})
  
  (fn/tree->db  Root (com.fulcrologic.fulcro.components/get-initial-state Root) true)
  

  
  (comp/transact! com.submerged-structure.app/app '[(api/update-current-transcript {:transcript/id "48658c6e-6508-48f3-b581-eccb13e18082"})])
  )
  



  
  
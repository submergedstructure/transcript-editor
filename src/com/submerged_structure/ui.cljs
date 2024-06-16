(ns com.submerged-structure.ui
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div h1 li p ul span i]]
            [com.fulcrologic.fulcro.algorithms.normalize :as fn]
            [com.fulcrologic.fulcro.algorithms.denormalize :as fdn]
            [com.fulcrologic.fulcro.application :as app]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.data-fetch :as df]
            [com.fulcrologic.fulcro.raw.components :as rc]
            [com.submerged-structure.mock-data :as mock-data]
            [com.submerged-structure.mutations :as api]
            [com.submerged-structure.app :as ss]
            [goog.functions :as gf]
            [com.submerged-structure.confidence-to-color :as c-to-c]
            [com.fulcrologic.semantic-ui.modules.sticky.ui-sticky :refer [ui-sticky]]
            [com.fulcrologic.semantic-ui.modules.dropdown.ui-dropdown :refer [ui-dropdown]]
            [com.fulcrologic.semantic-ui.modules.dropdown.ui-dropdown-item :refer [ui-dropdown-item]]
            #_[com.fulcrologic.semantic-ui.elements.container.ui-container :refer [ui-container]]
            [com.submerged-structure.ui-player :as ui-player]))



(defsc Word [this {:word/keys [word active score start]}]
  {:ident :word/id
   :initial-state {:word/active false}
   :query [:word/id :word/word :word/start :word/end :word/active :word/score]}
  (span {:data-c score
         :className (when active "active")
         :onClick (fn [ws] (ui-player/on-word-click ws start))
         :style (c-to-c/confidence-to-style score)}
        word))

(def ui-word (comp/factory Word {:keyfn :word/id}))


(defsc Segment [this {:keys [segment/words]}]
  {:ident :segment/id
   :initial-state (fn [_] {:segment/words (comp/get-initial-state Word {})})
   :query [:segment/id {:segment/words (comp/get-query Word)}]}
  (p (interleave (map ui-word words) (repeat " ")))) ;; space between words is language dependent may need to change to support eg. Asian languages.

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

(defn update-current-word [t this id]
  (comp/transact!! this [(api/update-transcript-current-time {:transcript/id id :transcript/current-time t})])
  (js/console.log "update-current-word" this id t)
  (js/setTimeout
   (fn []
     (when-let [active-word (js/document.querySelector ".active")]
       (scroll-element-to-middle-of-visible-area-below-player active-word id)))
   0))

(def update-current-word-once-per-frame
  "called when we don't have a start or end time for the current period."
  (gf/rateLimit update-current-word (/ 1000 10))) ; 10 frames per second

(defn update-current-word-throttled [t this id]
  (let [props (comp/props this)
        start (:ui-period/start props)
        end (:ui-period/end props)]
    (if (some nil? [start end]);check if either start or end are nil
      (update-current-word-once-per-frame this id t)
      (when-not (<= start t end)
        (js/console.log "throttled according to time period " t start end)
        (update-current-word t this id)))))

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
      (update-current-word-throttled current-time this id)
      (ui-player/player-on-timeupdate ws))))

#_(defsc TranscriptSwitcherOption [this {:transcript/keys [id label]}]
  {:ident :transcript/id
   :query [:transcript/id :transcript/label]
   :initial-state {:transcript/id nil
                   :transcript/label ""}}
  )

#_(def ui-transcript-switcher-option (comp/computed-factory TranscriptSwitcherOption {:keyfn :transcript/id}))

(declare Transcript)

(defsc TranscriptSwitcher [this {:transcript-switcher/keys [all-transcripts]}]
  {:query [{:transcript-switcher/all-transcripts [:transcript/id :transcript/label]}]
   :ident :transcript-switcher/all-transcripts
   :initial-state (fn [_] {:transcript-switcher/all-transcripts
                           {:transcript/id nil
                            :transcript/label ""}})}
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
                  (comp/transact! this [(api/update-current-transcript {:transcript/id (.-value data)})])
                  #_(df/load! this :root/current-transcript Transcript))
      :text (:transcript/label (first (filter #(= (:transcript/id %) current-transcript) all-transcripts)))})))

(def ui-transcript-switcher (comp/computed-factory TranscriptSwitcher))

(defsc Transcript [this {:transcript/keys [id
                                           label
                                           segments
                                           duration]
                         :ui-period/keys  [start end]
                         :ui-player/keys  [doing]
                         :>/keys          [player
                                           transcript-switcher]}]
  {:ident :transcript/id
   :initial-state (fn [_] {:ui-period/start 0
                           :ui-period/end nil
                           :transcript/segments (comp/get-initial-state Segment {})
                           :>/transcript-switcher (comp/get-initial-state TranscriptSwitcher {})
                           :>/player (comp/get-initial-state ui-player/PlayerComponent {})})
   :query [:transcript/id
           :transcript/label
           :transcript/duration
           :ui-player/doing
           :ui-period/start
           :ui-period/end
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
          (ui-player/ui-player-controls (ui-player/get-player) doing duration))})
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
  (app/current-state Root)
  (fn/tree->db  Root (com.fulcrologic.fulcro.components/get-initial-state Root) true)
  (df/load! com.submerged-structure.app/app :transcript (rc/nc '[*]))
  (df/load! com.submerged-structure.app/app [:transcript/id "2221f28c-0f2d-479b-b4a7-80924c80721c"] Transcript)
  ;; Load this => 
  ;; Pathom 2: will get back from server {:sequence [{..}, ..]} - i.e. a *vector*
  ;; even though the resolver returns a lazy seq
  ;; Pathom 3: returns a list: {:sequence ({..}, ..)}
  (df/load! com.submerged-structure.app/app :sequence (rc/nc [:tst/id :tst/val]))


  (require '[com.submerged-structure.app :as ss])
  (api/words-from-state (app/current-state ss/app))
  (fdn/db->tree [#:root{:current-transcript
                        [:transcript/id
                         #:transcript{:segments
                                      [:segment/id #:segment{:words [:word/id :word/start :word/end]}]}]}]
                (app/current-state ss/app) (app/current-state ss/app))

  (get (app/current-state ss/app) :word/id)
  (api/find-current-period (app/current-state ss/app) 27.91 "2221f28c-0f2d-479b-b4a7-80924c80721c")
  (api/find-first-word-not-ended (app/current-state ss/app) 0.0)
  (api/find-last-word-ended (app/current-state ss/app) 0.0)

  (fdn/db->tree [#:root{:current-transcript
                        [:transcript/id
                         #:transcript{:segments
                                      [:segment/id #:segment{:words [:word/id :word/start :word/end]}]}]}]
                (app/current-state ss/app) (app/current-state ss/app)))
  



  
  
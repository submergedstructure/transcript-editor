(ns com.submerged-structure.components.controls.progressive-reveal-controls
  (:require
   [com.fulcrologic.fulcro.dom :as dom]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc fragment]]
   [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
   [com.fulcrologic.semantic-ui.modules.popup.ui-popup-header :refer [ui-popup-header]]
   [com.fulcrologic.semantic-ui.elements.button.ui-button :refer [ui-button]]
   [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
   [com.fulcrologic.semantic-ui.icons :as i]

   [com.submerged-structure.components.controls.common :as common-to-controls]
   
   [com.submerged-structure.components.controls.progressive-reveal-settings :refer [ui-progressive-reveal-settings ProgressiveRevealSettings]]))

(defn element-y-in-viewport [element]
  (.. element
      (getBoundingClientRect)
      -top))

(defn save-current-scroll-position-and-after-timeout-scroll-there [dom-element]
  (let [old-element-y-in-viewport (element-y-in-viewport dom-element)]
    (js/setTimeout (fn []
                     (let [scroll-by (- (element-y-in-viewport dom-element) old-element-y-in-viewport)]
                       (js/window.scrollBy  (clj->js {:left 0
                                                      :top scroll-by
                                                      :behavior "smooth"}))
                       (js/console.log "scrolling by" scroll-by)))
                   100)))

(comment 
  (def element (js/document.querySelector ".segment-transcription-and-translation.active")))


(defsc ProgressiveRevealControls [this {#_#_:ui-morphological-info-grid-control/keys [any-visible?]
                                        :>/keys [progressive-reveal-settings]}]
  {:ident :transcript/id
   :query [:transcript/id {:>/progressive-reveal-settings (comp/get-query ProgressiveRevealSettings)}]
   :initial-state (fn [_] {:>/progressive-reveal-settings (comp/get-initial-state ProgressiveRevealSettings)})}
  (fragment
   (ui-popup
    (merge
     {:key :progressive-reveal-segments-upto-current
      :header "Progressively reveal more about sentences played so far. \"f\""
      :content (fragment
                (ui-popup-header "Repeatedly press to progressively reveal more about sentences played so far. \"f\"")
                (dom/p "Reveal more about the sentences played so far.")
                (dom/p "Keyboard shortcut \"f\" to reveal.")
                #_(dom/p "Open the settings dialogue to the right to control what is revealed on pressing this button."))
      :trigger (ui-button {:id "progressive-reveal-segments-upto-current"
                           :icon (fragment (ui-icon {:name i/eye-icon}) (ui-icon {:name i/clock-icon}))
                           :onClick
                           (fn [& _args]
                             (comp/transact!
                              this
                              `[(com.submerged-structure.mutations.progressive-reveal/progressive-reveal-segments-upto-current {})])
                             (when-let [element (js/document.querySelector ".segment-transcription-and-translation.active")]
                               (save-current-scroll-position-and-after-timeout-scroll-there element)))
                           #_#_:positive any-visible?})}
     common-to-controls/common-options-for-popups-of-controls))
   (ui-popup
    (merge
     {:key :reset-reveal-state-of-all
      :header "Hide ALL info about sentences. \"e\""
      :content (fragment #_(dom/p "Set all the sentences to the first state set in the settings dialogue to the right.")
                (dom/p "Keyboard shortcut \"e\"."))
      :trigger (ui-button {:id "reset-reveal-state-of-all"
                           :icon i/low-vision-icon
                           :onClick
                           (fn [& _args]
                             (comp/transact!
                              this
                              `[(com.submerged-structure.mutations.progressive-reveal/reset-reveal-state-of-all {})]))
                           #_#_:positive any-visible?})}
     common-to-controls/common-options-for-popups-of-controls))
   (ui-progressive-reveal-settings progressive-reveal-settings)))

(def ui-progressive-reveal-controls (comp/factory ProgressiveRevealControls {:keyfn :transcript/id}))

(ns com.submerged-structure.components.controls.progressive-reveal-settings
  (:require
   [com.fulcrologic.fulcro.dom :as dom]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc fragment]]
 
   [com.fulcrologic.semantic-ui.modules.modal.ui-modal :refer [ui-modal]]

   [com.fulcrologic.semantic-ui.elements.segment.ui-segment :refer [ui-segment]]
  
   [com.fulcrologic.semantic-ui.elements.button.ui-button :refer [ui-button]]
   [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
   [com.fulcrologic.semantic-ui.icons :as i]
   [com.submerged-structure.progressive-reveal-states :as prs]
   
   [com.submerged-structure.transcript-scroll :as transcript-scroll]))

(defsc ProgressiveRevealSetting [this {:progressive-reveal-setting/keys [id active]}]
  {:ident :progressive-reveal-setting/id
   :query [:progressive-reveal-setting/id :progressive-reveal-setting/active] }
  (ui-segment
   {:key :progressive-reveal-setting
    :color (if active "green" "grey")
    :size "large"
    :inverted true
    :raised true
    
    :onClick
    (fn [& _args]
      (comp/transact!
       this
       `[(com.submerged-structure.mutations.progressive-reveal/toggle-reveal-state-active {:progressive-reveal-setting/id ~id})])
      (transcript-scroll/scroll-to-active-element-after-time-out))}
   
   (ui-icon {:name (if active i/check-circle-icon i/times-circle-icon)}) (get prs/description-of-all-possible-reveal-progressions id)))

(def ui-progressive-reveal-setting (comp/factory ProgressiveRevealSetting {:keyfn :progressive-reveal-setting/id}))

(defsc ProgressiveRevealSettings [this {:keys [progressive-reveal-settings]}]
  {:ident (fn [_] [:component/id :progressive-reveal-settings-control])
   :query [{[:progressive-reveal-settings '_] (comp/get-query ProgressiveRevealSetting)}]
   :initial-state {}}
  (let [icons (fragment (ui-icon {:name i/eye-icon})
                        (ui-icon {:name i/cog-icon}))]
    (ui-modal
     {:key :progressive-reveal-settings
      :closeIcon true
      :trigger (ui-button
                {:id "settings"
                 :icon icons})}
     (dom/div :.header icons "Progressive Reveal Settings")
     (dom/div :.content
              (dom/p "The progressive reveal button " (ui-icon {:name i/eye-icon}) (ui-icon {:name i/clock-icon}) " (shortcut key \"f\") progressively reveals more about played sentences, what states should the text go through?")
              (map ui-progressive-reveal-setting progressive-reveal-settings)
              (dom/p "Keyboard shortcut \"r\" for these settings."))))
  )

(def ui-progressive-reveal-settings (comp/factory ProgressiveRevealSettings))

(comment prs/reveal-progression)


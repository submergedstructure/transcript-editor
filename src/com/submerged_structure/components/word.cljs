(ns com.submerged-structure.components.word
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [div h1 h2 h3 li p ul span i b a]]

            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.submerged-structure.confidence-to-color :as c-to-c]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
            [com.fulcrologic.semantic-ui.modules.popup.ui-popup-header :refer [ui-popup-header]]
            [com.submerged-structure.components.player :as player]
            [com.fulcrologic.semantic-ui.icons :as i]
            [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]

            [clojure.string :as str]

            [com.submerged-structure.components.morphological-features-popup-content :as morphological-features-popup-content]))

(defn dict-link
  "More than one link for when word is two space separated lemmas."
  [word]
  (for [w (str/split word #" ")]
    (span (a {:href (str "https://www.diki.pl/slownik-angielskiego?q=" w)
              :target "_blank"}
             w) " ")))

(defsc Word [_this {:word/keys [word active score start lemma pos_explained is_morphed norm]
                    :>/keys [morphological-features]}]
  {:ident :word/id
   :initial-state {}
   :query [:word/id :word/word :word/start :word/end :word/active :word/score :word/morph :word/lemma :word/pos :word/pos_explained :word/is_morphed :word/norm
           {:>/morphological-features (comp/get-query morphological-features-popup-content/MorphologicalFeaturesPopupContent)}]}
  (ui-popup
   {:size "tiny"
    :hoverable true
    :position "bottom center"
    :hideOnScroll true
    :trigger (span {:data-c score
                    :classes [(when active "active") "word"]
                    :onClick (fn [ws] (player/on-word-click ws start))
                    :style (c-to-c/confidence-to-style score)}
                   word)}
   (ui-popup-header
    {}
    (span
     (when is_morphed
       (span nil (dict-link lemma) "  " (ui-icon {:name i/arrow-right-icon}) " "))
     (dict-link norm)
     (span nil " (" pos_explained ")")))
   (morphological-features-popup-content/ui-morphological-features-popup-content morphological-features)))

(def ui-word (comp/factory Word {:keyfn :word/id}))

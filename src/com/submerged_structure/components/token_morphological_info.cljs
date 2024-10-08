(ns com.submerged-structure.components.token-morphological-info
  (:require
   [com.fulcrologic.fulcro.dom :as dom  :refer [h3 h4 div span a]]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc fragment]]
  
   [com.fulcrologic.semantic-ui.modules.popup.ui-popup-content :refer [ui-popup-content]]
  
   [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
   [com.fulcrologic.semantic-ui.icons :as i]
   [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
   [com.fulcrologic.semantic-ui.elements.divider.ui-divider :refer [ui-divider]]
   [com.fulcrologic.semantic-ui.elements.segment.ui-segment :refer [ui-segment]]
   [com.fulcrologic.semantic-ui.elements.label.ui-label :refer [ui-label]]
  
   [clojure.string]

   [com.submerged-structure.spacy-grammar :as spacy-grammar]))


(defn human-readable-attribute-value-html-with-grammar-styling [morph-map attribute-name]
  (let [attribute-value (get morph-map attribute-name)]
    (span
     {:class (str attribute-name "_" attribute-value)}
     (spacy-grammar/human-readable-attribute-value-from-morph-map morph-map attribute-name))))

(comment
  js/Window
  (.matchMedia js/window "(pointer: coarse)")
  (.-matches (.matchMedia js/window "(pointer: coarse)"))
  (.-ontouchstart js/window)
  (.-maxTouchPoints js/navigator))
(defn is-touch-device []
  (or (.-ontouchstart js/window)
      (> (.-maxTouchPoints js/navigator) 0)
      (.-matches (.matchMedia js/window "(pointer: coarse)"))))


(defn ui-dict-link-and-popup [w]
  (ui-popup
   {:trigger
    (a {:href (str "https://www.diki.pl/slownik-angielskiego?q=" w)
        :target "_blank"}
       w)}
   (ui-popup-content
    {}
    (str "Click to open dictionary entry for \"" w "\"."))))

(defn ui-dict-links-and-popup
  "More than one link for when word is two space separated lemmas."
  [word]
  (let [[w1 & [w2]] (clojure.string/split word #" ")]
    (fragment
     (ui-dict-link-and-popup w1)
     (when w2
       (fragment " " (ui-dict-link-and-popup w2))))))

(defn ui-morph-attribute [morph-map attribute-name & {:keys [detail-options]}]
  (when-not ((into #{} (vals spacy-grammar/attributes-that-are-subtypes-of-other-attributes))  attribute-name)
    (let [subtype (get spacy-grammar/attributes-that-are-subtypes-of-other-attributes attribute-name)]
      (div :.item
           (div :.header
                (assoc (if detail-options detail-options {}) :key attribute-name :pointing "right")
                (spacy-grammar/human-readable-attribute-name attribute-name)
                (when (and subtype
                           (get morph-map subtype))
                  (fragment
                   " ("
                   (spacy-grammar/human-readable-attribute-name subtype)
                   ")")))
           (div :.description (human-readable-attribute-value-html-with-grammar-styling morph-map attribute-name)
                (when (and subtype
                           (get morph-map subtype))
                  (fragment " " (human-readable-attribute-value-html-with-grammar-styling morph-map subtype))))))))

(def html-classes-to-display ["Case" "Number" "Person" "Gender" "Animacy"])


(defn morph-html-css-classes
  "`pos` is passed when we want to display the morph classes in the context of the *dictionary form* of the part of speech."
  [morph & {:keys [pos]}]
  (let [relevant-morph-map (select-keys (spacy-grammar/non-redundant-morphological-features morph) html-classes-to-display)]
    (mapv
     (fn [[attribute-name attribue-value]] (str attribute-name "_" attribue-value))
     (if pos (spacy-grammar/morph-map-for-lemma pos relevant-morph-map) relevant-morph-map))))


(defn ui-lemma-and-norm [lemma norm active is_morphed morph pos link?]
  (fragment {}
            (when is_morphed
              (fragment {}
                        (span {:classes (concat (morph-html-css-classes morph :pos pos))}
                              (if link? (ui-dict-links-and-popup lemma) lemma))
                        "  "
                         (ui-icon {:name i/arrow-right-icon})
                         " "))
            (span {:classes (concat ["inflected_word"] (morph-html-css-classes morph))}
                  (span {:classes (when active ["active"])}
                        (if link? (ui-dict-links-and-popup norm) norm)))))

(defn ui-form [pos morph-map]
  (div {:classes (concat ["ui" "label" "pointing" "big"] (morph-html-css-classes (str "Case=" (get morph-map "Case"))))}
       (spacy-grammar/word-form-description morph-map pos)))


(defn ui-condensed-morph-details [this id active is_morphed morph pos lemma norm]
  (div :.item
   {:classes ["column"] #_(into [] (concat (if-not is_morphed ["two"] [(if (< (count (str lemma norm)) 16) "three" "four")]) [ "wide" "column"]))}
   (let [{:strs [Case]} (spacy-grammar/morphological-features-str-to-map morph)] 
     (ui-segment
      {:className (clojure.string/join
                   " "
                   (concat ["grammar_highlighting" "grammar_highlighting_background"]
                           (morph-html-css-classes (str "Case=" Case))
                           (when active ["active"])))


       :compact true
       :children
       (fragment
        (ui-label
         {:corner "right"
          :icon
          (ui-icon {:name i/close-icon
                    :onClick (fn [e & args]
                               (. e stopPropagation) ;; necessary to prevent the toggle from happening twice when both onRemove and onClick are called.
                               (js/console.log "Hide morph details:" e args id)
                               (comp/transact!
                                this
                                `[(com.submerged-structure.mutations/toggle-visibility-of-morphological-details-for-token {:token/id ~id})]))})})
        (h3 (ui-lemma-and-norm lemma norm active is_morphed morph pos false)))}))))

(defsc TokenMorphologicalInfo [this {:token/keys [id morph lemma pos is_morphed norm morphological-details-visible?]} {:word/keys [active]}]
  {:ident :token/id
   :initial-state {}
   :query [:token/id 
           :token/morph :token/lemma :token/pos :token/is_morphed :token/norm :token/morphological-details-visible?]}
  (when morphological-details-visible?
    (case pos
      ("SYM" "PUNCT") (div :.item.column (div :.segment norm))
      #_ (ui-condensed-morph-details this id active is_morphed morph pos lemma norm)
      (ui-popup
       {:hoverable true

        :popperModifiers #js [#js {"name" "preventOverflow"
                                   "options" #js {"padding" 8
                                                  "RootBoundary" "viewport"}}]
        :position "top right"
        :hideOnScroll true
        :on [(if (is-touch-device) "click" "hover")]

        :trigger (ui-condensed-morph-details this id active is_morphed morph pos lemma norm)}

       (ui-popup-content
        {}
        (div
         :.grammar_highlighting
         (let [norm-html (span {:classes [(when active "active")]} (ui-dict-links-and-popup norm))
               lemma-html (ui-dict-links-and-popup lemma)
               morph-map (spacy-grammar/non-redundant-morphological-features morph)]
           (fragment
            (h3 (ui-lemma-and-norm lemma norm active is_morphed morph pos false))
            (ui-form pos morph-map)

            (if (not-empty (spacy-grammar/word-attributes-that-inflect-word morph))
              (fragment (if is_morphed
                          (if (re-find #" " lemma); more than one lemma
                            (h4 {} "The root words \"" lemma-html "\" become \"" norm-html "\" here, \"" norm-html "\" is:")
                            (h4 {} "The root word \"" lemma-html "\" becomes \"" norm-html "\" here, \"" norm-html "\" is:"))
                          (h4 {} "The root word \"" norm-html "\" does not change here, \"" norm-html "\" is:"))
                        (div :.ui.list.animated {} (mapv (partial ui-morph-attribute (spacy-grammar/non-redundant-morphological-features morph)) (spacy-grammar/word-attributes-that-inflect-word morph))))
              (h4 {} "The root word \"" norm-html "\" Never changes! Yay!!"))
            (when (not-empty (spacy-grammar/word-attributes-that-are-properties-of-the-word-itself morph))
              (div {}
                   (ui-divider)
                   (h4 {} "The root word itself, \"" (ui-dict-links-and-popup lemma) "\" is or has:")
                   (div :.ui.list.animated {} (mapv (partial ui-morph-attribute morph-map) (spacy-grammar/word-attributes-that-are-properties-of-the-word-itself morph)))))))))))))

(def ui-token-morphological-info (comp/computed-factory TokenMorphologicalInfo {:keyfn :token/id}))

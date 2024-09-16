(ns com.submerged-structure.components.word-with-morphological-features-popup
  (:require
   [com.fulcrologic.fulcro.dom :as dom  :refer [h3 h4 div span a]]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc fragment]]

   [com.fulcrologic.semantic-ui.modules.popup.ui-popup-content :refer [ui-popup-content]]

   [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
   [com.fulcrologic.semantic-ui.icons :as i]
   [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
   [com.fulcrologic.semantic-ui.elements.divider.ui-divider :refer [ui-divider]]

   [clojure.string]

   [com.submerged-structure.confidence-to-color :as c-to-c]
   [com.submerged-structure.components.player :as player]
   [com.submerged-structure.spacy-grammar :as spacy-grammar]))


(defn human-readable-attribute-value-html-with-grammar-styling [morph-map attribute-name]
  (let [attribute-value (get morph-map attribute-name)]
    (span
     {:class (str attribute-name "_" attribute-value)}
     (spacy-grammar/human-readable-attribute-value-from-morph-map morph-map attribute-name))))
     

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

(defn word-attributes-that-are-properties-of-the-word-itself [morph]
  (let [morph-map (spacy-grammar/non-redundant-morphological-features morph)]
    (remove (set spacy-grammar/attribute-names-that-affect-word-ending) (keys morph-map))))


(def html-classes-to-display ["Case" "Number" "Person" "Gender" "Animacy"])

(defn morph-map-for-lemma
  "Given morph map of an inflected word, return morph map for the dictionary form of the word.
   
   For dictionary form if it is a noun gender and animacy do not change.
   Otherwise no person, no number; nominative case, masculine gender for words that have case."
  [pos {:strs [Case Number Gender Animacy]}]
  (if Case ;either adjective type word or noun
    (if (= pos "NOUN")
      {"Case" "Nom"
       "Number" (if (= Number "Plur") "Sing" Number)
       "Gender" Gender
       "Animacy" Animacy}
      {"Case" "Nom"
       "Number" (if (= Number "Plur") "Sing" Number)
       "Gender" "Masc"})
    {}))


(defn morph-html-css-classes
  "`pos` is passed when we want to display the morph classes in the context of the *dictionary form* of the part of speech."
  [morph & {:keys [pos]}]
  (let [relevant-morph-map (select-keys (spacy-grammar/non-redundant-morphological-features morph) html-classes-to-display)]
   (mapv 
    (fn [[attribute-name attribue-value]] (str attribute-name "_" attribue-value))
    (if pos (morph-map-for-lemma pos relevant-morph-map) relevant-morph-map))))
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

(defn word-attributes-that-inflect-word [morph]
  (let [morph-map (spacy-grammar/morphological-features-str-to-map morph)]
    (filter (set (keys morph-map)) spacy-grammar/attribute-names-that-affect-word-ending)))

(defn condensed-morph-details [is_morphed morph pos lemma norm]
  (let [morph-map (spacy-grammar/non-redundant-morphological-features morph)
        word-attributes-that-inflect-word (word-attributes-that-inflect-word morph)] 
   (div
   :.ui.column.grammar_highlighting
   {}
   (div
    :.ui.message
    {}
    (ui-icon {:name i/close-icon})
    (div {}
         (when is_morphed
           (fragment
            (span {:classes (concat ["inflected_word"] (morph-html-css-classes morph :pos pos))} (ui-dict-links-and-popup lemma))
            "  "
            (ui-icon {:name i/arrow-right-icon})
            " "))
         (span {:classes (concat ["inflected_word"] (morph-html-css-classes morph))} (ui-dict-links-and-popup norm)))
    (div {:classes (concat ["ui" "label" "pointing" "big"] (morph-html-css-classes (str "Case=" (get morph-map "Case"))))}
         (spacy-grammar/word-form-description morph-map pos))
    (when (empty? word-attributes-that-inflect-word)
      (h4 {} "Root word \"" (ui-dict-links-and-popup lemma) "\" never changes! Yay!!"))
  
  
    (div
     {}
     (when (not-empty (word-attributes-that-are-properties-of-the-word-itself morph))
       (fragment {}
                 (ui-divider)
                 (h4 {} "The root word itself, \"" (ui-dict-links-and-popup lemma) "\" is or has:")
                 (div :.ui.list.animated.tiny {} (mapv (partial ui-morph-attribute morph-map) (word-attributes-that-are-properties-of-the-word-itself morph))))))))))

(defsc WordMorphologicalInfo [_this {:word/keys [active morph lemma pos is_morphed norm]}]
  {:ident :word/id
   :initial-state {}
   :query [:word/id :word/active
           :word/morph :word/lemma :word/pos :word/is_morphed :word/norm]}
  (when (not-empty pos)
    (ui-popup
     {:hoverable true

      :popperModifiers #js [#js {"name" "preventOverflow"
                                 "options" #js {"padding" 8
                                                "RootBoundary" "viewport"}}]
      :position "top center"
      :hideOnScroll true
      :on [(if (is-touch-device) "click" "hover")]

      :trigger (condensed-morph-details is_morphed morph pos lemma norm)}
     (ui-popup-content
      {}
      (div
       :.grammar_highlighting
       {}
       (if (not-empty (word-attributes-that-inflect-word morph))
         (fragment (if is_morphed
                     (if (re-find #" " lemma); more than one lemma
                       (h4 {} "The root words \"" (ui-dict-links-and-popup lemma) "\" become \"" (ui-dict-links-and-popup norm) "\" here, \"" (ui-dict-links-and-popup norm) "\" is:")
                       (h4 {} "The root word \"" (ui-dict-links-and-popup lemma) "\" becomes \"" (ui-dict-links-and-popup norm) "\" here, \"" (ui-dict-links-and-popup norm) "\" is:"))
                     (h4 {} "The root word \"" (ui-dict-links-and-popup lemma) "\" does not change here, is:"))
                   (div :.ui.list.animated {} (mapv (partial ui-morph-attribute (spacy-grammar/morphological-features-str-to-map morph)) (word-attributes-that-inflect-word morph))))
         (fragment(h4 {} "The root word \"" (ui-dict-links-and-popup lemma) "\" Never changes! Yay!!")))
       )))))

(def ui-word-morphological-info (comp/factory WordMorphologicalInfo {:keyfn :word/id}))

(defsc WordMorphologicalInfoGrid [_this {:segment/keys [words]}]
  {:ident :segment/id
   :query [:segment/id :segment/words {:segment/words (comp/get-query WordMorphologicalInfo)}]}
  (div :.ui.grid.relaxed.stackable
       (div :.three.column.row
            (map (fn [morphological-info] (ui-word-morphological-info morphological-info)) words))))

(def ui-word-morphological-info-grid (comp/factory WordMorphologicalInfoGrid {:keyfn :segment/id}))

(defsc WordWithMorphPopup [_this {:word/keys [word active score start morph]}
                           {:transcript/keys [display-type]}]
  {:ident :word/id
   :initial-state (fn [_] {})
   :query [:word/id :word/word :word/start :word/end :word/active :word/score
           :word/morph :word/lemma :word/pos :word/is_morphed :word/norm
           ]}
  
  (span {:classes (concat [(when active "active") "word"] (morph-html-css-classes morph))
         :onClick (when-not (is-touch-device) (fn [ws] (player/on-word-click ws start)))
         :style (when (= display-type :confidence) (c-to-c/confidence-to-style score))}
        word))


(def ui-word-with-morph-popup (comp/computed-factory WordWithMorphPopup {:keyfn :word/id}))
         


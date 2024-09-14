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

(defn word-attributes-that-are-properties-of-the-word-itself [morph-map]
  (remove (set spacy-grammar/attribute-names-that-affect-word-ending) (keys morph-map)))


(defn ui-all-a-words-morph-properties [pos morph-map lemma norm is-morphed]
  (let [word-attributes-that-inflect-word
        (filter (set (keys morph-map)) spacy-grammar/attribute-names-that-affect-word-ending)]
    (div :.grammar_highlighting {}
         (if (not-empty word-attributes-that-inflect-word)
           (fragment {}
            (if is-morphed 
              (h4 {} "The root word \"" (ui-dict-links-and-popup lemma) "\" becomes \"" (ui-dict-links-and-popup norm) "\" here, \"" (ui-dict-links-and-popup norm) "\" is:")
              (h4 {} "The root word \"" (ui-dict-links-and-popup lemma) "\" does not change here! It is:"))
            (div :.ui.list.animated.large {} (mapv (partial ui-morph-attribute morph-map) word-attributes-that-inflect-word)))
           (if is-morphed
             (h4 {} "Root word \"" (ui-dict-links-and-popup lemma) "\" becomes \"" (ui-dict-links-and-popup norm) "\" here, it is changed for easier pronunciation with the following word and not grammatical reasons.")
             (h4 {} "Root word \"" (ui-dict-links-and-popup lemma) "\" never changes! Yay!!")))
         
         (when (not-empty (word-attributes-that-are-properties-of-the-word-itself morph-map))
           (fragment {}
            (ui-divider)
            (h4 {} "The root word itself, \"" (ui-dict-links-and-popup lemma) "\" is or has:")
            (div :.ui.list.animated.large {} (mapv (partial ui-morph-attribute morph-map) (word-attributes-that-are-properties-of-the-word-itself morph-map))))))))

(comment 
  (def word #:word{:start 0.15999999999999998,
                   :is_morphed false,
                   :active false,
                   :id "305f1411-6472-4996-9c66-9a72e2f49f42",
                   :word "Właśnie",
                   :score 0.69,
                   :lemma "właśnie",
                   :norm "właśnie",
                   :morph "",
                   :end 1.3599999999999999,
                   :pos "ADV"})
  (def morph-map (spacy-grammar/morphological-features-str-to-map (:word/morph word)))

  (let [
               
               lemma (:word/lemma word)
               norm (:word/norm word)
               is-morphed (:word/is_morphed word)
               word-attributes-that-inflect-word (keep (fn [attribute-name] (when (get (spacy-grammar/morphological-features-str-to-map (:word/morph word)) attribute-name)
                                                                              (ui-morph-attribute morph-map attribute-name))))
               word-attributes-that-are-properties-of-the-word-itself (keep (fn [attribute-name] (when (not (spacy-grammar/attribute-names-that-affect-word-ending attribute-name))
                                                                                                    (ui-morph-attribute morph-map attribute-name)))
                                                                          (keys morph-map))]
                   
           #_(keep (fn [attribute-name] (when (get (morphological-features-str-to-map (:word/morph word)) attribute-name)
                                        (morph-label morph-map attribute-name))
                   attribute-names-that-inflect-word))
           #_(keep (fn [attribute-name] (when (not (attribute-names-that-inflect-word attribute-name))
                                        (morph-label morph-map attribute-name)))
                 (keys morph-map))
           #_(if is-morphed
             (ui-divider {:content (str "The word " lemma " becomes " norm " because it is:")})
             (ui-divider {:content (str "The word " lemma " does not change because it is already:")}))
           (ui-divider {:content (str "The word " lemma " becomes " norm " because it is:")} )
           (fragment {}
                     (if is-morphed
                       (ui-divider {} (str "The word " lemma " becomes " norm " because it is:"))
                       (ui-divider {:content (str "The word " lemma " does not change because it is already:")}))
                     (div {} word-attributes-that-inflect-word))
           #_(ui-morph-properties morph-map (:word/lemma word) (:word/norm word) (:word/is_morphed word))
           (when (not-empty word-attributes-that-are-properties-of-the-word-itself)
             (fragment {}
                       (ui-divider {:content (str "The word " lemma " has the following grammatical properties:")})
                       (div {} word-attributes-that-are-properties-of-the-word-itself))))
         
         )
(def html-classes-to-display ["Case" "Number" "Person" "Gender" "Animacy"])

(defn morph-map-for-lemma
  "For dictionary form if it is a noun gender and animacy do not change.
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

(defsc WordMorphologicalInfo [_this {:word/keys [active morph lemma pos is_morphed norm]}]
  {:ident :word/id
   :initial-state {}
   :query [:word/id :word/active
           :word/morph :word/lemma :word/pos :word/is_morphed :word/norm]}
  (let [morph-map (spacy-grammar/non-redundant-morphological-features morph)]
    (fragment
     nil
     (div :.ui.red.ribbon.label (spacy-grammar/word-form-description morph-map pos))
     (h3 :.grammar_highlighting
         (when is_morphed
           (fragment
            (span :.inflected_word {:classes (morph-html-css-classes morph {:pos pos})} (ui-dict-links-and-popup lemma))
            "  "
            (ui-icon {:name i/arrow-right-icon})
            " "))
         (span :.inflected_word {:classes (morph-html-css-classes morph)} (ui-dict-links-and-popup norm)))

     (ui-all-a-words-morph-properties pos morph-map lemma norm is_morphed))))

(def ui-word-morphological-info (comp/factory WordMorphologicalInfo {:keyfn :word/id}))

(defsc WordWithMorphPopup [_this {:word/keys [word active score start morph pos]}
                           {:transcript/keys [display-type]}
                           {:>/keys [morphological-info]}]
  {:ident :word/id
   :initial-state (fn [_] {:>/morphological-info (comp/get-initial-state WordMorphologicalInfo {})})
   :query [:word/id :word/word :word/start :word/end :word/active :word/score
           :word/morph :word/lemma :word/pos :word/is_morphed :word/norm
           {:>/morphological-info (comp/get-query WordMorphologicalInfo)}]}
  
  (let [word-html (span {:classes (concat [(when active "active") "word"] (morph-html-css-classes morph))
                         :onClick (when-not (is-touch-device) (fn [ws] (player/on-word-click ws start)))
                         :style (when (= display-type :confidence) (c-to-c/confidence-to-style score))}
                        word)]
    (if (not-empty pos)
      (ui-popup
       {:hoverable true

        :popperModifiers #js [#js {"name" "preventOverflow"
                                   "options" #js {"padding" 8
                                                  "RootBoundary" "viewport"}}]
        :position "top left"
        :hideOnScroll true
        :on [(if (is-touch-device) "click" "hover")]

        :trigger word-html}

       (ui-popup-content
        {}
        (ui-word-morphological-info morphological-info)))
      word-html)))


(def ui-word-with-morph-popup (comp/computed-factory WordWithMorphPopup {:keyfn :word/id}))
         


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
   [com.submerged-structure.spacy-explain :as spacy-explain]))

;; from https://spacy.io/models/pl Morphologizer section
#_(def morph_tree_str  (spit "resources/spacy_morph_tree.txt"))
;; see also https://universaldependencies.org/pl/index.html for more information

(def map-of-attribute-names-to-human-readable-name
  {"AdpType" "adposition type"
   "VerbForm" "verb form"
   "PunctType" "punctuation type"
   "PronType" "pronoun type"
   "NumForm" "numeral form"
   "PrepCase" "prepositional case"
   "Reflex" "reflexive"
   "NumType" "numeral type"
   "Hyph" "hyphenated"
   "Poss" "possessive"
   "ConjType" "conjunction type"
   "VerbType" "verb type"
   "Number[psor]" "possessor's number"
   "PartType" "particle type"
   "Abbr" "abbreviation"
   "Pun" "punctuation"
   "PunctSide" "punctuation side"
   "Polite" "politeness"})

(def map-of-attribute-name-and-value-to-human-readable-value
  {["Abbr" "Yes"] "abbreviation"
   ["AdpType" "Post"] "postposition"
   ["AdpType" "Prep"] "preposition"
   ["Animacy" "Hum"] "(human)" ;;modifies Gender, we will put it in brackets
   ["Animacy" "Inan"] "(inanimate)"
   ["Animacy" "Nhum"] "(non-human)"
   ["Aspect" "Imp"] "imperfective"
   ["Aspect" "Imp,Perf"] "*imperf and perf identical*"
   ["Aspect" "Perf"] "perfective"
   ["Case" "Acc"] "accusative"
   ["Case" "Dat"] "dative"
   ["Case" "Gen"] "genitive"
   ["Case" "Ins"] "instrumental"
   ["Case" "Loc"] "locative"
   ["Case" "Nom"] "nominative"
   ["Case" "Voc"] "vocative"
   ["Clitic" "Yes"] "clitic"
   ["ConjType" "Comp"] "complementizer"
   ["ConjType" "Oper"] "operative"
   ["ConjType" "Pred"] "predicate"
   ["Degree" "Cmp"] "comparative"
   ["Degree" "Pos"] "positive"
   ["Degree" "Sup"] "superlative"
   ["Emphatic" "Yes"] "emphatic"
   ["Foreign" "Yes"] "foreign"
   ["Gender" "Fem"] "feminine"
   ["Gender" "Masc"] "masculine"
   ["Gender" "Neut"] "neutral"
   ["Hyph" "Yes"] "hyphenated"
   ["Mood" "Imp"] "imperative"
   ["Mood" "Ind"] "indicative"
   ["Number" "Plur"] "plural"
   ["Number" "Plur,Sing"] "*plural and singular identical*"
   ["Number" "Ptan"] "*always plural*"
   ["Number" "Sing"] "singular"
   ["Number[psor] " "Plur"] "plural possessor"
   ["Number[psor]" "Sing"] "singular possessor"
   ["NumForm" "Digit"] "digits"
   ["NumForm" "Roman"] "roman numerals"
   ["NumForm" "Word"] "word"
   ["NumType" "Card"] "cardinal numeral"
   ["NumType" "Ord"] "ordinal numeral"
   ["NumType" "Sets"] "refers to a set"
   ["PartType" "Int"] "interjectional"
   ["PartType" "Mod"] "modal"
   ["Person" "0"] "impersonal"
   ["Person" "1"] "1st person"
   ["Person" "1,2"] "1st or 2nd person"
   ["Person" "2"] "2nd person"
   ["Person" "3"] "3rd person"
   ["Polarity" "Neg"] "negative"
   ["Polarity" "Pos"] "positive"
   ["Polite" "Depr"] "derogatory"
   ["Poss" "Yes"] "possessive"
   ["PrepCase" "Npr"] "non-prepositional"
   ["PrepCase" "Pre"] "prepositional"
   ["PronType" "Dem"] "demonstrative"
   ["PronType" "Ind"] "indefinite"
   ["PronType" "Int"] "interrogative"
   ["PronType" "Neg"] "negative"
   ["PronType" "Prs"] "personal"
   ["PronType" "Rel"] "relative"
   ["PronType" "Tot"] "total"
   ["Pun" "No"] "no punctuation"
   ["Pun" "Yes"] "punctuation"
   ["Reflex" "Yes"] "reflexive"
   ["Tense" "Fut"] "future tense"
   ["Tense" "Past"] "past tense"
   ["Tense" "Pres"] "present tense"
   ["Variant" "Long"] "long"
   ["Variant" "Short"] "short"
   ["VerbForm" "Conv"] "converb"
   ["VerbForm" "Fin"] "finitive" ;; no need to display, verbs are assumed to be finitive if not marked otherwise
   ["VerbForm" "Inf"] "infinitive"
   ["VerbForm" "Part"] "participle"
   ["VerbForm" "Vnoun"] "verbal noun"
   ["VerbType" "Mod"] "modal verb"
   ["VerbType" "Quasi"] "quasi-verb (no declination)"
   ["Voice" "Act"] "active"
   ["Voice" "Pass"] "passive"})


(defn human-readable-attribute-name
  [attribute-name]
  (if-let [hr-name (get map-of-attribute-names-to-human-readable-name attribute-name)]
    hr-name
    attribute-name))

(defn human-readable-attribute-value
  "need attribute name as context"
  [morph-map attribute-name]
  (let [attribute-value (get morph-map attribute-name)]
    (if-let [hr-name (get map-of-attribute-name-and-value-to-human-readable-value [attribute-name attribute-value])]
       hr-name
       attribute-value)))

(defn human-readable-attribute-value-html-with-grammar-styling [morph-map attribute-name]
  (let [attribute-value (get morph-map attribute-name)]
    (span
     {:class (str attribute-name "_" attribute-value)}
     (human-readable-attribute-value morph-map attribute-name))))
     

(defn morphological-features-str-to-map [morph-string]
  (into {}
        (for [kv (clojure.string/split morph-string #"\|")
              :let [[k v] (clojure.string/split kv #"=")]
              :when (seq k)]
          [k v])))

(comment
  (morphological-features-str-to-map "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing")
  ;; => {"Animacy" "Inan", "Case" "Nom", "Gender" "Masc", "Number" "Sing"}
  )

(def redundant-attribute-values
  "Some attribute values are redundant, since they are the most common they are assumed."
  #{["VerbForm" "Fin"]
   ["Mood" "Ind"]
   ["Voice" "Act"]
    ["Degree" "Pos"]
    ["Polarity" "Neg"]
    ["Polarity" "Pos"]
    ["Number" "Sing"]})

(defn non-redundant-morphological-features [morph]
  (let [morph-map (morphological-features-str-to-map morph)]
    (into {} (remove redundant-attribute-values morph-map))))


(defn ui-dict-link-and-popup
  "More than one link for when word is two space separated lemmas."
  [word]
  (for [w (clojure.string/split word #" ")]
    (fragment
     (ui-popup
      {:trigger
       (a {:href (str "https://www.diki.pl/slownik-angielskiego?q=" w)
           :target "_blank"}
          w)}
      (ui-popup-content
       {}
       (str "Click to open dictionary entry for \"" w "\".")))
     " ")))


(comment
  (non-redundant-morphological-features "Animacy=Hum|Aspect=Imp,Perf|Clitic=Yes|Gender=Masc|Mood=Ind|Number=Sing|Person=1|Tense=Past|Variant=Long|VerbForm=Fin|Voice=Act"))


(def attribute-names-that-inflect-word
  "Only some of the attributes are relevant for inflection, some are properties of the word itself.
   In a sensible order to display. Hopefully good for all word classes.
   Will be followed by human readable part of speech."
  ["Tense" "Case" "Mood" "Number" "Person" "Gender" "Animacy" "Voice" "VerbForm"])

(comment attribute-names-that-inflect-word)

(defn human-readable-pos
  "For some parts of speech, an attribute is more human readable than the part of speech itself."
  [morph-map pos]
  (let [pos-explained' (spacy-explain/explain pos)]
    (case pos
      "ADP" (human-readable-attribute-value morph-map "AdpType")
      "X" (cond  (get morph-map "Foreign") (human-readable-attribute-value morph-map "Foreign")
                 (get morph-map "Abbr") (human-readable-attribute-value morph-map "Abbr")
                 :else "X")
      pos-explained')))

(comment (human-readable-pos {"Abbr" "Yes", "Pun" "Yes"} "X"))

(defn word-form-description [morph-map pos]
  (let [word-class-description
        (clojure.string/join " "
                             (keep (fn [attribute-name]
                                     (when (get morph-map attribute-name) (human-readable-attribute-value morph-map attribute-name)))
                                   attribute-names-that-inflect-word))]
    (str (when word-class-description
           (str (clojure.string/capitalize word-class-description) " "))
         (clojure.string/upper-case (human-readable-pos morph-map pos)))))

(def attributes-that-are-subtypes-of-other-attributes
  "Some attributes are subtypes of other attributes, so they are displayed in the same label as the parent category."
  {"Gender" "Animacy"})

(defn ui-morph-attribute [morph-map attribute-name & {:keys [detail-options]}]
  (when-not ((into #{} (vals attributes-that-are-subtypes-of-other-attributes))  attribute-name)
    (let [subtype (get attributes-that-are-subtypes-of-other-attributes attribute-name)]
      (div :.item
           (div :.header
                (assoc (if detail-options detail-options {}) :key attribute-name :pointing "right")
                (human-readable-attribute-name attribute-name)
                (when (and subtype
                           (get morph-map subtype))
                  (fragment
                   " ("
                   (human-readable-attribute-name subtype)
                   ")")))
           (div :.description (human-readable-attribute-value-html-with-grammar-styling morph-map attribute-name)
                     (when (and subtype
                                (get morph-map subtype))
                       (fragment " " (human-readable-attribute-value-html-with-grammar-styling morph-map subtype))))))))


(defn ui-all-a-words-morph-properties [morph-map lemma norm is-morphed]
  (let [word-attributes-that-inflect-word
        (filter (set (keys morph-map)) attribute-names-that-inflect-word)
        word-attributes-that-are-properties-of-the-word-itself
        (remove (set attribute-names-that-inflect-word) (keys morph-map))]
    
    (div :.grammar_highlighting {}
         (if (not-empty word-attributes-that-inflect-word)
           (fragment {}
            (if is-morphed 
              (h4 {} "The root word \"" (ui-dict-link-and-popup lemma) "\" becomes \"" (ui-dict-link-and-popup norm) "\" here, because it is:")
              (h4 {} "The root word \"" (ui-dict-link-and-popup lemma) "\" does not change here! It is:"))
            (div :.ui.list.animated.large {} (mapv (partial ui-morph-attribute morph-map) word-attributes-that-inflect-word)))
           (h4 {} "The root word \"" (ui-dict-link-and-popup lemma) "\" never changes! Yay!!"))
         
         (when (not-empty word-attributes-that-are-properties-of-the-word-itself)
           (fragment {}
            (ui-divider)
            (h4 {} "The root word itself, \"" (ui-dict-link-and-popup lemma) "\" has the following grammatical properties:")
            (div :.ui.list.animated.large {} (mapv (partial ui-morph-attribute morph-map) word-attributes-that-are-properties-of-the-word-itself)))))))

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
  (def morph-map (morphological-features-str-to-map (:word/morph word)))

  (let [
               
               lemma (:word/lemma word)
               norm (:word/norm word)
               is-morphed (:word/is_morphed word)
               word-attributes-that-inflect-word (keep (fn [attribute-name] (when (get (morphological-features-str-to-map (:word/morph word)) attribute-name)
                                                                              (ui-morph-attribute morph-map attribute-name))))
               word-attributes-that-are-properties-of-the-word-itself (keep (fn [attribute-name] (when (not (attribute-names-that-inflect-word attribute-name))
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

(defn morph-html-classes [morph]
  (mapv (fn [[attribute-name attribue-value]] (str attribute-name "_" attribue-value))
        (select-keys (non-redundant-morphological-features morph) html-classes-to-display)))


(defsc WordWithMorphPopup [_this {:word/keys [id word active score start
                                morph lemma pos is_morphed norm] :as whole-word}
                           {:transcript/keys [display-type]}]
  {:ident :word/id
   :initial-state {}
   :query [:word/id :word/word :word/start :word/end :word/active :word/score
           :word/morph :word/lemma :word/pos :word/is_morphed :word/norm]}
  
  (let [word-html (span {:classes (concat [(when active "active") "word"] (morph-html-classes morph))
                         :onClick (fn [ws] (player/on-word-click ws start))
                         :style (when (= display-type :confidence) (c-to-c/confidence-to-style score))
                         }
                        word)]
    (if (not-empty pos)
      (ui-popup
       {:hoverable true

        :popperModifiers #js [#js {"name" "preventOverflow"
                                   "options" #js {"padding" 8
                                                  "RootBoundary" "viewport"}}]
        :position "bottom right"
        :positionFixed true
        :hideOnScroll true
        :trigger word-html}


       (let [morph-map (non-redundant-morphological-features morph)]
         (ui-popup-content
          {}
          (div :.ui.red.ribbon.label (word-form-description morph-map pos))
          (h3 nil
              (when is_morphed
                (fragment {} (ui-dict-link-and-popup lemma) "  " (ui-icon {:name i/arrow-right-icon}) " "))
              (ui-dict-link-and-popup norm))

          (ui-all-a-words-morph-properties morph-map lemma norm is_morphed))))
      word-html)))

         
         
         #_"This word only has one form! No inflection! Phew!!"

(def ui-word-with-morph-popup (comp/computed-factory WordWithMorphPopup {:keyfn :word/id}))
         


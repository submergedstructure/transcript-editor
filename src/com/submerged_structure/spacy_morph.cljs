(ns com.submerged-structure.spacy-morph
  (:require
   [com.fulcrologic.fulcro.dom :as dom  :refer [div p]]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
   [com.fulcrologic.semantic-ui.modules.popup.ui-popup-content :refer [ui-popup-content]]
   [com.fulcrologic.semantic-ui.elements.label.ui-label :refer [ui-label]]
   [com.fulcrologic.semantic-ui.elements.label.ui-label-detail :refer [ui-label-detail]]

   [clojure.string :as str]))

;; from https://spacy.io/models/pl Morphologizer section
#_(def morph_tree_str  (spit "resources/spacy_morph_tree.txt"))
;; see also https://universaldependencies.org/pl/index.html for more information

(def map-of-attribute-names-to-human-readable-name
  {"AdpType" "Adposition Type"
   "VerbForm" "Verb Form"
   "PunctType" "Punctuation Type"
   "PronType" "Pronoun Type"
   "NumForm" "Numeral Form"
   "PrepCase" "Prepositional Case"
   "Reflex" "Reflexive"
   "NumType" "Numeral Type"
   "Hyph" "Hyphenated"
   "Poss" "Possessive"
   "ConjType" "Conjunction Type"
   "VerbType" "Verb Type"
   "Number[psor]" "Possessor's Number"
   "PartType" "Particle Type"
   "Abbr" "Abbreviation"
   "Pun" "Punctuation"
   "PunctSide" "Punctuation Side"
   "Polite" "Politeness"})

(def map-of-attribute-name-and-value-to-human-readable-value
  {["Abbr" "Yes"] "Abbreviation"
   ["AdpType" "Post"] "Postposition"
   ["AdpType" "Prep"] "Preposition"
   ["Animacy" "Hum"] "Human"
   ["Animacy" "Inan"] "Inanimate"
   ["Animacy" "Nhum"] "Non-human"
   ["Aspect" "Imp"] "Imperfective"
   ["Aspect" "Imp,Perf"] "Imperf and Perf identical "
   ["Aspect" "Perf"] "Perfective"
   ["Case" "Acc"] "Accusative"
   ["Case" "Dat"] "Dative"
   ["Case" "Gen"] "Genitive"
   ["Case" "Ins"] "Instrumental"
   ["Case" "Loc"] "Locative"
   ["Case" "Nom"] "Nominative"
   ["Case" "Voc"] "Vocative"
   ["Clitic" "Yes"] "Clitic"
   ["ConjType" "Comp"] "Complementizer"
   ["ConjType" "Oper"] "Operative"
   ["ConjType" "Pred"] "Predicate"
   ["Degree" "Cmp"] "Comparative"
   ["Degree" "Pos"] "Positive"
   ["Degree" "Sup"] "Superlative"
   ["Emphatic" "Yes"] "Emphatic"
   ["Foreign" "Yes"] "Foreign"
   ["Gender" "Fem"] "Feminine"
   ["Gender" "Masc"] "Masculine"
   ["Gender" "Neut"] "Neuter"
   ["Hyph" "Yes"] "Hyphenated"
   ["Mood" "Imp"] "Imperative"
   ["Mood" "Ind"] "Indicative"
   ["Number" "Plur"] "Plural"
   ["Number" "Plur,Sing"] "Plural and Singular Identical"
   ["Number" "Ptan"] "Always plural"
   ["Number" "Sing"] "Singular"
   ["Number[psor]" "Plur"] "Plural possessor"
   ["Number[psor]" "Sing"] "Singular possessor"
   ["NumForm" "Digit"] "Digits"
   ["NumForm" "Roman"] "Roman numerals"
   ["NumForm" "Word"] "Word"
   ["NumType" "Card"] "Cardinal numeral"
   ["NumType" "Ord"] "Ordinal numeral"
   ["NumType" "Sets"] "Refers to a set"
   ["PartType" "Int"] "Interjectional particle"
   ["PartType" "Mod"] "Modal particle"
   ["Person" "0"] "impersonal"
   ["Person" "1"] "1st person"
   ["Person" "1,2"] "1st or 2nd person"
   ["Person" "2"] "Second person"
   ["Person" "3"] "3rd person"
   ["Polarity" "Neg"] "Negative"
   ["Polarity" "Pos"] "Positive"
   ["Polite" "Depr"] "Derogatory"
   ["Poss" "Yes"] "Possessive"
   ["PrepCase" "Npr"] "Non-prepositional"
   ["PrepCase" "Pre"] "Prepositional"
   ["PronType" "Dem"] "Demonstrative"
   ["PronType" "Ind"] "Indefinite"
   ["PronType" "Int"] "Interrogative"
   ["PronType" "Neg"] "Negative"
   ["PronType" "Prs"] "Personal"
   ["PronType" "Rel"] "Relative"
   ["PronType" "Tot"] "Total"
   ["Pun" "No"] "No punctuation"
   ["Pun" "Yes"] "Punctuation"
   ["Reflex" "Yes"] "Reflexive"
   ["Tense" "Fut"] "Future"
   ["Tense" "Past"] "Past"
   ["Tense" "Pres"] "Present"
   ["Variant" "Long"] "Long"
   ["Variant" "Short"] "Short"
   ["VerbForm" "Conv"] "Converb"
   ["VerbForm" "Fin"] "Finite verb"
   ["VerbForm" "Inf"] "Infinitive"
   ["VerbForm" "Part"] "Participle"
   ["VerbForm" "Vnoun"] "Verbal noun"
   ["VerbType" "Mod"] "Modal verb"
   ["VerbType" "Quasi"] "Quasi-verb (no person, aspect, number)"
   ["Voice" "Act"] "Active"
   ["Voice" "Pass"] "Passive"})

(defn attribute-name-to-human-readable-name
  [attribute-name]
  (if-let [hr-name (get map-of-attribute-names-to-human-readable-name attribute-name)]
    hr-name
    attribute-name))

(defn attribute-name-and-value-to-human-readable-value [attribute-name attribute-value]
  (get map-of-attribute-name-and-value-to-human-readable-value [attribute-name attribute-value]))


(def priority-attribute-names
  {"blue" ["Case" "Gender" "Animacy"]
   "green" ["Tense" "Person" "Number" "Aspect"]
   nil []})

(defn is-attribute-name-priority [attribute-name color]
  (if color
    (some #{attribute-name} (get priority-attribute-names color))
    (not-any? #{attribute-name} (apply concat (vals (dissoc priority-attribute-names nil))))))

(comment (is-attribute-name-priority "Tense" "blue")
         ;; => nil
         (is-attribute-name-priority "Case" "blue")
         ;; => "Case"

)

(defn attribute-priority-colors []
  (keys priority-attribute-names))


(defn morphological-features-str-to-map [morph-string]
  (into {}
        (for [color (attribute-priority-colors)]
          [color
           (into {}
                 (for [kv (str/split morph-string #"\|")
                       :let [[k v] (str/split kv #"=")]
                       :when (is-attribute-name-priority k color)]
                   [k v]))])))

(comment

  (morphological-features-str-to-map "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing")
  ;; => {"blue" {"Animacy" "Inan", "Case" "Nom", "Gender" "Masc"}, "green" {"Number" "Sing"}, nil {}}

  )




(defn dict-link-instructions [is_morphed]
  (if is_morphed
    (p "Click on either the linked word above or it's root, to open dictonary for conjugated word or it's root entry.")
    (p "Click on the linked word above to open dictonary entry.")))

(defn label-list-from-morphological-features-map [morph]
  (map (fn [[color kvs]]
         (map (fn [[k v]]
                (ui-label
                 {:color color}
                 (attribute-name-to-human-readable-name k)
                 (ui-label-detail {} (attribute-name-and-value-to-human-readable-value k v))))
              kvs))
       (morphological-features-str-to-map morph)))

(comment (label-list-from-morphological-features-map "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing")
)

(defsc MorphologicalFeaturesPopupContent [_this {:word/keys [morph is_morphed]}]
  {:query [:word/id :word/morph :word/is_morphed]
   :ident :word/id}
  (if (not-empty morph)
    (ui-popup-content
     {}

     (div
      (dict-link-instructions is_morphed)
      (label-list-from-morphological-features-map morph)))
    (ui-popup-content
     {}
     (dict-link-instructions is_morphed))))

(def ui-morphological-features-popup-content
  (comp/factory MorphologicalFeaturesPopupContent))
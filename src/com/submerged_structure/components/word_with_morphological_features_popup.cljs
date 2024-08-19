(ns com.submerged-structure.components.word-with-morphological-features-popup
  (:require
   [com.fulcrologic.fulcro.dom :as dom  :refer [div p span a]]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
   [com.fulcrologic.semantic-ui.modules.popup.ui-popup-content :refer [ui-popup-content]]
   [com.fulcrologic.semantic-ui.elements.label.ui-label :refer [ui-label]]
   [com.fulcrologic.semantic-ui.elements.label.ui-label-detail :refer [ui-label-detail]]



   [com.submerged-structure.confidence-to-color :as c-to-c]
   [com.fulcrologic.semantic-ui.modules.popup.ui-popup :refer [ui-popup]]
   [com.fulcrologic.semantic-ui.modules.popup.ui-popup-header :refer [ui-popup-header]]
   [com.submerged-structure.components.player :as player]
   [com.fulcrologic.semantic-ui.icons :as i]
   [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
   
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



(defn dict-link-instructions [is_morphed]
  (if is_morphed
    (p "Click on either the linked word above or it's root, to open dictonary for conjugated word or it's root entry.")
    (p "Click on the linked word above to open dictonary entry.")))

(defn attribute-name-to-human-readable-name
  [attribute-name]
  (if-let [hr-name (get map-of-attribute-names-to-human-readable-name attribute-name)]
    hr-name
    attribute-name))

(defn human-readable-attribute-value
  "need attribute name as context"
  [attribute-name attribute-value]
  (if-let [hr-name (get map-of-attribute-name-and-value-to-human-readable-value [attribute-name attribute-value])]
    hr-name
    attribute-value))


(defn morphological-features-str-to-map [morph-string]
  (into {}
        (for [kv (str/split morph-string #"\|")
              :let [[k v] (str/split kv #"=")]]
          [k v])))

(comment
  (morphological-features-str-to-map "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing")
  ;; => {"Animacy" "Inan", "Case" "Nom", "Gender" "Masc", "Number" "Sing"}
)

(defn morph-label [[attribute-name attribute-value] & [options]]
  (ui-label
   (if options options {})
   (attribute-name-to-human-readable-name attribute-name)
   (ui-label-detail {} (human-readable-attribute-value attribute-name attribute-value))))


(defn label-list-from-morphological-features-map-noun [{:strs [Animacy Case Gender Number NumType Aspect VerbForm Polarity Polite]}]
  (div
   (when Polite (morph-label ["Polite" Polite] {:color "red"}))
   (morph-label ["Case" Case])
   (ui-label {}
             "Gender"
             (ui-label-detail {} (str (human-readable-attribute-value "Gender" Gender)
                                      (when Animacy (str " (" (human-readable-attribute-value "Animacy" Animacy) ")" )))))
   (ui-label {}
             "Number"
             (ui-label-detail
              {} Number
              (when NumType (str " (" (human-readable-attribute-value "NumType" NumType) ")"))))
   (when VerbForm
     (ui-label {}
               "VerbForm"
               (ui-label-detail
                {}
                (str (str (human-readable-attribute-value "VerbForm" VerbForm)
                          " ("
                          (human-readable-attribute-value "Aspect" Aspect)
                          " - "
                          (human-readable-attribute-value "Polarity" Polarity)
                          ")")))))))


(defn html-from-morphological-features-map [pos {:as morph-map}]
  (case pos
    "NOUN" (label-list-from-morphological-features-map-noun morph-map)
    "PROPN" (label-list-from-morphological-features-map-noun morph-map)
   (map morph-label morph-map)))

(comment (html-from-morphological-features-map "NOUN" "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing")
)



(defn dict-link
  "More than one link for when word is two space separated lemmas."
  [word]
  (for [ws (str/split word #" ")]
    (span (a {:href (str "https://www.diki.pl/slownik-angielskiego?q=" ws)
              :target "_blank"}
             ws) " ")))

(defsc WordWithMorphPopup [_this {:word/keys [word active score start
                                morph lemma pos pos_explained is_morphed norm]}]
  {:ident :word/id
   :initial-state {}
   :query [:word/id :word/word :word/start :word/end :word/active :word/score
           :word/morph :word/lemma :word/pos :word/pos_explained :word/is_morphed :word/norm]}
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
  

   (ui-popup-content
    {}
    (div :.ui.raised.segment
         (div :.ui.red.ribbon.label pos_explained)
         (span nil
               (when is_morphed
                 (span nil (dict-link lemma) "  " (ui-icon {:name i/arrow-right-icon}) " "))
               (dict-link norm))
         (p "")
         (div :.ui.blue.ribbon.label "Community")
         "User Reviews"
         (p "")
     (if (not-empty morph)
      (div
       (dict-link-instructions is_morphed)
       (html-from-morphological-features-map pos (morphological-features-str-to-map morph)))
      (dict-link-instructions is_morphed))))))

(def ui-word-with-morph-popup (comp/factory WordWithMorphPopup {:keyfn :word/id}))

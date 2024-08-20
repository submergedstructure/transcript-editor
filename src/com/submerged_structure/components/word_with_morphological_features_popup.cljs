(ns com.submerged-structure.components.word-with-morphological-features-popup
  (:require
   [com.fulcrologic.fulcro.dom :as dom  :refer [h3 div p span a iframe]]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc fragment]]
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
   ["Animacy" "Hum"] "human"
   ["Animacy" "Inan"] "inanimate"
   ["Animacy" "Nhum"] "non-human"
   ["Aspect" "Imp"] "imperfective"
   ["Aspect" "Imp,Perf"] "imperf and perf identical"
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
   ["Gender" "Neut"] "neuter"
   ["Hyph" "Yes"] "hyphenated"
   ["Mood" "Imp"] "imperative"
   ["Mood" "Ind"] "indicative"
   ["Number" "Plur"] "plural"
   ["Number" "Plur,Sing"] "plural and singular identical"
   ["Number" "Ptan"] "always plural"
   ["Number" "Sing"] "singular"
   ["Number[psor] " "Plur"] "plural possessor"
   ["Number[psor]" "Sing"] "singular possessor"
   ["NumForm" "Digit"] "digits"
   ["NumForm" "Roman"] "roman numerals"
   ["NumForm" "Word"] "word"
   ["NumType" "Card"] "cardinal numeral"
   ["NumType" "Ord"] "ordinal numeral"
   ["NumType" "Sets"] "refers to a set"
   ["PartType" "Int"] "interjectional particle"
   ["PartType" "Mod"] "modal particle"
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
   ["Tense" "Fut"] "future"
   ["Tense" "Past"] "past"
   ["Tense" "Pres"] "present"
   ["Variant" "Long"] "long"
   ["Variant" "Short"] "short"
   ["VerbForm" "Conv"] "converb"
   ["VerbForm" "Fin"] "finite verb"
   ["VerbForm" "Inf"] "infinitive"
   ["VerbForm" "Part"] "participle"
   ["VerbForm" "Vnoun"] "verbal noun"
   ["VerbType" "Mod"] "modal verb"
   ["VerbType" "Quasi"] "quasi-verb (no person, aspect, number)"
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


(defn morphological-features-str-to-map [morph-string]
  (into {}
        (for [kv (str/split morph-string #"\|")
              :let [[k v] (str/split kv #"=")]]
          [k v])))

(comment
  (morphological-features-str-to-map "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing")
  ;; => {"Animacy" "Inan", "Case" "Nom", "Gender" "Masc", "Number" "Sing"}
)

(defn morph-label [morph-map attribute-name & {:keys [detail-options postfix prefix]}]
  (ui-label
   (if detail-options detail-options {})
   (human-readable-attribute-name attribute-name)
   (ui-label-detail {} (human-readable-attribute-value morph-map attribute-name))))


(defn label-list-from-morphological-features-map-noun [{:strs [Animacy Case Gender Number NumType Aspect VerbForm Polarity Polite] :as morph-map}]
  (div
   (when Polite (morph-label morph-map "Polite" {:detail-options {:color "red"}}))
   (morph-label morph-map "Case")
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


(defn ui-morphological-features [pos {:as morph-map}]
  (map (fn [[attribute-name _]] (morph-label morph-map attribute-name)) morph-map))

(comment (ui-morphological-features "NOUN" "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing")
)

(defn human-readable-parts-with-spacing [morph-map attribute-names]
  (str/join " "
            (for [attr attribute-names
                  :let [value (get morph-map attr)]
                  :when value]
              (human-readable-attribute-value morph-map attr))))

(defn case-gender-number [{:strs [Animacy Case Gender] :as morph-map}]
  (str/capitalize
   (str (when Case (str (human-readable-attribute-value morph-map "Case") " "))
        
        (when Gender (human-readable-attribute-value morph-map "Gender"))

        (when Animacy (str " (" (human-readable-attribute-value morph-map "Animacy") ")"))
        " "
        (when Case (human-readable-attribute-value morph-map "Number")))))

(defn word-class [pos pos_explained morph-map]
  (case pos
    ("NOUN" "PROPN" "ADJ" "PRON") (str (case-gender-number morph-map) " " pos_explained)
    "ADP" (str (human-readable-attribute-value morph-map "AdpType"))
    (str pos_explained)))

(defn ui-dict-link-and-popup
  "More than one link for when word is two space separated lemmas."
  [word]
  (for [w (str/split word #" ")]
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

(defsc WordWithMorphPopup [_this {:word/keys [word active score start
                                morph lemma pos pos_explained is_morphed norm]}]
  {:ident :word/id
   :initial-state {}
   :query [:word/id :word/word :word/start :word/end :word/active :word/score
           :word/morph :word/lemma :word/pos :word/pos_explained :word/is_morphed :word/norm]}
  (ui-popup
   {:hoverable true

    :popperModifiers #js [#js {"name" "preventOverflow"
                               "options" #js {"padding" 8
                                              "RootBoundary" "viewport"}}]
    :position "bottom right"
    :hideOnScroll true
    :trigger (span {:data-c score
                    :classes [(when active "active") "word"]
                    :onClick (fn [ws] (player/on-word-click ws start))
                    :style (c-to-c/confidence-to-style score)}
                   word)}
  

   (ui-popup-content
    {}
    (div :.ui.red.ribbon.label (word-class pos pos_explained (morphological-features-str-to-map morph)))
    (h3 nil
        (when is_morphed
          (fragment {} (ui-dict-link-and-popup lemma) "  " (ui-icon {:name i/arrow-right-icon}) " "))
        (ui-dict-link-and-popup norm))

    (div
     (if (not-empty morph)
       (ui-morphological-features pos (morphological-features-str-to-map morph))
       "This word only has one form! No inflection! Phew!!")))))

(def ui-word-with-morph-popup (comp/factory WordWithMorphPopup {:keyfn :word/id}))

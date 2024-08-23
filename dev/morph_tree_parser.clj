(ns morph-tree-parser
  (:require [clojure.string :as str]))

;; Examinining possible attributes and values that can be returned by spacy for Polish language.

;; from https://spacy.io/models/pl Morphologizer section
;; see also https://universaldependencies.org/pl/index.html for more information

(def morph-tree (slurp "resources/morpholiger_leaf_tree.txt"))

(defn morph-tree-parser
  "Parse the morpholiger leaf tree. Returns a vector of maps, where each map
   represents a leaf in the tree. Each leaf is a map of attributes and values."
  []
  (->> (str/split morph-tree #"\, ")
       (map (fn [leaf] (str/split leaf #"\|")))
       (map (fn [attrs-and-values]
              (map (fn [attr-and-value]
                     (let [[k v](str/split attr-and-value #"=")]
                       [k v]))
                   attrs-and-values)))
       (mapv #(into {} %))))

(defn morph-tree-attributes-and-values
  "Get the attributes and values from the morpholiger leaf tree"
  []
  (distinct
   (apply concat
          (for [leaf (morph-tree-parser)
                :when (not (#{"PUNCT"} (get leaf "POS")))]
            (for [[attr value] leaf
                  :when (not= attr "POS")]
              [attr value])))))

(defn morph-tree-attributes-and-values-per-pos []
  (group-by #(get % "POS") (morph-tree-parser)))

(defn morph-tree-attributes-and-values-per-pos-groups-with-same-attributes
  "For each part of speech a collection of maps of unique combinations of attributes and the possible values these attributes can take."
  []
  (apply merge-with into
        (apply
         concat
         (for [[pos leaves] (morph-tree-attributes-and-values-per-pos)]
           (->> leaves
                (group-by #(sort (keys %)))
                (map (fn [[_ks samekeys]] (map (fn [leaf] (into {} (map (fn [[k v]] [k #{v}])) leaf)) samekeys)))
                (map (fn [samekeys] {(keyword pos) [(apply merge-with into samekeys)]})))))))

(comment 
  (morph-tree-parser)
  (println (apply str (for [[attr value] (morph-tree-attributes-and-values)]
    (str attr " = " value "\n"))))
  (count *1)

  
  (:NOUN (morph-tree-attributes-and-values-per-pos-groups-with-same-attributes))
  (count *1)
  (keys *2)

  (morph-tree-attributes-and-values-per-pos-groups-with-same-attributes)

  (for [[pos arr-of-names->poss-values-map] (morph-tree-attributes-and-values-per-pos-groups-with-same-attributes)]
    (for [names->poss-values-map arr-of-names->poss-values-map]
      (let  [type-key (filter #(re-matches #".*Type" %) (keys names->poss-values-map))]
        (if (not-empty type-key)
          (prn pos type-key)
          (prn pos "no type key")))))
  )




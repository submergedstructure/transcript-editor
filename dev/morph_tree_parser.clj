(ns morph-tree-parser
  (:require [clojure.string :as str]))

(def morph-tree (slurp "resources/morpholiger_leaf_tree.txt"))

(defn morph-tree-parser
  "Parse the morpholiger leaf tree"
  []
  (->> (str/split morph-tree #"\, ")
       (map (fn [leaf] (str/split leaf #"\|")))
       (map (fn [attrs-and-values]
              (map (fn [attr-and-value] (str/split attr-and-value #"=")) attrs-and-values)))
       (mapv #(into {} %))))

(defn morph-tree-attributes-and-values
  "Get the attributes and values from the morpholiger leaf tree"
  []
  (distinct
   (apply concat
          (for [leaf (morph-tree-parser)
                :when (not (#{"PUNCT" "SYM"} (get leaf "POS")))]
            (for [[attr value] leaf
                  :when (not= attr "POS")]
              [attr value])))))


(comment 
  (morph-tree-parser)
  (println (apply str (for [[attr value] (morph-tree-attributes-and-values)]
    (str attr " = " value "\n"))))
  (count *1)
  )


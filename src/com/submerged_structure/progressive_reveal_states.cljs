(ns com.submerged-structure.progressive-reveal-states)


(def text-reveal-progression ["blurred" "un-blurred" "grammar-highlighted" "grammar-highlighted"])
(def translation-reveal-progression [false false false true])

(def all-possible-reveal-progressions
  "This is all the possible states that the text can be in. Which states are included in the progression is determined by the settings."
  (mapv (fn [text-state translation-state]
                                {:text text-state
                                 :translations-visible? translation-state})
                              text-reveal-progression translation-reveal-progression))

(def description-of-all-possible-reveal-progressions
  "Used in UI for setttings."
  ["Blurred" "Un-blurred" "Grammar Highlighted" "Grammar Highlighted with Translation"])

(def reveal-progressions-initially-active [false true true true])

(def initial-state-of-local-db (map-indexed (fn [idx active]
                                              {:progressive-reveal-setting/id idx
                                               :progressive-reveal-setting/active active})
                                            reveal-progressions-initially-active))

(defn reveal-progressions-currently-active [current]
  (keep (fn [{:progressive-reveal-setting/keys [id active]}] (when active (get all-possible-reveal-progressions id))) current))

(comment (reveal-progressions-currently-active initial-state-of-local-db))
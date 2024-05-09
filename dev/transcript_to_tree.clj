(ns transcript-to-tree
  (:require [cheshire.core :as cheshire]
            [clojure.pprint :as pprint]
            [clojure.string :as str]
            ;[fulcrologic.client.primitives :as fp]
            [clojure.walk :refer [postwalk]]
            [clojure.core :as clj]
            [com.fulcrologic.fulcro.mutations :as m]
            [com.fulcrologic.fulcro.algorithms.normalize :as fn]
            [com.fulcrologic.fulcro.components :as comp]
            [com.fulcrologic.fulcro.routing.dynamic-routing :as dr]))

;; Code to be run in the clj repl to convert the transcript data returned by my 
;; web service to edn.

;; Run's in the same JVM as the shadow-cljs repl.

;; Later this will be done on the server side and immediately put in the db.

(def filepath "resources/public/audio_and_transcript/")

(def filename "realpolish-hint1")

(def full-filepath-without-extension (str filepath filename))



(defn add-id [m]
  (assoc m "id" (str (java.util.UUID/randomUUID))))
  
(defn add-ids [tree]
  (postwalk (fn [node]
              (if (map? node)
                (add-id node)
                node))
            tree))

(declare add-ns-and-keywordize-keys-in-vec)

(defn add-ns-and-keywordize-keys-in-m [m ns]
  (into {} (for [[k v] m]
             [(keyword ns k) 
              (if (vector? v)
                (add-ns-and-keywordize-keys-in-vec v (subs k 0 (dec (count k))))
                v)])))

(comment 
  (add-ns-and-keywordize-keys-in-m {"a" "xa" "words" [{"a" 5}] "b" 2} "segment")
  ;; => #:segment{:a "xa", :words [#:word{:a 5}], :b 2}
  (add-ns-and-keywordize-keys-in-m {"a" 1 "b" 2} "word")
  ;; => #:word{:a 1, :b 2}

  )


(defn add-ns-and-keywordize-keys-in-vec [v ns]
  (into [] (for [m v]
             (add-ns-and-keywordize-keys-in-m m ns))))

(comment
  (add-ns-and-keywordize-keys-in-vec [{"a" 1 "b" 2} {"c" 3 "d" 4}] "segment")
  ;; => [#:segment{:a 1, :b 2} #:segment{:c 3, :d 4}]
)

(defn transcript-data []
  (let [segments (-> (str full-filepath-without-extension ".json")
                     slurp
                     (cheshire/parse-string false)
                     (get "output")
                     (get "segments"))]
  (-> {"audio-filename" filename
       "label" "\"Real Polish\" podcast - Hint 1"
       "segments" segments}
      add-ids
      (add-ns-and-keywordize-keys-in-m "transcript"))))

(defn write-mock-data-cljs-file []
  (-> (transcript-data)
      pprint/pprint
      with-out-str
      (str/replace #"^" "  ")
      (#(str "(ns com.submerged-structure.mock-data)\n\n(def transcript\n" % ")\n"))
      (#(spit "src/com/submerged_structure/mock_data.cljc" %))))
(comment 
  (write-mock-data-cljs-file)
  (transcript-data)
  )


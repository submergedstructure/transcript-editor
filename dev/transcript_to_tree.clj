(ns transcript-to-tree
  (:require [cheshire.core :as cheshire]
            [clojure.pprint :as pprint]
            [clojure.string :as str]
            ;[fulcrologic.client.primitives :as fp]
            [clojure.walk :refer [postwalk]]
            [clojure.core :as clj]
            [clojure.set :as set]
            [com.fulcrologic.fulcro.mutations :as m]
            [com.fulcrologic.fulcro.algorithms.normalize :as fn]
            [com.fulcrologic.fulcro.components :as comp]
            [com.fulcrologic.fulcro.routing.dynamic-routing :as dr]))

;; Code to be run in the clj repl to convert the transcript data returned by my 
;; web service to edn.

;; Run's in the same JVM as the shadow-cljs repl.

;; Later this will be done on the server side and immediately put in the db.

(def filepath "resources/public/audio_and_transcript/whispers2t/")



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

(defn transcript-data [label full-filepath-without-extension]
  (let [segments (-> (str full-filepath-without-extension ".json")
                     slurp
                     (cheshire/parse-string false)
                     (get "output")
                     (get 0))]
  (-> {"audio-filename" (str (subs full-filepath-without-extension (count "resources/public")) ".mp3")
       "label" label
       "segments" (mapv
                   (fn [segment]
                     (let [segment' (set/rename-keys segment {"word_timestamps" "words" "start_time" "start" "end_time" "end"})]
                       (update segment' "words" (fn [words] (mapv (fn [word] (set/rename-keys word {"prob" "score"})) words)))))
                   segments)}
      
      add-ids
      (add-ns-and-keywordize-keys-in-m "transcript"))))


(defn find-mp3-files-at-path [path]
  (->> (java.io.File. path)
       (.listFiles)
       (filter #(.isFile %))
       (filter #(re-matches #".*\.mp3" (.getName %)))
       (map #(.getName %))
       (map #(vector 
              %
              (str path (subs % 0 (- (count %) 4)))))))



(defn write-mock-data-cljs-file []
  (-> (for [[filename full-filepath-without-extension] (find-mp3-files-at-path filepath)
            :let [t-d (transcript-data filename full-filepath-without-extension)]]
        (vector (:transcript/id t-d) t-d))
      (#(into {} %))
      pprint/pprint
      with-out-str
      (str/replace #"^" "  ")
      (#(str "(ns com.submerged-structure.mock-data)\n\n(def transcripts\n" % ")\n"))
      (#(spit "src/com/submerged_structure/mock_data.cljc" %))))


(comment (find-mp3-files-at-path filepath))


(comment 
  (write-mock-data-cljs-file)
  
  )


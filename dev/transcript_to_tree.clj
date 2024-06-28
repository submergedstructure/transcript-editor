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
  (add-ns-and-keywordize-keys-in-m {"a" 1 "b" 2} "word"))
  ;; => #:word{:a 1, :b 2}

  


(defn add-ns-and-keywordize-keys-in-vec [v ns]
  (into [] (for [m v]
             (add-ns-and-keywordize-keys-in-m m ns))))

(comment
  (add-ns-and-keywordize-keys-in-vec [{"a" 1 "b" 2} {"c" 3 "d" 4}] "segment"))
  ;; => [#:segment{:a 1, :b 2} #:segment{:c 3, :d 4}]



(defn add-prev-and-next [ns objs & [prev next]]
  (loop [[obj & rest-objs] objs
         last-obj-id prev
         objs-with-prev []]
    (if (nil? obj)
      objs-with-prev
      (recur rest-objs
             ((keyword ns "id") obj)
             (conj objs-with-prev
                   (assoc
                    obj
                    (keyword ns "prev") last-obj-id
                    (keyword ns "next") (if (empty? rest-objs)
                                          next
                                          ((keyword ns "id") (first rest-objs)))))))))

(defn get-fn-word-id-in-segment [fn segment]
  (-> segment
      :segment/words
      fn
      :word/id))

(defn add-prev-and-next-to-transcript-segments-and-words-in-segments [segments]
  (->> segments
      (add-prev-and-next "segment")
      (map-indexed
       (fn [idx segment]
        (let [prev (if (zero? idx) nil (get-fn-word-id-in-segment last (nth segments (dec idx))))
              next (if (= (dec (count segments)) idx) nil (get-fn-word-id-in-segment first (nth segments (inc idx))))]
          (update
           segment
           :segment/words
           (fn [words] (add-prev-and-next "word" words prev next))))))
      (into [])))

(defn transcript-data [label full-filepath-without-extension]
  (let [segments (-> (str full-filepath-without-extension ".json")
                     slurp
                     (cheshire/parse-string false)
                     (get "output")
                     (get "segments")
                     ;Assuming segments and words are already sorted and remembering that some words have nil start time
                     ;as no time stamp has been found though they are in the correct order.
                     ;will not sort but this may need reviewing.
                     #_(#(sort-by :start %))
                     #_(#(mapv (fn [segment] (update segment :words (partial sort-by :start))) %)))]
   (-> {"audio-filename" (str (subs full-filepath-without-extension (count "resources/public")) ".mp3")
        "label" label
        "segments" segments}
       add-ids
       (add-ns-and-keywordize-keys-in-m "transcript")
       (update :transcript/segments add-prev-and-next-to-transcript-segments-and-words-in-segments)
       (update :transcript/segments (fn [segments]
                                      (mapv
                                       (fn [segment]
                                         (update segment :segment/words
                                                 (fn [words]
                                                   (mapv
                                                    (fn [word] (assoc word :word/segment (:segment/id segment)))
                                                    words))))
                                       segments))))))


(comment
  (def t-d (apply transcript-data ["realpolish-hint1.mp3" "resources/public/audio_and_transcript/realpolish-hint1"]))
  (add-prev-and-next (:transcript/segments t-d) "segment"))
  
  

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
  (write-mock-data-cljs-file))
  
  


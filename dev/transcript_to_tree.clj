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

(defn singular-from-plural
  "All plural keys are simply the singular key with an 's' appended."
  [s]
  (subs s 0 (dec (count s))))

(defn add-ns-and-keywordize-keys-in-m [m ns]
  (into {} (for [[k v] m]
             [(keyword ns k) 
              (if (vector? v)
                (add-ns-and-keywordize-keys-in-vec v (singular-from-plural k))
                v)])))

(defn namespace-of-map
  "For a map with common namespace for all keys."
  [m]
  (namespace (first (keys m))))

(defn replace-all-vectors-in-maps-with-ids-of-submaps [m]
  (apply merge m
         (for [[k v] m
               :when (vector? v)]
           {k (into [] (keep #(when-not (empty? v)
                                (let [submap-ns (namespace-of-map (first v))
                                      submap-id (keyword submap-ns "id")]
                                  (into {} [[submap-id (submap-id %)]]))) v))})))

(defn flatten-tree-of-maps-into-maps-referenced-by-id
  "Return map to lookup map by keywordized key with identity [keyword-for-id id] of chlidren as values."
  [ms]
  (loop [to-process ms ; add sub-maps to process 
         result {}]
    (if (empty? to-process)
      result
      (let [[m & remaining] to-process]
        (recur (apply concat remaining
                      (for [[_ v] m
                            :when (vector? v)]
                        v))
               (let [key-id-of-m (keyword (namespace-of-map m) "id")
                     identity-of-m (vector key-id-of-m (key-id-of-m m))]
                 (apply merge result
                        {identity-of-m (replace-all-vectors-in-maps-with-ids-of-submaps m)})))))))

(comment
(def ms
  [#:transcript{:audio-filename
                "/audio_and_transcript/realpolish-hint1.mp3",
                :label "realpolish-hint1.mp3",
                :segments
                [#:segment{:text "Cześć, co słychać?",
                           :words
                           [#:word{:word "Cześć,",
                                   :start 0.2,
                                   :end 1.18,
                                   :score 0.73,
                                   :id
                                   "5988ab6a-555d-4dd5-9c06-e40c7a7c298f"}
                            #:word{:word "co",
                                   :start 1.18,
                                   :end 1.18,
                                   :score 0.97,
                                   :id
                                   "11c72263-2626-4360-9324-f4a3af38f07f"}
                            #:word{:word "słychać?",
                                   :start 1.18,
                                   :end 2.2600000000000002,
                                   :score 0.68,
                                   :id
                                   "d149e6a5-874e-420c-b998-c6e483f1e92c"}],
                           :start 0.2,
                           :end 2.2600000000000002,
                           :id "c38ba87e-45e6-4e4b-bd98-5c5048488c7b"}
                 #:segment{:text
                           "Mam nadzieję, że nauka polskiego idzie dobrze.",
                           :words
                           [#:word{:word "Mam",
                                   :start 2.54,
                                   :end 2.54,
                                   :score 0.98,
                                   :id
                                   "bf842b44-69e3-4960-8a38-3b6424418dbd"}
                            #:word{:word "nadzieję,",
                                   :start 2.54,
                                   :end 3.12,
                                   :score 0.96,
                                   :id
                                   "c68e3756-d724-46fd-8178-af9cf25cb5be"}
                            #:word{:word "że",
                                   :start 3.12,
                                   :end 3.52,
                                   :score 1.0,
                                   :id
                                   "27bb00e2-63b9-448e-b970-10b53f030dd2"}
                            #:word{:word "nauka",
                                   :start 3.52,
                                   :end 3.88,
                                   :score 0.73,
                                   :id
                                   "d9e9c3ee-6e1b-489b-906c-86ad6fe8b7c7"}
                            #:word{:word "polskiego",
                                   :start 3.88,
                                   :end 4.5200000000000005,
                                   :score 0.7,
                                   :id
                                   "f23f7e2a-b4ad-43a4-a904-76e484bfb21f"}
                            #:word{:word "idzie",
                                   :start 4.5200000000000005,
                                   :end 4.840000000000001,
                                   :score 0.84,
                                   :id
                                   "7a4d488b-2a36-4078-b65e-61f47a7e44be"}
                            #:word{:word "dobrze.",
                                   :start 4.840000000000001,
                                   :end 5.9,
                                   :score 0.74,
                                   :id
                                   "119d9e44-661c-4163-b641-1cc433919d9c"}],
                           :start 2.54,
                           :end 5.9,
                           :id "b469cb0f-ccb5-443c-b491-74db94321f58"}
                 #:segment{:text
                           "Właśnie napisałem krótki artykuł o szybkiej metodzie nauki.",
                           :words
                           [#:word{:word "Właśnie",
                                   :start 6.459999999999999,
                                   :end 7.08,
                                   :score 0.78,
                                   :id
                                   "2f3ddbc2-c178-49a2-9eed-fe0a3f3dcffc"}
                            #:word{:word "napisałem",
                                   :start 7.08,
                                   :end 7.9,
                                   :score 0.9,
                                   :id
                                   "4f954008-3c74-4b75-979a-2090fdadad0f"}
                            #:word{:word "krótki",
                                   :start 7.9,
                                   :end 8.54,
                                   :score 0.97,
                                   :id
                                   "6356b212-76a0-4b7a-b754-aefd4f1c2953"}
                            #:word{:word "artykuł",
                                   :start 8.54,
                                   :end 9.76,
                                   :score 0.87,
                                   :id
                                   "72be5444-b794-403b-866e-8f5ff2c2c23b"}
                            #:word{:word "o",
                                   :start 9.76,
                                   :end 9.86,
                                   :score 0.0,
                                   :id
                                   "04eec711-f5e8-427a-9fcd-60c21c8ae0df"}
                            #:word{:word "szybkiej",
                                   :start 9.86,
                                   :end 10.219999999999999,
                                   :score 0.56,
                                   :id
                                   "4b0db33b-703e-46c2-9286-7df8644ef04a"}
                            #:word{:word "metodzie",
                                   :start 10.219999999999999,
                                   :end 10.68,
                                   :score 0.92,
                                   :id
                                   "fdd012be-691f-4314-95f2-073970cdc8f7"}
                            #:word{:word "nauki.",
                                   :start 10.68,
                                   :end 12.1,
                                   :score 0.92,
                                   :id
                                   "f0bd6129-a491-475b-bf8c-7a175bf6e06d"}],
                           :start 6.459999999999999,
                           :end 12.1,
                           :id "22e609b6-6b82-4696-811f-7e861145a2db"}]}
   #:transcript{:audio-filename
                "/audio_and_transcript/090C-DailyPolishStory-QA.mp3",
                :label "090C-DailyPolishStory-QA.mp3",
                :segments
                [#:segment{:text
                           "Świetnie, znasz już dobrze historyjkę, pora więc zaczynać pytania i odpowiedzi.",
                           :words
                           [#:word{:word "Świetnie,",
                                   :start 1.02,
                                   :end 2.2800000000000002,
                                   :score 0.89,
                                   :id
                                   "7afa9219-51d4-46c1-9553-cb8638a30d59"}
                            #:word{:word "znasz",
                                   :start 2.2800000000000002,
                                   :end 2.52,
                                   :score 0.22,
                                   :id
                                   "20ff93b3-0cd7-418c-8eb5-c534119246e0"}
                            #:word{:word "już",
                                   :start 2.52,
                                   :end 2.74,
                                   :score 1.0,
                                   :id
                                   "c515c1ae-64a6-47e0-94d8-9e4d033ae6af"}],
                           :start 1.02,
                           :end 7.4,
                           :id "868dc94e-9ef4-490d-9f0b-92cbd302a851"}
                 #:segment{:text
                           "Tak jak zwykle, proszę odpowiadaj na pytania.",
                           :words
                           [#:word{:word "Tak",
                                   :start 7.4,
                                   :end 7.58,
                                   :score 0.95,
                                   :id
                                   "3bf1c2f4-aa10-4a39-a367-2877560ea23d"}
                            #:word{:word "jak",
                                   :start 7.58,
                                   :end 7.76,
                                   :score 0.97,
                                   :id
                                   "5b54bcae-b17a-4564-b09a-2b6b008adea1"}],
                           :start 7.4,
                           :end 11.12,
                           :id "f669642f-e67e-4c75-9cd5-05efaf338be3"}]}])
(flatten-tree-of-maps-into-maps-referenced-by-id ms)
)

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



(defn transcripts_all_files []
  (-> (str filepath "transcriptions_and_translations.json")
      slurp
      (cheshire/parse-string false)))


(defn file-translated-segments-to-tree [translations]
  (into
   []
   (for [[lang segments] translations
         segment segments]
     (->
      segment
      (select-keys ["start_time" "end_time" "text"])
      (assoc "lang" lang)
      (set/rename-keys {"start_time" "start" "end_time" "end"})))))

(defn file-transcribed-segments-to-tree [transcription]
  (mapv
   (fn [segment]
     (->
      (set/rename-keys segment {"word_timestamps" "words" "start_time" "start" "end_time" "end"})
      (update "words" (fn [words] (mapv (fn [word] (set/rename-keys word {"prob" "score"})) words)))))
   transcription))


(comment 
  (def full-filepath-without-extension "resources/public/audio_and_transcript/whispers2t/Freediving - zapanować nad emocjami ｜ Mateusz Malina ｜ TEDxWSB [z5Vlh1VfuJE].cbr")
  (def t (-> (str full-filepath-without-extension ".json")
             slurp
             (cheshire/parse-string false)
             (get "output")))
  (get-in t [0 0 "text"])
  (get-in t [1 0 "text"])

  )

(defn find-translations-within-timestamp-range [start end translations]
  (filterv
   (fn [translation]
     (and
      (< start (get translation "start"))
      (< (get translation "end") end)))
   translations))

;;Detect any translations before the transcription segment starts 
;; and that ends before the transcription segment ending.
;;Must be long enough to detect translations with differing time stamps but not too long to detect translations 
;; of other segments.
;;If no translations found expand window by 0.1 seconds, each side, and try again.
(defn find-translations-for-segment [segment translations]
  (loop [allowed-translation-transcription-timestamp-difference 0.0]
   (let [start (- (get segment "start") allowed-translation-transcription-timestamp-difference)
         end (+ (get segment "end") allowed-translation-transcription-timestamp-difference)
         translations (find-translations-within-timestamp-range start end translations)]
    (if-not (empty? translations)
      translations
      (recur (+ allowed-translation-transcription-timestamp-difference 0.1))))))

(defn get-segment-data-with-translation [filename transcripts-and-translations]
  (let [transcribed (file-transcribed-segments-to-tree (get-in transcripts-and-translations [filename "transcription"]))
        translations (file-translated-segments-to-tree (get-in transcripts-and-translations [filename "translations"]))]
    (mapv (fn [segment]
            (assoc segment "translations" (find-translations-for-segment segment translations)))
          transcribed)))

(defn transcript-tree [filename full-filepath-without-extension]
  (let [transcripts-and-translations (transcripts_all_files)]
    (-> {"audio-filename" (str (subs full-filepath-without-extension (count "resources/public")) ".mp3")
         "label" filename
         "segments" (get-segment-data-with-translation filename transcripts-and-translations)}
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
  (-> (for [[filename full-filepath-without-extension] (find-mp3-files-at-path filepath)]
        (transcript-tree filename full-filepath-without-extension))
      (flatten-tree-of-maps-into-maps-referenced-by-id)
      pprint/pprint
      with-out-str
      (str/replace #"^" "  ")
      (#(str/replace (slurp "resources/txt/mock_data_template.txt") "%%%" %))
      (#(spit "src/com/submerged_structure/mock_data.cljc" %))))


(comment (find-mp3-files-at-path filepath))


(comment 
  (write-mock-data-cljs-file)
  
  )


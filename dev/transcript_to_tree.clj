(ns transcript-to-tree
  (:require [cheshire.core :as cheshire]
            [clojure.pprint :as pprint]
            [clojure.string :as str]
            ;[fulcrologic.client.primitives :as fp]
            [clojure.walk :refer [postwalk]]
            [clojure.core :as clj]
            [clojure.set :as set]

            [clojure.math :refer [round]]
            [clojure.pprint :as pp]
            [clojure.string :as s]))

;; Code to be run in the clj repl to convert the transcript data returned by my 
;; web service to edn.

;; Run's in the same JVM as the shadow-cljs repl.

;; Later this will be done on the server side and immediately put in the db.

(def filepath "resources/public/audio_and_transcript/")


(defn singular-from-plural
  "All plural keys are simply the singular key with an 's' appended."
  [s]
  (subs s 0 (dec (count s))))

(declare add-ns-and-keywordize-keys-in-vec)

(defn add-ns-and-keywordize-keys-in-m [m ns]
  (into {} (for [[k v] m]
             [(keyword ns k)
              (if (vector? v)
                (add-ns-and-keywordize-keys-in-vec v (singular-from-plural k))
                v)])))


(defn add-ns-and-keywordize-keys-in-vec [v ns]
  (into [] (for [m v]
             (add-ns-and-keywordize-keys-in-m m ns))))

(comment
  (add-ns-and-keywordize-keys-in-vec [{"a" 1 "b" 2} {"c" 3 "d" 4}] "segment")
  ;; => [#:segment{:a 1, :b 2} #:segment{:c 3, :d 4}]
  )


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
  (flatten-tree-of-maps-into-maps-referenced-by-id ms))

(comment
  (add-ns-and-keywordize-keys-in-m {"a" "xa" "words" [{"a" 5}] "b" 2} "segment")
  ;; => #:segment{:a "xa", :words [#:word{:a 5}], :b 2}
  (add-ns-and-keywordize-keys-in-m {"a" 1 "b" 2} "word")
  ;; => #:word{:a 1, :b 2}
  )




(defn transcripts_all_files []
  (-> (str filepath "transcriptions_and_translations.json")
      slurp
      (cheshire/parse-string false)))


(comment


  (let [filename "Freediving.mp3"
        transcripts-and-translations (transcripts_all_files)]
    (->> (get-in transcripts-and-translations [filename "transcription"])
         #_(mapv (fn [segment]
                   (->
                    (select-keys segment ["text" "start" "end"]))))))
  (let [filename "Freediving.mp3"
        transcripts-and-translations (transcripts_all_files)]
    (->> (get-in transcripts-and-translations [filename "translations"])
         #_(mapv (fn [segment]
                   (->
                    (select-keys segment ["text" "start" "end"])))))))

(defn whispers2t-keys-to-open-ai-standard-keys
  "Converts keys from whispers2t to open-ai standard keys.
   `lang` is added to each segment if provided, useful for translations."
  [segments]
  (mapv
   (fn [segment]
     (->
      (set/rename-keys segment {"word_timestamps" "words" "start_time" "start" "end_time" "end"})
      (update "words" (fn [words] (mapv (fn [word] (set/rename-keys word {"prob" "score"})) words)))))
   segments))


(comment
  (def full-filepath-without-extension "resources/public/audio_and_transcript/whispers2t/Freediving - zapanować nad emocjami ｜ Mateusz Malina ｜ TEDxWSB [z5Vlh1VfuJE].cbr")
  (def t (-> (str full-filepath-without-extension ".json")
             slurp
             (cheshire/parse-string false)
             (get "output")))
  (get-in t [0 0 "text"])
  (get-in t [1 0 "text"]))


(defn within-time-window [{:strs [start end]} time]
  (<= start time end))

(defn score-for-translation-fit-to-segment
  "Small score is better."
  [{segment-start "start" segment-end "end" :as segment} {translation-start "start" translation-end "end"}]

  (+ (if (within-time-window segment translation-start)
       0
       (Math/abs (- segment-start translation-start)))
     (if (within-time-window segment translation-end)
       0
       (Math/abs (- segment-end translation-end)))))

(defn add-item-to-vector-create-vector-if-necessary [v item]
  (if (nil? v)
    [item]
    (conj v item)))

(comment
  (update-in {} [:a] add-item-to-vector-create-vector-if-necessary 1)
  ;; => {:a [1]}
  )

(defn get-segment-data-with-translation [transcribed translations]
  (loop [currently-checking-transcription-no 0
         currently-checking-translation-no 0
         transcribed-with-translations transcribed]
    (let [current-transcription (get transcribed currently-checking-transcription-no)]
      (cond  (> currently-checking-transcription-no (count transcribed))
             transcribed-with-translations
             (> currently-checking-translation-no (count translations))
             (do (prn "No more translations to check. next-transcription-to-check: " currently-checking-transcription-no " current-transcription text " (get current-transcription "text"))
                 transcribed-with-translations)
             :else
             (let [current-translation (get translations currently-checking-translation-no)]
               (if-let [next-transcription (get transcribed (inc currently-checking-transcription-no))]
                 (if (< (score-for-translation-fit-to-segment current-transcription current-translation)
                        (score-for-translation-fit-to-segment next-transcription current-translation))
                     ;found a fit
                   (recur currently-checking-transcription-no
                          (inc currently-checking-translation-no)
                          (update-in transcribed-with-translations
                                     [currently-checking-transcription-no "translations"]
                                     add-item-to-vector-create-vector-if-necessary
                                     current-translation))
                     ;translation fits better with next transcription
                   (recur (inc currently-checking-transcription-no)
                          currently-checking-translation-no
                          transcribed-with-translations))
                   ;no more transcriptions to check - translation fits best with last transcription
                 (update-in transcribed-with-translations
                            [currently-checking-transcription-no "translations"]
                            add-item-to-vector-create-vector-if-necessary
                            current-translation)))))))

(defn get-segment-data-with-translation-all-langs [transcripts translations]
  (reduce (fn [transcripts-interim lang]
            (get-segment-data-with-translation transcripts-interim (get translations lang)))
          transcripts
          (keys translations)))


(defn add-id [m]
  (assoc m "id" (str (java.util.UUID/randomUUID))))

(defn add-ids [tree]
  (postwalk (fn [node]
              (if (map? node)
                (add-id node)
                node))
            tree))

(defn round-to-three-decimals [n]
  (/ (round (* 1000.0 n)) 1000.0))


(defn round-start-and-end-if-exists [{:strs [start] :as m}]
  (if start
    (-> m
        (update "start" round-to-three-decimals)
        (update "end" round-to-three-decimals))
    m))

(comment
  (round-start-and-end-if-exists {"start" 589.0799999999998 "end" 0})
  ;; => {"start" 589.08, "end" 0.0}
  (round-start-and-end-if-exists {})
  ;; => {}
  )

(defn round-all-starts-and-ends [tree]
  (postwalk (fn [node]
              (if (map? node)
                (round-start-and-end-if-exists node)
                node))
            tree))


(comment
  (update {} :start round-to-three-decimals))

(defn adjust-segment-starts-and-ends
  "When segments start and end times are the same, the time stampss from whispers2t tend to be a bit late for start of segment."
  [segments]
  (let [new-segment-starts
        (for [segment-no (range (count segments))]
          (let [segment (get segments segment-no)
                previous-segment (get segments (dec segment-no))]
            (if (and previous-segment (= (get previous-segment "end") (get segment "start")))
              (- (get segment "start") 0.15)
              (get segment "start"))))
        new-segment-ends
        (for [segment-no (range (count segments))]
          (let [segment (get segments segment-no)
                next-segment (get segments (inc segment-no))]
            (if (and next-segment (= (get next-segment "start") (get segment "end")))
              (- (get segment "end") 0.2)
              (get segment "end"))))]
    (mapv (fn [segment new-segment-start new-segment-end]
            (->
             segment
             (assoc "start" new-segment-start
                    "end" new-segment-end)
             (assoc-in ["words" 0 "start"] new-segment-start)
             (assoc-in ["words" (dec (count (get segment "words"))) "end"] new-segment-end)))
          segments
          new-segment-starts
          new-segment-ends)))



    (defn transcript-tree [filename full-filepath-without-extension]
      (let [transcripts-and-translations (transcripts_all_files)
            transcripts (whispers2t-keys-to-open-ai-standard-keys (get-in transcripts-and-translations [filename "transcription"]))
            translations-all-langs
            (into {} (map
                      (fn [[lang translations-for-one-lang]]
                        [lang (->>
                               (whispers2t-keys-to-open-ai-standard-keys translations-for-one-lang)
                               (mapv #(assoc % "lang" lang)))])
                      (get-in transcripts-and-translations [filename "translations"])))]
        (-> {"audio-filename" (str (subs full-filepath-without-extension (count "resources/public")) ".mp3")
             "label" filename
             "segments" (get-segment-data-with-translation-all-langs transcripts translations-all-langs)}
            add-ids
            round-all-starts-and-ends
            (update "segments" adjust-segment-starts-and-ends)
            (add-ns-and-keywordize-keys-in-m "transcript"))))

    (comment
      (transcript-tree "Freediving.mp3" "resources/public/audio_and_transcript/whispers2t/Freediving.mp3"))

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
      (write-mock-data-cljs-file))


(ns user
  (:require [cheshire.core :as cheshire]
            [clojure.pprint :as pprint]
            [clojure.string :as str]))

;; Code to be run in the clj repl to convert the transcript data returned by my 
;; web service to edn.

;; Run's in the same JVM as the shadow-cljs repl.

;; Later this will be done on the server side and immediately put in the db.

(def filepath "resources/public/audio_and_transcript/")

(def filename "realpolish-hint1")

(def full-filepath-without-extension (str filepath filename))

(defn write-mock-data-cljs-file []
  (-> (str full-filepath-without-extension ".json")
      slurp
      (cheshire/parse-string true)
      :output
      (select-keys [:segments])
      (assoc :audio (str full-filepath-without-extension ".mp3"))
      pprint/pprint
      with-out-str
      (str/replace #"^" "  ")
      (#(str "(ns com.submerged-structure.mock-data)\n\n(def transcript\n" % ")\n"))
      (#(spit "src/com/submerged_structure/mock_data.cljs" %))))

(comment 
  (write-mock-data-cljs-file)
  )


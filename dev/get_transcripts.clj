(ns get-transcripts
  (:require
   [babashka.http-client :as http]
   [cheshire.core :as json])
  (:import [java.net URLEncoder]))


(def audio_filepath "resources/public/audio_and_transcript/")
(def serverless-instance-id "ndhmhr68e8m8vu")

(defn find-mp3-files-at-path [path]
  (->> (java.io.File. path)
       (.listFiles)
       (filter #(.isFile %))
       (filter #(re-matches #".*\.mp3" (.getName %)))
       (map #(.getName %))))

(defn params-for-all-tasks [filenames params]
  (into [] (for [_filename filenames
                 task [:transcribe :translate]]
             (task params))))

(defn- url-encode
  "Returns an UTF-8 URL encoded version of the given string."
  [^String unencoded]
  (URLEncoder/encode unencoded "UTF-8"))

(defn job-input [filepath]
  (let [filenames (find-mp3-files-at-path filepath)]
    (into {}
          [["files" (into [] (for [filename [(first filenames)]
                                  _task [:transcribe :translate]]
                              {"audio_url" (str "https://submergedstructure.s3.eu-north-1.amazonaws.com/" (url-encode filename))}))]
           ["transcribe-with-vad"
           {"initial_prompts" (params-for-all-tasks [(first filenames)] {:transcribe ""
                                                               :translate ""})
            "lang_codes" (params-for-all-tasks [(first filenames)] {:transcribe "pl"
                                                          :translate "en"})
            "tasks" (params-for-all-tasks [(first filenames)] {:transcribe "transcribe"
                                                     :translate "translate"})}]])))

(comment (job-input audio_filepath))

(defn run-pod-call [opts]
  (http/request (merge {:headers {"Authorization" (str "Bearer " (System/getenv "RUNPOD_API_KEY"))
                                  :content-type "application/json"}} opts)))


(defn run-pod-serverless-instance-health []
  (run-pod-call {:method :get
                 :uri (str "https://api.runpod.ai/v2/" serverless-instance-id "/health/")}))


(defn run-pod-serverless-instance-run []
  (run-pod-call {:method :post
                 :uri (str "https://api.runpod.ai/v2/" serverless-instance-id "/run/")
                 :body (json/generate-string {"input" (job-input audio_filepath)})}))

(defn run-pod-serverless-instance-status [job-id]
  (run-pod-call {:uri (str "https://api.runpod.ai/v2/" serverless-instance-id "/status/" job-id)}))

;; REST call to url


(comment (->
          (http/get "https://postman-echo.com/get" {:query-params {"q" "clojure"}})
          :body
          (json/parse-string true)
          :args)

         (-> (http/get (str "https://api.runpod.ai/v2/" serverless-instance-id "/health/") {:headers {"Authorization" (str "Bearer " (System/getenv "RUNPOD_API_KEY"))}})
             :body
             (json/parse-string true))
         (run-pod-serverless-instance-run)
         (run-pod-serverless-instance-health)
         (def resp (run-pod-serverless-instance-status "080caf98-7aa4-484b-821b-f048becb5f6d-e1"))
         resp
         (-> resp
             :body
             )
         )

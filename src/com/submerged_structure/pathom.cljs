(ns com.submerged-structure.pathom
  "The Pathom parser that is our (in-browser) backend.
   
   Add your resolvers and 'server-side' mutations here."
  {:clj-kondo/config '{:linters {:unused-namespace {:level :off}}}}
  (:require
   [cljs.core.async :as async]
   [com.wsscode.pathom.viz.ws-connector.core :as-alias pvc]
   [com.wsscode.pathom.viz.ws-connector.pathom3 :as pathom3-viz-conn]
   [com.wsscode.pathom3.cache :as p.cache]
   [com.wsscode.pathom3.connect.built-in.plugins :as pbip]
   [com.wsscode.pathom3.connect.built-in.resolvers :as pbir]
   [com.wsscode.pathom3.connect.foreign :as pcf]
   [com.wsscode.pathom3.connect.indexes :as pci]
   [com.wsscode.pathom3.connect.operation :as pco]
   [com.wsscode.pathom3.connect.operation.transit :as pcot]
   [com.wsscode.pathom3.connect.planner :as pcp]
   [com.wsscode.pathom3.connect.runner :as pcr]
   [com.wsscode.pathom3.error :as p.error]
   [com.wsscode.pathom3.format.eql :as pf.eql]
   [com.wsscode.pathom3.interface.async.eql :as p.a.eql]
   [com.wsscode.pathom3.interface.eql :as p.eql]
   [com.wsscode.pathom3.interface.smart-map :as psm]
   [com.wsscode.pathom3.path :as p.path]
   [com.wsscode.pathom3.plugin :as p.plugin]
   [promesa.core :as p]
   
   [com.submerged-structure.mock-data :as mock-data]

   [com.submerged-structure.mutations.words-and-segments]
   [com.submerged-structure.mutations.load]
   [com.submerged-structure.mutations.controls]
   [com.submerged-structure.mutations.translations]
   ))


(pco/defresolver all-transcripts [_ _]
  {::pco/output [{:transcript-switcher/all-transcripts [:transcript/id]}]}
  {:transcript-switcher/all-transcripts (mapv #(into {} [[:transcript/id %]]) (mock-data/transcript-ids))})


(pco/defresolver current-transcript [_ _]
  {::pco/output [{:root/current-transcript [:transcript/id]}]}
  {:root/current-transcript (mock-data/nth-transcript-id 2)})

(comment (filter (fn [[k _]] (= :transcript/id k)) mock-data/transcripts))

(pco/defresolver translation-control-data [_ {:ui-translation-control/keys [language]}]
  {::pco/input [:ui-translation-control/language]
   ::pco/output [:ui-translation-control/language :ui-translation-control/visible-translations?]}
  {:ui-translation-control/language language
   :ui-translation-control/visible-translations? false})

(pco/defresolver transcript-data
  [_ {:keys [transcript/id]}]
  {::pco/input  [:transcript/id]
   ::pco/output [:transcript/audio-url :transcript/label :transcript/summary :transcript/label-pl :transcript/summary-pl :transcript/url :transcript/segments :transcript/id :transcript/display-type :ui-player/scroll-to-active :ui-player/doing]}
  (js/console.log "MOCK SERVER: Simulate loading transcript data" id)
  (->
   (get mock-data/transcripts [:transcript/id id])
   (assoc :ui-player/scroll-to-active true
          :ui-player/doing :loading
          :transcript/display-type :grammar
          :ui-translation-controls/languages [{:ui-translation-control/language "en"}]
          :ui-transcript-autopause/next-period-start nil
          :ui-transcript-autopause/next-period-end nil)))

#_(defn segment-data-from-tree [segment-id]
  (get mock-data/transcripts [:segment/id segment-id]))


#_(defn make-link-idents-in-object
  "turn IDs with `ns` namespaced keys `keys` in `obj` into idents to the objects in same `ns` or optionally `other-ns`."
  [obj ns keys & [other-ns]]
  (let [nsed-keys (map (partial keyword ns) keys)
        idents (map (fn [k] (hash-map (keyword (if other-ns other-ns ns) "id") ((keyword ns k) obj))) keys)]
    (apply assoc obj (interleave nsed-keys idents))))


(comment

  #_(make-link-idents-in-object {:segment/prev "1" :segment/next "2"} "segment" ["prev" "next"])
  ;; => #:segment{:prev #:segment{:id "1"}, :next #:segment{:id "2"}}

  (nth (keys mock-data/transcripts) 2)
  ;; => "67fe3ce1-7bcd-434c-a595-c609c51873e7"

  (get-in mock-data/transcripts ["67fe3ce1-7bcd-434c-a595-c609c51873e7" :transcript/segments 1 :segment/words 1])
  #_(make-link-idents-in-object (get-in mock-data/transcripts ["67fe3ce1-7bcd-434c-a595-c609c51873e7" :transcript/segments 1 :segment/words 1]) "word" ["prev" "next"])
  ;; => #:word{:end 13.894,
  ;;           :score 0.94,
  ;;           :start 13.194,
  ;;           :word "wielokrotnym",
  ;;           :id "0fb9c263-b65c-4276-a299-3bc29adb4d8b",
  ;;           :prev #:word{:id "0ec8b93b-f437-41a0-a57c-c00b50f88d62"},
  ;;           :next #:word{:id "381568e0-6722-4764-8883-5169ee4c47ee"},
  ;;           :segment "3797cf8d-7483-4390-88f0-609f966f1254"}
  #_(make-link-idents-in-object (get-in mock-data/transcripts ["67fe3ce1-7bcd-434c-a595-c609c51873e7" :transcript/segments 1 :segment/words 1]) "word" ["segment"] "segment")
  ;; => #:word{:end 13.894,
  ;;           :score 0.94,
  ;;           :start 13.194,
  ;;           :word "wielokrotnym",
  ;;           :id "0fb9c263-b65c-4276-a299-3bc29adb4d8b",
  ;;           :prev "0ec8b93b-f437-41a0-a57c-c00b50f88d62",
  ;;           :next "381568e0-6722-4764-8883-5169ee4c47ee",
  ;;           :segment #:segment{:id "3797cf8d-7483-4390-88f0-609f966f1254"}}



  

  


  #_(segment-data-from-tree "946ed8e9-8546-4128-ac5f-a85c3b2cbf14")
  )


(pco/defresolver segment-data
  [_ {:keys [segment/id]}]
  {::pco/input [:segment/id]
   ::pco/output [:segment/words :segment/end :segment/start :segment/id :segment/translations]}
  (get mock-data/transcripts [:segment/id id]))

(pco/defresolver translation-data
  [_ {:keys [:translation/id]}]
  {::pco/input [:translation/id]
   ::pco/output [:translation/id :translation/lang :translation/text :translation/start :translation/end :translation/visible?]}
  (-> 
   (get mock-data/transcripts [:translation/id id])
   (assoc :translation/visible? false)))


(pco/defresolver word-data
  [_ {:keys [:word/id]}]
  {::pco/input [:word/id]
   ::pco/output [:word/start :word/end :word/id :word/word :word/score  :word/active]}
  (->(get mock-data/transcripts [:word/id id])
   (assoc :word/active false)))

(pco/defresolver token-data
  [_ {:keys [:token/id]}]
  {::pco/input [:token/id]
   ::pco/output [:token/text :token/morph :token/lemma :token/pos :token/pos_explained :token/is_morphed :token/norm  :token/whitespace]}
  (get mock-data/transcripts [:token/id id]))



(def my-resolvers-and-mutations
  "Add any resolvers you make to this list (and reload to re-create the parser)"
  [#_create-random-thing
   #_i-fail
   #_person
   transcript-data
   translation-control-data
   segment-data
   translation-data
   word-data
   token-data
   current-transcript
   all-transcripts])

(def enable-pathom-viz false)

(defn connect-pathom-viz
  "Expose indices to standalone Pathom-Viz v2022+"
  [env]
  (try (cond-> env
         enable-pathom-viz
         (pathom3-viz-conn/connect-env {::pvc/parser-id `env}))
       (catch :default e
         (println "Failed to enable Pahom-Viz" e)
         env)))

(def env
  (-> {:com.wsscode.pathom3.error/lenient-mode? true}
      (p.plugin/register pbip/mutation-resolve-params) ; needed or not?
      (pci/register my-resolvers-and-mutations)
      ;; Uncomment the line below to enable Pathom Viz GUI to connect to the app
      connect-pathom-viz))

(defn new-parser "DIY parser" []
  (fn [eql]
    (let [ch (async/promise-chan)]
      (-> (p.a.eql/process env eql)
          (p/then #(do
                     (println "PARSER:" eql "->" %)
                     (async/go (async/>! ch %)))))
      ch)))

(comment
  (transcript-data nil {:transcript/id "2221f28c-0f2d-479b-b4a7-80924c80721c"})
  (segment-data nil {:segment/id "9a0b9cfe-6f5f-4e5a-bf65-cbecfca00ba6"})

  (p.eql/process env '[{:i-fail [*]}])

  (p.eql/process
   (pci/register
    (pco/resolver 'error
                  {::pco/output [:error]}
                  (fn [_ _]
                    (throw (ex-info "Deu ruim." {})))))
   [:error])

  (p.eql/process
   (-> {:com.wsscode.pathom3.error/lenient-mode? true}
       (pci/register
        (pco/resolver 'error
                      {::pco/output [:error]}
                      (fn [_ _]
                        (throw (ex-info "Deu ruim." {}))))))
   [:error ::pcr/attribute-errors ::pcr/mutation-error]))


(comment
  (->>
   mock-data/transcripts
   (keep (fn [[[kw id] v]] (when (= :transcript/id kw) [(:transcript/audio-filename v) {"id" id}])))
   (into {}))
  )
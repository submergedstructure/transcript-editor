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
   [com.submerged-structure.mutations :as mutations]))


(pco/defresolver current-transcript [_ _]
  {::pco/output [{:root/current-transcript [:transcript/id]}]}
  {:root/current-transcript {:transcript/id (nth (keys mock-data/transcripts) 1)}})


(pco/defresolver transcript-data
  [_ {:keys [transcript/id]}]
  {::pco/input  [:transcript/id]
   ::pco/output [:transcript/audio-filename :transcript/label :transcript/segments :transcript/id :transcript/current-time]}
  (js/console.log "MOCK SERVER: Simulate loading transcript data" id)
  (get (assoc-in mock-data/transcripts [id :transcript/segments] (mapv #(select-keys % [:segment/id]) (get-in mock-data/transcripts [id :transcript/segments]))) id))

(defn segment-data-from-tree [segment-id]
  (->> (vals mock-data/transcripts);all transcripts
       (mapcat :transcript/segments);all segemnts from all transcripts
       (filter #(= (:segment/id %) segment-id))
       first ;specific segment
       (#(assoc % :segment/words (mapv (fn [word] (select-keys word [:word/id])) (get % :segment/words))))))

  #_(first (filter #(= (:segment/id %) id) (mapcat :transcript/segments (vals mock-data/transcript))))

(comment (segment-data-from-tree "4a1a191f-7586-47d2-bfbb-f21722ed8bb1"))


(pco/defresolver segment-data
  [_ {:keys [segment/id]}]
  {::pco/input [:segment/id]
   ::pco/output [:segment/words :segment/id]}
  (segment-data-from-tree id))

(defn word-data-from-tree [word-id]
  (->> mock-data/transcripts
       vals
       (mapcat :transcript/segments)
       (mapcat :segment/words)
       (filter #(= (:word/id %) word-id))
       first))

(comment (word-data-from-tree "1b966e29-e96a-4af7-a601-7a71d6fa89f4"))

(pco/defresolver word-data
  [_ {:keys [:word/id]}]
  {::pco/input [:word/id]
   ::pco/output [:word/start :word/end :word/id :word/word :word/score]}
  {::pco/input [:word/id]
   ::pco/output [:word/start :word/end :word/id :word/word :word/score]}
  (word-data-from-tree id))


(def my-resolvers-and-mutations
  "Add any resolvers you make to this list (and reload to re-create the parser)"
  [#_create-random-thing
   #_i-fail
   #_person
   transcript-data
   segment-data
   word-data
   current-transcript])

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

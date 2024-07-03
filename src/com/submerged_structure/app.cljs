(ns com.submerged-structure.app
  (:require
   [com.submerged-structure.mock-server :as mock-server]
   [com.fulcrologic.fulcro.application :as app]
   [com.fulcrologic.fulcro.react.version18 :refer [with-react18]]
   [edn-query-language.core :as eql]
   [com.fulcrologic.fulcro.components :as comp]))

(defn global-eql-transform
  [ast]
  (-> ast
      (app/default-global-eql-transform)
      ;; Make sure that if Pathom sends ::p/errors, Fulcro does not remove it:
      #_(update :children conj (eql/expr->ast :com.wsscode.pathom3.connect.runner/attribute-errors))))

(defonce app (-> (app/fulcro-app {:remotes {:remote (mock-server/mock-remote)}
                                  :global-eql-transform global-eql-transform
                                  :remote-error?
                                  (fn [result]
                                    (or
                                     (app/default-remote-error? result)
                                     (:com.wsscode.pathom3.connect.runner/attribute-errors (:body result))))
                                  :global-error-action
                                  (fn [{{:keys [body status-code error-text]} :result :as env}]
                                    (println "WARN: Remote call failed"
                                             status-code
                                             error-text
                                             body
                                             (:com.wsscode.pathom3.connect.runner/attribute-errors body)))
                                  :render-middleware
                                   (when goog.DEBUG js/holyjak.fulcro_troubleshooting.troubleshooting_render_middleware)})
                 (with-react18)))


(comment 
  (-> (app/current-state app)
   :transcript/id
   (get "2221f28c-0f2d-479b-b4a7-80924c80721c"))
  ;; => nil

  (:root/current-transcript (app/current-state app))
  ;; => [:transcript/id "71f28d76-ba2e-46da-be47-8d2162b20803"]


  (assoc-in (app/current-state app) [:transcript/id "2221f28c-0f2d-479b-b4a7-80924c80721c" :transcript/current-time] 20)
  (comp/transact! app ['(com.submerged-structure.mutations/update-transcript-current-time {:transcript/current-time 5})])
  
  )
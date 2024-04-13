(ns com.submerged-structure.ui
  (:require #?(:clj [com.fulcrologic.fulcro.dom-server :as dom  :refer [div h1 li p ul]]
      :cljs [com.fulcrologic.fulcro.dom :as dom  :refer [div h1 li p ul]])
            [com.fulcrologic.fulcro.algorithms.normalize :as fn]
            [com.fulcrologic.fulcro.application :as app]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.data-fetch :as df]
            [com.fulcrologic.fulcro.raw.components :as rc]
            [com.submerged-structure.mock-data :as mock-data]))

(defsc Word [this {:keys [word/start word/end word/word]}]
  {:ident :word/id
   :query [:word/id :word/start :word/end :word/word]}
  (li "Word componet "(str start " - " end ": " word)))

(def ui-word (comp/factory Word {:keyfn :word/id}))


(defsc Segment [this {:keys [segment/start segment/end segment/text segment/words]}]
  {:ident :segment/id
   :query [:segment/id :segment/start :segment/end :segment/text {:segment/words (comp/get-query Word)}]}
  (li "Sgement component" (str start " - " end ": " text)
      (ul
       (map ui-word words))))

(def ui-segment (comp/factory Segment {:keyfn :segment/id}))

(defsc Transcript [this {:keys [transcript/id transcript/audio-filename transcript/label transcript/segments]}]
  {:ident :transcript/id
   :query [:transcript/id :transcript/label
           :transcript/audio-filename
           {:transcript/segments (comp/get-query Segment)}]}
  (div
   (p "Hello from the ui/Transcript component!")
   (div
    (h1 label)
    (p audio-filename)
    (p (str id))
    (ul
     (map ui-segment segments)))))

(def ui-transcript (comp/factory Transcript {:keyfn :transcript/id}))

(defsc Root [this {:keys [:root/current-transcript]}]
  {:query (fn [_] [{:root/current-transcript (comp/get-query Transcript)}])}
  (div
   (p "Hello from the ui/Root component!")
   (ui-transcript current-transcript)))


(comment
  (comp/get-query Root)

  (require '[clojure.walk :as w])
  (w/postwalk
   (fn [x]
     (cond
       (map-entry? x) x
       (vector? x) (list
                    (get-in (meta x)
                            [:component :displayName]
                            :NO-COMPONENT)
                    (filterv map? x))

       :default x))
   (comp/get-query Root))
  ;; => (:NO-COMPONENT
  ;;     [#:root{:current-transcript
  ;;             (:NO-COMPONENT [#:transcript{:segments (:NO-COMPONENT [#:segment{:words (:NO-COMPONENT [])}])}])}])





  (require 'clojure.spec.alpha 'edn-query-language.core)
  (clojure.spec.alpha/explain
   :edn-query-language.core/query
   (comp/get-query com.submerged-structure.ui/Root))

  (comp/get-initial-state Transcript {})
  mock-data/transcript
  (comp/get-query com.submerged-structure.ui/Transcript)
  (com.fulcrologic.fulcro.components/get-initial-state Root {})
  (app/current-state app.application/app)
  (fn/tree->db  Root (com.fulcrologic.fulcro.components/get-initial-state Root) true)
  (df/load! com.submerged-structure.app/app :transcript (rc/nc '[*]))
  (df/load! com.submerged-structure.app/app [:transcript/id "2221f28c-0f2d-479b-b4a7-80924c80721c"] Transcript)
  ;; Load this => 
  ;; Pathom 2: will get back from server {:sequence [{..}, ..]} - i.e. a *vector*
  ;; even though the resolver returns a lazy seq
  ;; Pathom 3: returns a list: {:sequence ({..}, ..)}
  (df/load! com.submerged-structure.app/app :sequence (rc/nc [:tst/id :tst/val]))
  )
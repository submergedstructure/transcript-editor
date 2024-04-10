(ns com.submerged-structure.ui
  (:require
   [com.submerged-structure.mutations :as mut]
   [com.fulcrologic.fulcro.algorithms.merge :as merge]
   [com.fulcrologic.fulcro.algorithms.tempid :as tempid]
   [com.fulcrologic.fulcro.algorithms.data-targeting :as targeting]
   [com.fulcrologic.fulcro.algorithms.normalized-state :as norm]
   [com.fulcrologic.fulcro.components :as comp :refer [defsc transact!]]
   [com.fulcrologic.fulcro.raw.components :as rc]
   [com.fulcrologic.fulcro.data-fetch :as df]
   [com.fulcrologic.fulcro.dom :as dom :refer [button div form h1 h2 h3 input label li ol p ul]]
   [com.submerged-structure.mock-data :as mock-data]
   [com.submerged-structure.ui :as ui]))

(defsc Segment [this {:keys [start end text]}]
  (li (str start " - " end ": " text)))

(def ui-segment (comp/factory Segment))

(defsc Transcript [this {:keys [label segments]}]
  {#_#_:query [[df/marker-table :load-progress] :label :segments]}
  (div
   (pr-str segments)
   (p "Hello from the ui/Transcript component!")
   (div
    (h1 label)
    (ul
     (map ui-segment segments)))))

(def ui-transcript (comp/factory Transcript))

(defsc Root [this {:keys [transcript]}]
  {:initial-state {:transcript mock-data/transcript}
   #_#_:query [{[:transcript/id 1] (comp/get-query Transcript)}]}
  (div
   (p "Hello from the ui/Root component!")
   (ui-transcript transcript)))

#_(defsc Root [this props]
  {:query [[df/marker-table :load-progress]]}
  (div
   (p "Hello from the ui/Root component!")
   (div {:style {:border "1px dashed", :margin "1em", :padding "1em"}}
    (p "Invoke a load! that fails and display the error:")
    (when-let [m (get props [df/marker-table :load-progress])]
      (dom/p "Progress marker: " (str m)))
    (button {:onClick #(df/load! this :i-fail (rc/nc '[*]) {:marker :load-progress})} "I fail!"))
   (div {:style {:border "1px dashed", :margin "1em", :padding "1em"}}
    (p "Simulate creating a new thing with server-assigned ID, leveraging Fulcro's tempid support:")
    (button {:onClick #(let [tmpid (tempid/tempid)]
                         (comp/transact! this [(mut/create-random-thing {:tmpid tmpid})]))}
            "I create!")
    (when-let [things (:new-thing props)]
      (p (str "Created a thing with the ID: " (first (keys things))))))))

(comment
  mock-data/transcript
  (com.fulcrologic.fulcro.components/get-initial-state Root {})
  (com.fulcrologic.fulcro.application/current-state app.application/app)
  (fdn/db->tree [{:friends [:list/label]}] (comp/get-initial-state app.ui/Root {}) {})
  (df/load! com.submerged-structure.app/app :transcript (rc/nc '[*]))
  (df/load! com.submerged-structure.app/app :transcript Transcript)
  ;; Load this => 
  ;; Pathom 2: will get back from server {:sequence [{..}, ..]} - i.e. a *vector*
  ;; even though the resolver returns a lazy seq
  ;; Pathom 3: returns a list: {:sequence ({..}, ..)}
  (df/load! com.submerged-structure.app/app :sequence (rc/nc [:tst/id :tst/val])))
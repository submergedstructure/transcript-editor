(ns com.submerged-structure.components.home
  (:require [com.fulcrologic.fulcro.dom :as dom]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.dom.html-entities :as ent]
            [com.submerged-structure.components.transcript :as transcript]
            [com.fulcrologic.rad.routing :as rad-routing]))

(defsc Home [this {:keys []}]
  {:ident (fn [] [:component/id ::Home])
   :query []
   :initial-state {}
   :route-segment ["home"]}
  (dom/div
   :#home
   (dom/div
    :.pusher
    (dom/div
     :.ui.inverted.vertical.masthead.center.aligned.segment

     (dom/div
      :.ui.text.container
      (dom/h1 :.ui.inverted.header "SubmergedStructure.com")
      (dom/h3 "Listen to any audio in your target language with all the help you need when you need it.")


      (dom/div
       :.ui.huge.primary.button
       {:onClick #(rad-routing/route-to! this transcript/Transcript {:transcript/id "a0f1cee2-b66a-4a7f-afd8-e6b362724c5d"})}
       "Example interative transcript"
       (dom/i :.right.arrow.icon))))
    (dom/div
     :.ui.vertical.stripe.segment
     (dom/div
      :.ui.text.container
      (dom/h3 :.ui.header "Learn a language by engaging with content personally interesting to you and suitable for your level of learning.")
      (dom/p
       "Link to or upload any audio file or YouTube video. The more you are actually interested in understanding material, the more your mind engages more fully with the language, and along the way you will learn vocab in your interest area, natural sounding pronunciation and get a feel for the way words are put together - the grammar of the language.")
      (dom/p "Language learning can often over focus on conscious learning of grammar rules. Somehow we have to get to a place where we can unconcsciously produce words expressing our meaning and understand other's meaning. Immersion in engaging content you want to understand can help you here.")
      (dom/h3 :.ui.header "All the help you need when you need it.")
      (dom/ul
       (dom/li "Interative transcript lets you read each word as you hear it. Keyboard shortcuts and buttons let you skip back and forth; controls are optimised for language learning. You can autopause after each sentence or let the audio play on.")
       (dom/li "Translation of each sentence available when you need it.")
       (dom/li "Coloring of words, underlining and symbols reveal the grammatical strucuture of the sentence.")
       (dom/li "Click or touch on any word to see a popup with a further breakdown of the features of that word."))))

    (dom/div
     :.ui.vertical.stripe.segment.bg-image
     (dom/div
      :.ui.top.aligned.stackable.grid.container
      (dom/div
       :.row
       (dom/div
        :.eight.wide.column
        (dom/h2 :.ui.header "You can use this site on your mobile phone to listen on the go.")

        (dom/img
         :.ui.large.bordered.rounded.image
         {:src "/images/mobile.jpg"}))
       (dom/div
        :.six.wide.column
        (dom/h2 :.ui.header "Translations available when you need them.")
        (dom/img
         :.ui.large.bordered.rounded.image
         {:src "/images/grammar-x-ray-plus-translation.jpg"})))
      (dom/div
       :.row
       (dom/div
        :.six.wide.column
        (dom/h2 :.ui.header "Grammar X-Ray.")

        (dom/p
         "Our Grammar X-Ray feature will accelerate your understanding of the underlying structure of each sentence.")
        (dom/h4 :.ui.header "Before.")
        (dom/img
         :.ui.large.bordered.rounded.image
         {:src "/images/just-text.jpg"})

        (dom/h4 :.ui.header "After.")
        (dom/img
         :.ui.large.bordered.rounded.image
         {:src "/images/grammar-x-ray.jpg"}))
       (dom/div
        :.eight.wide.right.floated.column
        (dom/h2 :.ui.header "Grammar X-Ray key.")
        (dom/img
         :.ui.large.bordered.rounded.image
         {:src "/images/x-ray-key.jpg"})))
      (dom/div
       :.row.centered
       (dom/div
        :.six.wide.column
        
        (dom/h2 :.ui.header "Dive into more details of a words properties by clicking on it or touching it.")
        
        (dom/img
         :.ui.large.bordered.rounded.image
         {:src "/images/grammar-popup.jpg"})))
      )
     (dom/div
      :.ui.text.container.center.aligned.padding
      (dom/div
       :.ui.huge.primary.button
       {:onClick #(rad-routing/route-to! this transcript/Transcript {:transcript/id "a0f1cee2-b66a-4a7f-afd8-e6b362724c5d"})}
       "Example interative transcript"
       (dom/i :.right.arrow.icon)))))))
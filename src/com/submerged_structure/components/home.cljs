(ns com.submerged-structure.components.home
  (:require [com.fulcrologic.fulcro.dom :as dom]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.submerged-structure.components.transcript-page :as transcript]
            [com.fulcrologic.rad.routing :as rad-routing]
            
            [com.fulcrologic.semantic-ui.elements.icon.ui-icon :refer [ui-icon]]
            [com.fulcrologic.semantic-ui.icons :as i]))

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
      (dom/h2 "Create a regular listening and simultaneous reading habit to super charge your Polish learning. With the best tool to help you progressively understand more and more of what you are listening to.")


      (dom/div
       :.ui.huge.primary.button
       {:onClick #(rad-routing/route-to! this transcript/TranscriptPage {:transcript/id "a0f1cee2-b66a-4a7f-afd8-e6b362724c5d"})}
       "Example interactive transcript"
       (dom/i :.right.arrow.icon))))
    (dom/div
     :.ui.vertical.stripe.segment
     (dom/div
      :.ui.text.container
      (dom/h2 :.ui.header "An early overemphasis on learning grammar rules, and the further exceptions to those rules, overwhelms many learners and leaves them unsure of their ability to learn Polish.")
      (dom/h3 :.ui.header "You can begin listening and understanding Polish today.")
      (dom/p "Little grammar is needed for understanding the meaning of what is being said. Listening improves your vocabulary, intuition of how words are put together and how they should sound. And as your natural curiosity about how sentences are put together is inspired we have you covered letting you gradually explore from a place of curiosity. The listening and reading you do today will help what you learn about how to put words together later and on the way, to actually stick in your head.")
      (dom/p
       "Soon you will be able to link to or upload any audio file or YouTube video. The more you are actually interested in understanding material, the more your mind engages more fully with the language.")
      (dom/p "Somehow we have to get to a place where we can unconsciously produce words expressing our meaning and understand other's meaning.")
      (dom/h3 :.ui.header "All the help you need when you need it.")
      (dom/ul
       (dom/li "Interactive transcript lets you read each word as you hear it. Keyboard shortcuts and buttons let you skip back and forth; controls are optimized for language learning. You can auto pause after each sentence or let the audio play on.")
       (dom/li "Translation of each sentence available when you need it.")
       (dom/li "Coloring of words, underlining and symbols reveal the grammatical structure of the sentence.")
       (dom/li "Click or touch on any word to see a popup with a further breakdown of the features of that word for when you become curious about why a word ends in a certain way and how words are put together. Short to the point grammar lesson nuggets when your curiosity is piqued."))))

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

        (dom/h2 :.ui.header "Dive into more details of a word's grammatical properties by clicking on the word or touching it.")

        (dom/img
         :.ui.large.bordered.rounded.image
         {:src "/images/grammar-popup.jpg"}))))
     (dom/div
      :.ui.text.container.center.aligned.padding
      (dom/div
       :.ui.huge.primary.button
       {:onClick #(rad-routing/route-to! this transcript/TranscriptPage {:transcript/id "a0f1cee2-b66a-4a7f-afd8-e6b362724c5d"})}
       "Example interactive transcript"
       (dom/i :.right.arrow.icon)))
     (dom/h2
      :.ui.horizontal.header.divider
      (dom/a {:href "#"} (dom/i :.angle.double.down.icon)
             " Developments in progress.  "
             (dom/i :.angle.double.down.icon)))
     (dom/div
      :.ui.vertical.stripe.quote.segment
      (dom/div
       :.ui.equal.width.stackable.internally.celled.grid
       (dom/div
        :.center.aligned.row
        (dom/div
         :.column
         (dom/h3 (ui-icon {:name i/lightbulb-icon :color "yellow"}) "Let user progressively reveal text, grammar highlighting and then translation for audio listened to so far." )
         (dom/p "With repeated button click or key press, we will reveal first the text, then the grammar highlighting and then the translation of the audio listened to so far."))
        (dom/div
         :.column
         (dom/h3
          (ui-icon {:name i/lightbulb-icon :color "yellow"})
          "Allow user to generate similar transcript from any linked or uploaded video or audio."
          )
         (dom/p
          "The transcripts generated will be of the same quality as the example on the site that was automatically generated in seconds by the latest AI tech.")))))
     (dom/div
      :.ui.vertical.stripe.quote.segment
      (dom/div
       :.ui.equal.width.stackable.internally.celled.grid
       (dom/div
        :.center.aligned.row
        (dom/div
         :.column
         (dom/h3 (ui-icon {:name i/lightbulb-icon :color "yellow"}) "Integrate dictionary lookup directly in the app." )
         (dom/p "When you click on a word as well as getting the details of the grammar of the word, you will also instantly get a dictionary definition of the root of the word."))
        (dom/div
         :.column
         (dom/h3
          (ui-icon {:name i/lightbulb-icon :color "yellow"})
          "Allow user to generate playlists of transcripts."
          )
         (dom/p
          "To make the app useful for listening on the go as well as sitting down to study we will allow users to create playlists of their favorite transcripts. ")
         (dom/p
          "This web app works on mobile devices and we want users to be able to listen wherever they are and whatever they are doing.")))))
     (dom/div
      :.ui.vertical.stripe.quote.segment
      (dom/div
       :.ui.equal.width.stackable.internally.celled.grid
       (dom/div
        :.center.aligned.row
        (dom/div
         :.column
         (dom/h3 (ui-icon {:name i/lightbulb-icon :color "yellow"}) "Switch to two additional views of the current transcript with sentences categorized by noun phrases or verb phrases." )
         (dom/p "This will help you understand word endings and see grouped real examples in the current transcript of:")
         (dom/ul
          (dom/li "noun phrases categorized by case, whether they are plural or singular, masculine (animate or inanimate), feminine or neutral.")
          (dom/li "verb phrases categorized by tense, aspect, mood, person and number."))
         (dom/p "There will be a short explanation of how the word endings of words change for this category of noun or verb phrase."))
        (dom/div
         :.column
         (dom/h3
          (ui-icon {:name i/lightbulb-icon :color "yellow"})
          "Ideal navigation to follow your curiosity."
          )
         (dom/p
          "Scroll through all the examples or from the grammar info for a particular word skip to the part of the view where you can see other noun phrases or verb phrases in the same category as the current one.")))))
     (dom/div
      :.ui.vertical.stripe.quote.segment
      (dom/div
       :.ui.equal.width.stackable.internally.celled.grid
       (dom/div
        :.center.aligned.row
        (dom/div
         :.column
         (dom/h3 (ui-icon {:name i/lightbulb-icon :color "yellow"}) "Reach out to learners." )
         (dom/h4 "User group in Kraków")
         (dom/p "I'm going to start a user group in Kraków to meet in person to help guide development and to get feedback on the app and what would be useful to them. Also in the group I hope we can help each other think about ways to use this tool effectively for language learning, how to effectively make use of what language schools have to offer and think about how to make best use of language exchange partners.")
         (dom/h4 "Online")
         (dom/p "I'll be making some videos to introduce the app to people and how it might be helpful for their language learning."))
        (dom/div
         :.column
         (dom/h3
          (ui-icon {:name i/lightbulb-icon :color "yellow"})
          "Reach out to content creators."
          )
         (dom/p
          "I want to encourage content creators to create natural sounding audio and video content, particularly material for beginners. There are already some great examples of beginner content being produced and hope to get some of these people on board with this project and get advice from them how we can help each other.")
         (dom/p
          "I'm considering how we can help content creators get paid for their efforts.")
         (dom/h4 "Examples of content creators creating content useful to beginner learners, often for free.")
         (dom/p (dom/a {:href "https://realpolish.pl/"} "Piotr at RealPolish.pl"))
         (dom/p (dom/a {:href "https://ioannesoculus.com/polish-with-john/"} "Ioannes Oculus and his \"Polish with John Podcast\"."))
         (dom/p (dom/a {:href "https://www.youtube.com/@LingoPutPolish"} "LingoPut - Polish Comprehensible Input."))))))))))
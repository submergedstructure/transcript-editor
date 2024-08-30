(ns com.submerged-structure.app-help
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [li p ul b a]]
            [com.fulcrologic.semantic-ui.collections.message.ui-message-header :refer [ui-message-header]]
            [com.fulcrologic.fulcro.components :as comp :refer [fragment]]))


(def app-help
  (fragment

   (ui-message-header nil "How to use this player")
   (p "As the audio plays you will see the currently spoken word is highlighed.")
   (ul
    (li (b "Several transcripts available: ") "At the very top of the page the heading with the transcript name is actually a dropdown menu, you can use this to switch between different transcripts.")
    (li (b "Choose from several formatting options, by clicking on the tabs direcly below this message:")
        (ul 
         (li (b "\"Plain Transcript - No Coloring\"") " for no formatting.")
         (li (b "\"AI's Confidence of Each Word\"") " colors each word to show the AI's confidence of the transcription of that word.")
         (li (b "\"Grammar X-Ray\"") " formats text to show the grammatical features of each word. (the default option)")))
    
    (li (b "Playback control: ")
        (ul
         (li "You can click on any word in the transcript to skip forward or backward to that word and begin playing.")
         (li "You can also click on the audio waveform itself to skip to that point in the audio.")
         (li "The top of the waveform visualisation is a zoomed in view of the currently playing audio. Below this is a zoomed out view of the entire audio. You can click on either to skip to that point in the audio.")
         (li "Hover over playback control buttons below the waveform with your cursor for more information about what they do.")))
    (li (b "Translations: ")
        (ul
         (li "Click on the button next to a sentence to see a translation of that sentence. You can also click on the translation to hide it again.")
         (li "You can also click on the button to the right of the player controls to show or hide all translations at once.")))
    (li (b "Grammar Analysis:")
        (ul
         (li "Hover over any word to see the root word it is derived from and the grammatical features of that word.")))
    (li (b "Links to dictionary for individual words:")
        (ul
         (li "Click on linked root word and / or the declined word itself in the grammatical analysis pop up to see the definition of the word on " (a {:href "https://www.diki.pl/" :target "_blank"} "diki.pl") "."))))))
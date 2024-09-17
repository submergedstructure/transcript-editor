(ns com.submerged-structure.app-help
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [h2 h3 h3 div li p ul b a]]
            [com.fulcrologic.semantic-ui.collections.message.ui-message-header :refer [ui-message-header]]
            [com.fulcrologic.fulcro.components :as comp :refer [fragment]]))


(def app-help
  (fragment
   (h2 "How to use this player")
   (p "As the audio plays you will see the currently spoken word is highlighed.")
   (h3 "Several transcripts available: ")
   (p "At the very top of the page the heading with the transcript name is actually a dropdown menu, you can use this to switch between different transcripts.")

   (h3 "Choose from several formatting options, by clicking on the tabs direcly below this message:")
   (div
        (p (b "\"Plain Transcript - No Coloring\"") " for no formatting. Click on any word to go to that word and begin playing.")
        (p (b "\"AI's Confidence of Each Word\"") " colors each word to show the AI's confidence of the transcription of that word. Click on any word to go to that word and begin playing.")
        (p (b "\"Grammar X-Ray\"") " formats text to show the grammatical features of each word (the default option). Click on any word for more info."))
   (h3 "Playback control: ")
   (div
    (p "Hover over playback control buttons below the waveform with your cursor for more information about what they do.")
    (p "When not in the \"Grammar X-Ray\" view, you can click on any word in the transcript to skip forward or backward to that word and begin playing.")
    (p "You can also click on the audio waveform itself to skip to that point in the audio.")
    (p "The top of the waveform visualisation is a zoomed in view of the currently playing audio. Below this is a zoomed out view of the entire audio. You can click on either to skip to that point in the audio."))
   (h3 "Translations: ")
   (div
        (p "Click on the button next to a sentence to see a translation of that sentence. You can also click on the translation to hide it again.")
        (p "You can also click on the button to the right of the player controls to show or hide all translations at once."))
   (h3 "Grammar Analysis:")
   (p "When in the \"Grammar X-Ray\" view you can click any word to see the root word it is derived from, what class of word it is and the grammatical features of that word.")
   (h3 "Links to dictionary for individual words:")
   (p "Click on linked root word and / or the declined word itself in the grammatical analysis pop up to see the definition of the word on " (a {:href "https://www.diki.pl/" :target "_blank"} "Diki.pl") ".")
   (h3 "Works on moble devices:")
   (p "This player is designed to work on mobile devices as well as desktops. On devices with touch screens clicking on a word shows the grammatical analysis pop up, rather than playing the transcript from that point on.")))
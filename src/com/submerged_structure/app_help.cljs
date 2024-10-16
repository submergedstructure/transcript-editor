(ns com.submerged-structure.app-help
  (:require [com.fulcrologic.fulcro.dom :as dom  :refer [h2 h3 h3 div p b a]]
            [com.fulcrologic.fulcro.components :as comp :refer [fragment]]))


(def app-help
  (fragment
   (h2 "How to use this player")
   (p "As the audio plays you will see the currently spoken word is highlighted.")
   
   (h3 "Choose from several formatting options, by clicking on the tabs direcly below this message:")
   (div
        (p (b "\"Plain Transcript - No Coloring\"") " for no formatting. Click on any word to go to that word and begin playing.")
        (p (b "\"AI's Confidence of Each Word\"") " colors each word to show the AI's confidence of the transcription of that word. Click on any word to go to that word and begin playing.")
        (p (b "\"Grammar X-Ray\"") " (the default option) colours text to show the most important grammatical features of each word. Click on any word for more info."))
   (h3 "Playback control: ")
   (div
    (p "Hover over playback control buttons below the waveform with your cursor for more information about what they do.")
    (p "You can also click on the audio waveform itself to skip to that point in the audio.")
    (p "The top of the waveform visualisation is a zoomed in view of the currently playing audio. Below this is a zoomed out view of the entire audio. You can click on either to skip to that point in the audio.")
    (dom/h4 "Keyboard Shortcuts:")
    (dom/ul
     (dom/li (dom/strong "a") " = back to beginning of sentence, repeatedly press to go to previous sentences.")
     (dom/li (dom/strong "s") " = pause if playing; and play if paused.")
     (dom/li (dom/strong "d") " = next sentence, repeatedly press to skip forward more than one sentence.")
     (dom/li (dom/strong "w") " = toggle autopause after every sentence.")))
   (h3 "Translations:")
   (div
        (p "Click on the grey button next to a sentence to see a translation of that sentence. You can also click on the translation to hide it again.")
        (p "You can also click on the button to the right of the player controls to show or hide all translations at once."))
   (h3 "Grammar Analysis:")
   (p "Click any word to see the grammatical features of that word.")
   (h3 "Links to dictionary for definitions of individual words:")
   (p "Click on linked root word and / or the declined word itself in the grammatical analysis pop up to see the definition of the word on " (a {:href "https://www.diki.pl/" :target "_blank"} "Diki.pl") ".")
   (h3 "Works on moble devices:")
   (p "Care has been taken to make the interface work well on mobile and touch screen devices as well as in your desktop browser.")))
(ns transcript-hard-coded-data)

(def audio-url->transcript-data
  {"/audio_and_transcript/090C-DailyPolishStory-QA.mp3" {"id" "b9c8eb5d-b7f8-4ce9-ae33-b19317380165"},
   "/audio_and_transcript/realpolish-hint1.mp3" {"id" "088fa835-c6cc-43a4-9feb-f330d5897b41"},
   "/audio_and_transcript/Freediving.mp3"
   {"id" "a0f1cee2-b66a-4a7f-afd8-e6b362724c5d"
    "label" "July 2018 TED Talk \"Freediving - control your emotions | Mateusz Malina | TEDxWSB\"."
    "url" "https://www.youtube.com/watch?v=z5Vlh1VfuJE"
    "summary" "Freediving is a way to control emotions and stress. How much does our heart slow down underwater and how can we use sport to relieve everyday stress? Mateusz is a 31 years old, multiple champion and world record holder in Freediving (diving while holding your breath). His deepest dive was 114 meters, the longest breath he held for 10 minutes, he set a world record in the swimming pool by swimming underwater in a monofin across 6 Olympic pools. Sport is his whole life."},
   "/audio_and_transcript/091C-DailyPolishStory-QA.mp3" {"id" "8159973c-d58a-4a3d-82fd-d3e27deb73b3"}})


(comment audio-url->transcript-data
         (def audio-url "/audio_and_transcript/Freediving.mp3")
         (merge {} (get audio-url->transcript-data audio-url)))
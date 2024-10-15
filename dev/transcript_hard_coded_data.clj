(ns transcript-hard-coded-data)

(def audio-url->transcript-data
  {"/audio_and_transcript/090C-DailyPolishStory-QA.mp3" {"id" "b9c8eb5d-b7f8-4ce9-ae33-b19317380165"},
   "/audio_and_transcript/realpolish-hint1.mp3" {"id" "088fa835-c6cc-43a4-9feb-f330d5897b41"},
   "/audio_and_transcript/Freediving.mp3"
   {"id" "a0f1cee2-b66a-4a7f-afd8-e6b362724c5d"
    "label" "July 2018 TED Talk \"Freediving - tame one's emotions | Mateusz Malina | TEDxWSB\"."
    "label-pl" "July 2018 TED Talk \"Freediving - zapanować nad emocjami | Mateusz Malina | TEDxWSB\"."
    "url" "https://www.youtube.com/watch?v=z5Vlh1VfuJE"
    "summary" (str "Mateusz Malina, world champion and record holder in freediving, discusses how freediving helps him manage emotions rather than focusing on records or achievements. In his talk, he explains the physiological response to stress, such as increased heart rate, and how the body's amygdala reacts instinctively. By holding his breath and submerging in water, Malina demonstrates how freediving reduces heart rate and brings mental calmness. This meditative aspect, he says, allows him to dive deep into his thoughts and emotions, pushing beyond stress and distractions. Freediving, for him, is a journey of self-discovery, helping him confront and control his emotions in all aspects of life.\n"
              "Malina also highlights the mental state of \"flow,\" where the rational mind is quieted, allowing athletes, artists, and creators to perform at their best. He recalls his experiences of deep dives, comparing them to floating in space, where senses become heightened, and the body feels weightless.")},
    "summary-pl" (str "Mateusz Malina, wielokrotny mistrz i rekordzista świata we freedivingu, opowiada, jak nurkowanie swobodne pomaga mu zarządzać emocjami, zamiast skupiać się na wynikach czy osiągnięciach. W swoim wystąpieniu wyjaśnia, jak ciało reaguje na stres, zwiększając tętno, i jak ciało migdałowate działa instynktownie. Podczas demonstracji wstrzymywania oddechu i zanurzenia twarzy w wodzie pokazuje, jak freediving spowalnia tętno i wprowadza spokój umysłu. To medytacyjne doświadczenie pozwala mu zanurzać się w swoich myślach i emocjach, przekraczając stres i zakłócenia. Freediving jest dla niego podróżą w głąb siebie, pomagającą mu mierzyć się z emocjami w różnych aspektach życia.\n"
                      "Malina podkreśla również stan \"flow\", w którym wycisza się racjonalny umysł, co pozwala sportowcom, artystom i twórcom osiągać najlepsze wyniki. Wspomina swoje głębokie nurkowania, porównując je do unoszenia się w kosmosie, gdzie zmysły są wyostrzone, a ciało wydaje się bezwładne.")
   "/audio_and_transcript/091C-DailyPolishStory-QA.mp3" {"id" "8159973c-d58a-4a3d-82fd-d3e27deb73b3"}})


(comment audio-url->transcript-data
         (def audio-url "/audio_and_transcript/Freediving.mp3")
         (merge {} (get audio-url->transcript-data audio-url)))
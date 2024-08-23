(ns example-tree)

(def tree #:root{:current-transcript
                 {:transcript/id "f7017546-4a9e-4529-930f-e1f9e1bbe8c4"
                  :transcript/label "realpolish-hint1.mp3"
                  :ui/help-hidden false
                  :ui-player/scroll-to-active true
                  :transcript/segments
                  [#:segment{:id "f89cc790-3017-4ac9-a1a4-2ce8720032d7"
                             :start 0.2
                             :end 2.2600000000000002
                             :text "Cześć, co słychać?"
                             :words
                             [#:word{:start      0.2
                                     :is_morphed false
                                     :active     false
                                     :id         "4f2191cc-4919-4223-be77-80922ee72999"
                                     :word       "Cześć,"
                                     :score      0.73
                                     :lemma      "cześć"
                                     :norm       "cześć"
                                     :morph      ""
                                     :end        1.18
                                     :pos        "INTJ"}
                              #:word{:start 1.18
                                     :is_morphed false
                                     :active false
                                     :id "3b41d4e8-15d2-45ff-8270-3b67f2137326"
                                     :word "co"
                                     :score 0.97
                                     :lemma "co"
                                     :norm "co"
                                     :morph
                                     "Case=Acc|Gender=Neut|Number=Sing|PronType=Int"
                                     :end 1.18
                                     :pos "PRON"}
                              #:word{:start 1.18
                                     :is_morphed false
                                     :active false
                                     :id "ef97c0cf-0539-4cdf-ab4c-578995736cfa"
                                     :word "słychać?"
                                     :score 0.68
                                     :lemma "słychać"
                                     :norm "słychać"
                                     :morph
                                     "Mood=Ind|Tense=Pres|VerbForm=Fin|VerbType=Quasi"
                                     :end 2.2600000000000002
                                     :pos "VERB"}]
                             :translations
                             [#:translation{:id "305e4d9a-91fa-4235-bb2c-1072f2945f36"
                                            :text "Hi, what's up?"
                                            :start 0.2
                                            :end 2.06
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "b91e3f88-c633-4c7a-b681-b37f6f7bf24b"
                             :start 2.54
                             :end 5.9
                             :text "Mam nadzieję, że nauka polskiego idzie dobrze."
                             :words
                             [#:word{:start 2.54
                                     :is_morphed true
                                     :active false
                                     :id "518d452a-6648-418f-979c-492c01d69b41"
                                     :word "Mam"
                                     :score 0.98
                                     :lemma "mieć"
                                     :norm "mam"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=1|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 2.54
                                     :pos "VERB"}
                              #:word{:start      2.54
                                     :is_morphed true
                                     :active     false
                                     :id         "d3b42191-35f0-4d91-900a-81d97ccef499"
                                     :word       "nadzieję,"
                                     :score      0.96
                                     :lemma      "nadzieja"
                                     :norm       "nadzieję"
                                     :morph      "Case=Acc|Gender=Fem|Number=Sing"
                                     :end        3.12
                                     :pos        "NOUN"}
                              #:word{:start      3.12
                                     :is_morphed false
                                     :active     false
                                     :id         "b8f0237c-a265-4318-ad44-043a78599cba"
                                     :word       "że"
                                     :score      1
                                     :lemma      "że"
                                     :norm       "że"
                                     :morph      ""
                                     :end        3.52
                                     :pos        "SCONJ"}
                              #:word{:start      3.52
                                     :is_morphed false
                                     :active     false
                                     :id         "ed7029dd-c39f-4ae3-a130-17a2c58a48ee"
                                     :word       "nauka"
                                     :score      0.73
                                     :lemma      "nauka"
                                     :norm       "nauka"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        3.88
                                     :pos        "NOUN"}
                              #:word{:start 3.88
                                     :is_morphed true
                                     :active false
                                     :id "4cc4777d-7d98-4ae9-9729-362ea9425b68"
                                     :word "polskiego"
                                     :score 0.7
                                     :lemma "polski"
                                     :norm "polskiego"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 4.5200000000000005
                                     :pos "ADJ"}
                              #:word{:start 4.5200000000000005
                                     :is_morphed true
                                     :active false
                                     :id "5d2bc054-b117-4292-b9bd-b91741de17a9"
                                     :word "idzie"
                                     :score 0.84
                                     :lemma "iść"
                                     :norm "idzie"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 4.840000000000001
                                     :pos "VERB"}
                              #:word{:start      4.840000000000001
                                     :is_morphed false
                                     :active     false
                                     :id         "3e4db007-ddb4-450d-a0b6-8c159b37b2af"
                                     :word       "dobrze."
                                     :score      0.74
                                     :lemma      "dobrze"
                                     :norm       "dobrze"
                                     :morph      "Degree=Pos"
                                     :end        5.9
                                     :pos        "ADV"}]
                             :translations
                             [#:translation{:id "e49b7b97-b279-4ea1-b66e-4d316c50dd31"
                                            :text
                                            "I hope that Polish learning is going well."
                                            :start 2.3400000000000003
                                            :end 5.78
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "5de2b560-a63a-43c8-b9c5-db8e1f843fab"
                             :start 6.459999999999999
                             :end 12.1
                             :text
                             "Właśnie napisałem krótki artykuł o szybkiej metodzie nauki."
                             :words
                             [#:word{:start      6.459999999999999
                                     :is_morphed false
                                     :active     false
                                     :id         "33584d59-a249-4cce-b4a7-209fc4bff8df"
                                     :word       "Właśnie"
                                     :score      0.78
                                     :lemma      "właśnie"
                                     :norm       "właśnie"
                                     :morph      ""
                                     :end        7.08
                                     :pos        "ADV"}
                              #:word{:start 7.08
                                     :is_morphed true
                                     :active false
                                     :id "5f57a56f-fde2-414a-862b-5f151a385ef4"
                                     :word "napisałem"
                                     :score 0.9
                                     :lemma "napisać być"
                                     :norm "napisałem"
                                     :morph
                                     "Animacy=Hum|Aspect=Imp,Perf|Clitic=Yes|Gender=Masc|Mood=Ind|Number=Sing|Person=1|Tense=Past|Variant=Long|VerbForm=Fin|Voice=Act"
                                     :end 7.9
                                     :pos "VERB"}
                              #:word{:start 7.9
                                     :is_morphed false
                                     :active false
                                     :id "cbfcc2eb-8600-47e8-a147-6a22e19986bf"
                                     :word "krótki"
                                     :score 0.97
                                     :lemma "krótki"
                                     :norm "krótki"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 8.54
                                     :pos "ADJ"}
                              #:word{:start 8.54
                                     :is_morphed false
                                     :active false
                                     :id "88d59afb-534e-49b9-8aeb-f23add384c64"
                                     :word "artykuł"
                                     :score 0.87
                                     :lemma "artykuł"
                                     :norm "artykuł"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing"
                                     :end 9.76
                                     :pos "NOUN"}
                              #:word{:start      9.76
                                     :is_morphed false
                                     :active     false
                                     :id         "da6f990b-3cfc-4756-a1b2-509429c9d8e4"
                                     :word       "o"
                                     :score      0
                                     :lemma      "o"
                                     :norm       "o"
                                     :morph      "AdpType=Prep"
                                     :end        9.86
                                     :pos        "ADP"}
                              #:word{:start 9.86
                                     :is_morphed true
                                     :active false
                                     :id "33dab881-6a28-4601-af47-6c9055463273"
                                     :word "szybkiej"
                                     :score 0.56
                                     :lemma "szybki"
                                     :norm "szybkiej"
                                     :morph "Case=Loc|Degree=Pos|Gender=Fem|Number=Sing"
                                     :end 10.219999999999999
                                     :pos "ADJ"}
                              #:word{:start      10.219999999999999
                                     :is_morphed true
                                     :active     false
                                     :id         "694e2093-0bca-4304-aed5-514c6eef4efb"
                                     :word       "metodzie"
                                     :score      0.92
                                     :lemma      "metoda"
                                     :norm       "metodzie"
                                     :morph      "Case=Loc|Gender=Fem|Number=Sing"
                                     :end        10.68
                                     :pos        "NOUN"}
                              #:word{:start      10.68
                                     :is_morphed true
                                     :active     false
                                     :id         "784eae0c-1c8b-421f-a156-349f9fe27ab5"
                                     :word       "nauki."
                                     :score      0.92
                                     :lemma      "nauka"
                                     :norm       "nauki"
                                     :morph      "Case=Gen|Gender=Fem|Number=Sing"
                                     :end        12.1
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "b3df3128-c8ac-45d8-81ae-b8604f3a3887"
                                            :text
                                            "I just wrote a short article about the quick method of learning."
                                            :start 6.34
                                            :end 11.68
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "27768bb3-acab-4af2-b964-c11407c15c8a"
                             :start 12.46
                             :end 30.220000000000002
                             :text
                             "Oczywiście ta metoda nie jest dobra dla wszystkich, ale jeśli musisz poprawić swój polski bardzo szybko, chcesz mieć duży postęp w krótkim czasie, od miesiąca do trzech miesięcy, posłuchaj, a potem przeczytaj mój artykuł."
                             :words
                             [#:word{:start      12.46
                                     :is_morphed false
                                     :active     false
                                     :id         "cce016a0-343f-4060-9a72-63cfe010d24f"
                                     :word       "Oczywiście"
                                     :score      0.96
                                     :lemma      "oczywiście"
                                     :norm       "oczywiście"
                                     :morph      "Degree=Pos"
                                     :end        12.56
                                     :pos        "ADV"}
                              #:word{:start 12.56
                                     :is_morphed true
                                     :active false
                                     :id "69875279-8844-4c8d-95e1-22a4c1df1da5"
                                     :word "ta"
                                     :score 0.07
                                     :lemma "ten"
                                     :norm "ta"
                                     :morph "Case=Nom|Gender=Fem|Number=Sing|PronType=Dem"
                                     :end 13.14
                                     :pos "DET"}
                              #:word{:start      13.14
                                     :is_morphed false
                                     :active     false
                                     :id         "f12982ce-06a5-475b-96b6-a6acc99e065b"
                                     :word       "metoda"
                                     :score      0.96
                                     :lemma      "metoda"
                                     :norm       "metoda"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        13.6
                                     :pos        "NOUN"}
                              #:word{:start      13.6
                                     :is_morphed false
                                     :active     false
                                     :id         "b4f7f83a-eb1b-425b-89f1-a2e8eb5e9974"
                                     :word       "nie"
                                     :score      0.93
                                     :lemma      "nie"
                                     :norm       "nie"
                                     :morph      "Polarity=Neg"
                                     :end        13.98
                                     :pos        "PART"}
                              #:word{:start 13.98
                                     :is_morphed true
                                     :active false
                                     :id "051478f2-f801-4c77-8a79-f55e7d5f3f79"
                                     :word "jest"
                                     :score 1
                                     :lemma "być"
                                     :norm "jest"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 14.16
                                     :pos "AUX"}
                              #:word{:start 14.16
                                     :is_morphed true
                                     :active false
                                     :id "bdf79f5f-916d-48ac-a57b-393200f2cc31"
                                     :word "dobra"
                                     :score 0.66
                                     :lemma "dobry"
                                     :norm "dobra"
                                     :morph "Case=Nom|Degree=Pos|Gender=Fem|Number=Sing"
                                     :end 14.46
                                     :pos "ADJ"}
                              #:word{:start      14.46
                                     :is_morphed false
                                     :active     false
                                     :id         "5f19d52d-65e2-4fc8-8ad8-9e456c81f95e"
                                     :word       "dla"
                                     :score      1
                                     :lemma      "dla"
                                     :norm       "dla"
                                     :morph      "AdpType=Prep"
                                     :end        14.66
                                     :pos        "ADP"}
                              #:word{:start 14.66
                                     :is_morphed true
                                     :active false
                                     :id "0be65bd1-e3d5-4276-9f92-7a4de46480af"
                                     :word "wszystkich,"
                                     :score 0.79
                                     :lemma "wszyscy"
                                     :norm "wszystkich"
                                     :morph
                                     "Animacy=Hum|Case=Gen|Gender=Masc|Number=Ptan|PronType=Tot"
                                     :end 15.66
                                     :pos "PRON"}
                              #:word{:start      16.18
                                     :is_morphed false
                                     :active     false
                                     :id         "a9626a06-b1b9-4338-b271-a3d6f31bcb65"
                                     :word       "ale"
                                     :score      0.99
                                     :lemma      "ale"
                                     :norm       "ale"
                                     :morph      ""
                                     :end        16.32
                                     :pos        "CCONJ"}
                              #:word{:start      16.32
                                     :is_morphed false
                                     :active     false
                                     :id         "bf0843b8-d995-42ae-9337-25546488de2f"
                                     :word       "jeśli"
                                     :score      0.97
                                     :lemma      "jeśli"
                                     :norm       "jeśli"
                                     :morph      ""
                                     :end        16.68
                                     :pos        "SCONJ"}
                              #:word{:start 16.68
                                     :is_morphed true
                                     :active false
                                     :id "d8a9121b-0ec9-42dc-85f5-9e3c1bbdd35b"
                                     :word "musisz"
                                     :score 0.91
                                     :lemma "musieć"
                                     :norm "musisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 17.32
                                     :pos "VERB"}
                              #:word{:start      17.32
                                     :is_morphed false
                                     :active     false
                                     :id         "2e9ba408-26b2-4b43-a391-9b66e802d4ef"
                                     :word       "poprawić"
                                     :score      0.93
                                     :lemma      "poprawić"
                                     :norm       "poprawić"
                                     :morph      "Aspect=Perf|VerbForm=Inf|Voice=Act"
                                     :end        17.880000000000003
                                     :pos        "VERB"}
                              #:word{:start 17.880000000000003
                                     :is_morphed false
                                     :active false
                                     :id "8ded29af-aed6-4a05-bafe-c1dd8b2f6f97"
                                     :word "swój"
                                     :score 0.9
                                     :lemma "swój"
                                     :norm "swój"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing|Poss=Yes|PronType=Prs|Reflex=Yes"
                                     :end 18.200000000000003
                                     :pos "DET"}
                              #:word{:start 18.200000000000003
                                     :is_morphed false
                                     :active false
                                     :id "c2ba0071-cb52-4e19-a8f2-07064e47943a"
                                     :word "polski"
                                     :score 0.52
                                     :lemma "polski"
                                     :norm "polski"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 18.92
                                     :pos "ADJ"}
                              #:word{:start      18.92
                                     :is_morphed false
                                     :active     false
                                     :id         "93e076b6-3ecb-4ee9-9952-f8f7b677b567"
                                     :word       "bardzo"
                                     :score      0.22
                                     :lemma      "bardzo"
                                     :norm       "bardzo"
                                     :morph      "Degree=Pos"
                                     :end        19.5
                                     :pos        "ADV"}
                              #:word{:start      19.5
                                     :is_morphed false
                                     :active     false
                                     :id         "578e284f-5fd7-4614-874e-ffb25c093571"
                                     :word       "szybko,"
                                     :score      0.77
                                     :lemma      "szybko"
                                     :norm       "szybko"
                                     :morph      "Degree=Pos"
                                     :end        20.720000000000002
                                     :pos        "ADV"}
                              #:word{:start 20.720000000000002
                                     :is_morphed true
                                     :active false
                                     :id "e9ee1019-5a76-4b76-a9f5-632ddfe85e39"
                                     :word "chcesz"
                                     :score 0.86
                                     :lemma "chcieć"
                                     :norm "chcesz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 21.020000000000003
                                     :pos "VERB"}
                              #:word{:start      21.020000000000003
                                     :is_morphed false
                                     :active     false
                                     :id         "3e155c2c-ea75-4192-8485-15c9007ffe1f"
                                     :word       "mieć"
                                     :score      0.94
                                     :lemma      "mieć"
                                     :norm       "mieć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        21.580000000000002
                                     :pos        "VERB"}
                              #:word{:start 21.580000000000002
                                     :is_morphed false
                                     :active false
                                     :id "4600e7d1-5ca2-4913-ae58-a3db035d5129"
                                     :word "duży"
                                     :score 0.85
                                     :lemma "duży"
                                     :norm "duży"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 22.12
                                     :pos "ADJ"}
                              #:word{:start 22.12
                                     :is_morphed false
                                     :active false
                                     :id "821f70d5-48ad-4a55-ba14-ad3fe9690462"
                                     :word "postęp"
                                     :score 0.61
                                     :lemma "postęp"
                                     :norm "postęp"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing"
                                     :end 22.840000000000003
                                     :pos "NOUN"}
                              #:word{:start      22.840000000000003
                                     :is_morphed false
                                     :active     false
                                     :id         "ed890aab-4446-447c-94ff-29f956bd9b24"
                                     :word       "w"
                                     :score      0.94
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        23
                                     :pos        "ADP"}
                              #:word{:start 23
                                     :is_morphed true
                                     :active false
                                     :id "229ba6b7-e637-4ae3-891b-237ff7e96701"
                                     :word "krótkim"
                                     :score 0.98
                                     :lemma "krótki"
                                     :norm "krótkim"
                                     :morph
                                     "Animacy=Inan|Case=Loc|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 23.44
                                     :pos "ADJ"}
                              #:word{:start 23.44
                                     :is_morphed true
                                     :active false
                                     :id "a242ecf7-bb06-4351-b53a-09278701a7bd"
                                     :word "czasie,"
                                     :score 0.54
                                     :lemma "czas"
                                     :norm "czasie"
                                     :morph
                                     "Animacy=Inan|Case=Loc|Gender=Masc|Number=Sing"
                                     :end 24.380000000000003
                                     :pos "NOUN"}
                              #:word{:start      24.380000000000003
                                     :is_morphed false
                                     :active     false
                                     :id         "55695867-37c8-473e-beef-09bdfbdf8221"
                                     :word       "od"
                                     :score      0.98
                                     :lemma      "od"
                                     :norm       "od"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        24.6
                                     :pos        "ADP"}
                              #:word{:start 24.6
                                     :is_morphed true
                                     :active false
                                     :id "f5eba653-2979-4319-8ba8-71257c1c7597"
                                     :word "miesiąca"
                                     :score 0.96
                                     :lemma "miesiąc"
                                     :norm "miesiąca"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Gender=Masc|Number=Sing"
                                     :end 25.16
                                     :pos "NOUN"}
                              #:word{:start      25.16
                                     :is_morphed false
                                     :active     false
                                     :id         "c2e5ac1d-b8e8-4b9a-b8fa-adb649702a9c"
                                     :word       "do"
                                     :score      0.83
                                     :lemma      "do"
                                     :norm       "do"
                                     :morph      "AdpType=Prep"
                                     :end        25.48
                                     :pos        "ADP"}
                              #:word{:start 25.48
                                     :is_morphed true
                                     :active false
                                     :id "37f1395d-4c82-423e-87f3-c7c81cf5c49a"
                                     :word "trzech"
                                     :score 0.55
                                     :lemma "trzy"
                                     :norm "trzech"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Gender=Masc|NumForm=Word|Number=Plur"
                                     :end 25.8
                                     :pos "NUM"}
                              #:word{:start 25.8
                                     :is_morphed true
                                     :active false
                                     :id "2c032260-77fe-472d-a2ed-0ed5cc857b7e"
                                     :word "miesięcy,"
                                     :score 0.72
                                     :lemma "miesiąc"
                                     :norm "miesięcy"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Gender=Masc|Number=Plur"
                                     :end 26.92
                                     :pos "NOUN"}
                              #:word{:start 27.240000000000002
                                     :is_morphed true
                                     :active false
                                     :id "56da9852-57fc-4c0c-af9a-6980e87ed118"
                                     :word "posłuchaj,"
                                     :score 0.83
                                     :lemma "posłuchać"
                                     :norm "posłuchaj"
                                     :morph
                                     "Aspect=Perf|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 28.080000000000002
                                     :pos "VERB"}
                              #:word{:start      28.080000000000002
                                     :is_morphed false
                                     :active     false
                                     :id         "8c1a734b-6dd4-4d03-84a5-e2584b97eec6"
                                     :word       "a"
                                     :score      0.98
                                     :lemma      "a"
                                     :norm       "a"
                                     :morph      ""
                                     :end        28.12
                                     :pos        "CCONJ"}
                              #:word{:start      28.12
                                     :is_morphed false
                                     :active     false
                                     :id         "1b157684-f192-40c1-8ec1-ef148ce767e3"
                                     :word       "potem"
                                     :score      0.99
                                     :lemma      "potem"
                                     :norm       "potem"
                                     :morph      ""
                                     :end        28.680000000000003
                                     :pos        "ADV"}
                              #:word{:start 28.680000000000003
                                     :is_morphed true
                                     :active false
                                     :id "07e1ce17-d8af-4a8a-9e1f-a3aa3ec6e490"
                                     :word "przeczytaj"
                                     :score 0.84
                                     :lemma "przeczytać"
                                     :norm "przeczytaj"
                                     :morph
                                     "Aspect=Perf|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 29.44
                                     :pos "VERB"}
                              #:word{:start 29.44
                                     :is_morphed false
                                     :active false
                                     :id "39b42917-c09c-499f-83a8-7f4ed915d314"
                                     :word "mój"
                                     :score 0.49
                                     :lemma "mój"
                                     :norm "mój"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing|Number[psor]=Sing|Person=1|Poss=Yes|PronType=Prs"
                                     :end 29.6
                                     :pos "DET"}
                              #:word{:start 29.6
                                     :is_morphed false
                                     :active false
                                     :id "67290f1f-354b-4629-bc00-a863655c9883"
                                     :word "artykuł."
                                     :score 0.99
                                     :lemma "artykuł"
                                     :norm "artykuł"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing"
                                     :end 30.220000000000002
                                     :pos "NOUN"}]
                             :translations
                             [#:translation{:id "13935706-e67c-4bfd-afbb-d05fe6ae1a8b"
                                            :text
                                            "But if you have to improve your Polish very quickly, you want to make big progress in a short time, from a month to three months, listen and then read my article."
                                            :start 16.12
                                            :end 30.040000000000003
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "42ae59af-e48d-4252-8947-ff6685c86be8"
                             :start 31
                             :end 34.16
                             :text "Jestem ciekaw, co o tym myślisz."
                             :words
                             [#:word{:start 31
                                     :is_morphed true
                                     :active false
                                     :id "93e8bbe2-71c8-4092-b172-8a3eddd21f7f"
                                     :word "Jestem"
                                     :score 0.84
                                     :lemma "być"
                                     :norm "jestem"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=1|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 31.7
                                     :pos "AUX"}
                              #:word{:start      31.7
                                     :is_morphed false
                                     :active     false
                                     :id         "a44b0ffd-e101-4ad2-b46d-bb7dff3861cf"
                                     :word       "ciekaw,"
                                     :score      0.61
                                     :lemma      "ciekaw"
                                     :norm       "ciekaw"
                                     :morph      "Variant=Short"
                                     :end        32.56
                                     :pos        "ADJ"}
                              #:word{:start 32.56
                                     :is_morphed false
                                     :active false
                                     :id "f4dbb122-4a52-464b-98db-f473dc35312d"
                                     :word "co"
                                     :score 0.73
                                     :lemma "co"
                                     :norm "co"
                                     :morph
                                     "Case=Acc|Gender=Neut|Number=Sing|PronType=Int"
                                     :end 32.74
                                     :pos "PRON"}
                              #:word{:start      32.74
                                     :is_morphed false
                                     :active     false
                                     :id         "8f6e103d-eacf-4ca5-b95d-d495092ee67a"
                                     :word       "o"
                                     :score      0.03
                                     :lemma      "o"
                                     :norm       "o"
                                     :morph      "AdpType=Prep"
                                     :end        32.9
                                     :pos        "ADP"}
                              #:word{:start 32.9
                                     :is_morphed true
                                     :active false
                                     :id "5cfb5b3e-0550-4e21-b0ed-daac473f76e4"
                                     :word "tym"
                                     :score 1
                                     :lemma "to"
                                     :norm "tym"
                                     :morph
                                     "Case=Loc|Gender=Neut|Number=Sing|PronType=Dem"
                                     :end 33.02
                                     :pos "PRON"}
                              #:word{:start 33.02
                                     :is_morphed true
                                     :active false
                                     :id "96dc8133-15cc-4d92-91bd-002a20e3b54c"
                                     :word "myślisz."
                                     :score 0.7
                                     :lemma "myśleć"
                                     :norm "myślisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 34.16
                                     :pos "VERB"}]
                             :translations
                             [#:translation{:id "3a0e1885-8380-417f-a468-c8ab7d0f4b21"
                                            :text "I'm curious what you think about it."
                                            :start 31
                                            :end 34.16
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "be354b63-8705-4b2f-9d9e-a40255deee06"
                             :start 34.16
                             :end 35.42
                             :text "Napisz do mnie."
                             :words
                             [#:word{:start 34.16
                                     :is_morphed false
                                     :active false
                                     :id "a15d28d2-75be-4056-a3a0-0b9c6adea090"
                                     :word "Napisz"
                                     :score 0.9
                                     :lemma "Napisz"
                                     :norm "napisz"
                                     :morph
                                     "Aspect=Perf|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 34.54
                                     :pos "VERB"}
                              #:word{:start      34.54
                                     :is_morphed false
                                     :active     false
                                     :id         "2609b1db-168e-4041-aaeb-4b12db399938"
                                     :word       "do"
                                     :score      0.68
                                     :lemma      "do"
                                     :norm       "do"
                                     :morph      "AdpType=Prep"
                                     :end        34.76
                                     :pos        "ADP"}
                              #:word{:start 34.76
                                     :is_morphed true
                                     :active false
                                     :id "99695d87-8a62-4c0b-b7b6-ae414bc7ceb4"
                                     :word "mnie."
                                     :score 0.74
                                     :lemma "ja"
                                     :norm "mnie"
                                     :morph
                                     "Animacy=Hum|Case=Gen|Gender=Masc|Number=Sing|Person=1|PronType=Prs|Variant=Long"
                                     :end 35.42
                                     :pos "PRON"}]
                             :translations
                             [#:translation{:id "0f7c5d22-805a-4b55-b605-ee953bee05a0"
                                            :text "Write to me."
                                            :start 34.16
                                            :end 35.34
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "4ee8ef80-70b7-46c8-82fb-1802554e6df9"
                             :start 35.98
                             :end 37.12
                             :text "Ale do rzeczy."
                             :words [#:word{:start 35.98
                                            :is_morphed false
                                            :active false
                                            :id "492cd8c4-3dcb-4c53-92f0-0cfda628c90e"
                                            :word "Ale"
                                            :score 0.9
                                            :lemma "ale"
                                            :norm "ale"
                                            :morph ""
                                            :end 36.099999999999994
                                            :pos "CCONJ"}
                                     #:word{:start 36.099999999999994
                                            :is_morphed false
                                            :active false
                                            :id "f1c593d2-75ce-4a10-bd02-0bd63e628a5e"
                                            :word "do"
                                            :score 0.84
                                            :lemma "do"
                                            :norm "do"
                                            :morph "AdpType=Prep"
                                            :end 36.26
                                            :pos "ADP"}
                                     #:word{:start 36.26
                                            :is_morphed true
                                            :active false
                                            :id "5128f4d5-8f6e-427a-829a-c82634a0ce8a"
                                            :word "rzeczy."
                                            :score 0.65
                                            :lemma "rzecz"
                                            :norm "rzeczy"
                                            :morph "Case=Gen|Gender=Fem|Number=Sing"
                                            :end 37.12
                                            :pos "NOUN"}]
                             :translations
                             [#:translation{:id "2d5f145c-ceda-4a24-afda-ad5e6dd44385"
                                            :text "But to the point."
                                            :start 35.89999999999999
                                            :end 37.059999999999995
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "67240a65-b1b7-47b9-8537-629f696bbfdc"
                             :start 37.37999999999999
                             :end 38.539999999999985
                             :text "Posłuchaj."
                             :words
                             [#:word{:start 37.37999999999999
                                     :is_morphed true
                                     :active false
                                     :id "9471625a-204a-404e-9bd1-df16ba80cd97"
                                     :word "Posłuchaj."
                                     :score 0.79
                                     :lemma "posłuchać"
                                     :norm "posłuchaj"
                                     :morph
                                     "Aspect=Perf|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 38.539999999999985
                                     :pos "VERB"}]
                             :translations
                             [#:translation{:id "364a003f-9c72-45ea-9ca8-650caeef915e"
                                            :text "Listen."
                                            :start 37.319999999999986
                                            :end 38.499999999999986
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "c5d7cecf-1381-4514-8020-ffd8a600e0e1"
                             :start 38.539999999999985
                             :end 42.259999999999984
                             :text "Jak uczyć się języka polskiego bardzo szybko?"
                             :words
                             [#:word{:start      38.539999999999985
                                     :is_morphed false
                                     :active     false
                                     :id         "626eb00c-7b3b-47da-89ea-9c01e9893644"
                                     :word       "Jak"
                                     :score      0.96
                                     :lemma      "jak"
                                     :norm       "jak"
                                     :morph      "Degree=Pos|PronType=Int"
                                     :end        38.859999999999985
                                     :pos        "ADV"}
                              #:word{:start      38.859999999999985
                                     :is_morphed false
                                     :active     false
                                     :id         "8e01e01e-8976-42f0-9db2-452c1f5cf889"
                                     :word       "uczyć"
                                     :score      0.9
                                     :lemma      "uczyć"
                                     :norm       "uczyć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        39.319999999999986
                                     :pos        "VERB"}
                              #:word{:start      39.319999999999986
                                     :is_morphed false
                                     :active     false
                                     :id         "cab65907-4366-428a-90e1-3bb7bcd4ae7c"
                                     :word       "się"
                                     :score      0.99
                                     :lemma      "się"
                                     :norm       "się"
                                     :morph      "PronType=Prs|Reflex=Yes"
                                     :end        39.55999999999999
                                     :pos        "PRON"}
                              #:word{:start 39.55999999999999
                                     :is_morphed true
                                     :active false
                                     :id "5e57bb04-d36d-44d9-9ee4-20867ee9214f"
                                     :word "języka"
                                     :score 0.88
                                     :lemma "język"
                                     :norm "języka"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Gender=Masc|Number=Sing"
                                     :end 39.999999999999986
                                     :pos "NOUN"}
                              #:word{:start 39.999999999999986
                                     :is_morphed true
                                     :active false
                                     :id "b584449f-0ad7-4e74-b827-26499421b4f6"
                                     :word "polskiego"
                                     :score 0.93
                                     :lemma "polski"
                                     :norm "polskiego"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 40.79999999999998
                                     :pos "ADJ"}
                              #:word{:start      40.79999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "1fd77107-cc8f-4cfa-b0b7-58b5e474fd23"
                                     :word       "bardzo"
                                     :score      0.1
                                     :lemma      "bardzo"
                                     :norm       "bardzo"
                                     :morph      "Degree=Pos"
                                     :end        41.359999999999985
                                     :pos        "ADV"}
                              #:word{:start      41.359999999999985
                                     :is_morphed false
                                     :active     false
                                     :id         "ecefc5b0-cfe1-4ae8-8a9c-df724477f87b"
                                     :word       "szybko?"
                                     :score      0.87
                                     :lemma      "szybko"
                                     :norm       "szybko"
                                     :morph      "Degree=Pos"
                                     :end        42.259999999999984
                                     :pos        "ADV"}]
                             :translations
                             [#:translation{:id "5627f983-de11-4b26-9998-bdc54fd86047"
                                            :text "How to learn Polish very quickly?"
                                            :start 38.499999999999986
                                            :end 42.259999999999984
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "7defd57b-07ad-40c1-9e5b-110f1de9bf34"
                             :start 43.239999999999974
                             :end 48.81999999999997
                             :text
                             "Czasami ludzie pytają się, jak szybko mówić płynnie po polsku."
                             :words
                             [#:word{:start 43.239999999999974
                                     :is_morphed true
                                     :active false
                                     :id "78a2b1f4-b254-403b-8e01-ef99293f0866"
                                     :word "Czasami"
                                     :score 0.88
                                     :lemma "czas"
                                     :norm "czasami"
                                     :morph
                                     "Animacy=Inan|Case=Ins|Gender=Masc|Number=Plur"
                                     :end 43.77999999999997
                                     :pos "NOUN"}
                              #:word{:start 43.77999999999997
                                     :is_morphed true
                                     :active false
                                     :id "deff96c4-7d4a-4787-b20c-c023ebb24fc1"
                                     :word "ludzie"
                                     :score 1
                                     :lemma "człowiek"
                                     :norm "ludzie"
                                     :morph "Animacy=Hum|Case=Nom|Gender=Masc|Number=Plur"
                                     :end 44.09999999999998
                                     :pos "NOUN"}
                              #:word{:start 44.09999999999998
                                     :is_morphed true
                                     :active false
                                     :id "64c75f1a-fcbf-485d-ad7f-38b567e12095"
                                     :word "pytają"
                                     :score 0.91
                                     :lemma "pytać"
                                     :norm "pytają"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Plur|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 44.659999999999975
                                     :pos "VERB"}
                              #:word{:start      44.659999999999975
                                     :is_morphed false
                                     :active     false
                                     :id         "04f1eb57-8a70-4002-8894-0899c7274b91"
                                     :word       "się,"
                                     :score      0.79
                                     :lemma      "się"
                                     :norm       "się"
                                     :morph      "PronType=Prs|Reflex=Yes"
                                     :end        45.439999999999976
                                     :pos        "PRON"}
                              #:word{:start      45.89999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "afda1816-faf0-44ea-93da-3188131009fc"
                                     :word       "jak"
                                     :score      0.9
                                     :lemma      "jak"
                                     :norm       "jak"
                                     :morph      "Degree=Pos|PronType=Int"
                                     :end        46.15999999999997
                                     :pos        "ADV"}
                              #:word{:start      46.15999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "3a1b43ff-1ba2-4070-88f9-ec742c880055"
                                     :word       "szybko"
                                     :score      0.98
                                     :lemma      "szybko"
                                     :norm       "szybko"
                                     :morph      "Degree=Pos"
                                     :end        46.71999999999997
                                     :pos        "ADV"}
                              #:word{:start      46.71999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "3f01ebe7-3124-4c09-bf73-4b8061acad1d"
                                     :word       "mówić"
                                     :score      0.96
                                     :lemma      "mówić"
                                     :norm       "mówić"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        47.11999999999997
                                     :pos        "VERB"}
                              #:word{:start      47.11999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "afaa88ba-e14b-4ac5-93bf-f49af579a2c8"
                                     :word       "płynnie"
                                     :score      0.94
                                     :lemma      "płynnie"
                                     :norm       "płynnie"
                                     :morph      "Degree=Pos"
                                     :end        47.71999999999997
                                     :pos        "ADV"}
                              #:word{:start      47.71999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "c46fc489-3824-408b-8024-4f9c00ea029b"
                                     :word       "po"
                                     :score      0.82
                                     :lemma      "po"
                                     :norm       "po"
                                     :morph      "AdpType=Prep"
                                     :end        47.89999999999997
                                     :pos        "ADP"}
                              #:word{:start      47.89999999999997
                                     :is_morphed true
                                     :active     false
                                     :id         "4921de2d-5119-4a05-abf5-ec9d3050e229"
                                     :word       "polsku."
                                     :score      0.9
                                     :lemma      "polski"
                                     :norm       "polsku"
                                     :morph      "PrepCase=Pre"
                                     :end        48.81999999999997
                                     :pos        "ADJ"}]
                             :translations
                             [#:translation{:id "6730070d-3b76-44a1-a338-60add3c2ac4e"
                                            :text
                                            "Sometimes people ask how to speak Polish fluently quickly."
                                            :start 43.239999999999974
                                            :end 49.29999999999996
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "8fa49525-f640-4dfe-847f-0e6a7827ce1f"
                             :start 49.359999999999964
                             :end 57.89999999999996
                             :text
                             "Zwykle chcą poprawić swój polski, ponieważ jadą do Polski i chcą móc porozumiewać się po polsku."
                             :words
                             [#:word{:start      49.359999999999964
                                     :is_morphed false
                                     :active     false
                                     :id         "2ab2efc8-8dac-4f89-b3f7-5b782b0886f7"
                                     :word       "Zwykle"
                                     :score      0.95
                                     :lemma      "zwykle"
                                     :norm       "zwykle"
                                     :morph      "Degree=Pos"
                                     :end        50.13999999999996
                                     :pos        "ADV"}
                              #:word{:start 50.13999999999996
                                     :is_morphed true
                                     :active false
                                     :id "524cca66-c020-412a-b3fc-ec59bc6c6920"
                                     :word "chcą"
                                     :score 0.61
                                     :lemma "chcieć"
                                     :norm "chcą"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Plur|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 50.55999999999996
                                     :pos "VERB"}
                              #:word{:start      50.55999999999996
                                     :is_morphed false
                                     :active     false
                                     :id         "d168eed9-3e69-4be2-a583-39b898c69b09"
                                     :word       "poprawić"
                                     :score      0.86
                                     :lemma      "poprawić"
                                     :norm       "poprawić"
                                     :morph      "Aspect=Perf|VerbForm=Inf|Voice=Act"
                                     :end        51.179999999999964
                                     :pos        "VERB"}
                              #:word{:start 51.179999999999964
                                     :is_morphed false
                                     :active false
                                     :id "56c23b8a-a036-40a7-a60f-ad05adf616eb"
                                     :word "swój"
                                     :score 0.89
                                     :lemma "swój"
                                     :norm "swój"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing|Poss=Yes|PronType=Prs|Reflex=Yes"
                                     :end 51.49999999999996
                                     :pos "DET"}
                              #:word{:start 51.49999999999996
                                     :is_morphed false
                                     :active false
                                     :id "1d5c30de-446f-4ee0-a1b0-2c4878dbc6fb"
                                     :word "polski,"
                                     :score 0.44
                                     :lemma "polski"
                                     :norm "polski"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 52.21999999999996
                                     :pos "ADJ"}
                              #:word{:start      52.21999999999996
                                     :is_morphed false
                                     :active     false
                                     :id         "c81bde52-9717-437e-b0dd-0ed16ad21a7b"
                                     :word       "ponieważ"
                                     :score      0.98
                                     :lemma      "ponieważ"
                                     :norm       "ponieważ"
                                     :morph      ""
                                     :end        52.73999999999997
                                     :pos        "SCONJ"}
                              #:word{:start 52.73999999999997
                                     :is_morphed true
                                     :active false
                                     :id "45b1914e-bfa1-43ed-a022-3cc08c63c0e5"
                                     :word "jadą"
                                     :score 0.64
                                     :lemma "jechać"
                                     :norm "jadą"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Plur|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 53.63999999999996
                                     :pos "VERB"}
                              #:word{:start      53.63999999999996
                                     :is_morphed false
                                     :active     false
                                     :id         "d8808c3f-76b5-43df-bd82-09b43ad283f1"
                                     :word       "do"
                                     :score      0.97
                                     :lemma      "do"
                                     :norm       "do"
                                     :morph      "AdpType=Prep"
                                     :end        53.89999999999996
                                     :pos        "ADP"}
                              #:word{:start      53.89999999999996
                                     :is_morphed true
                                     :active     false
                                     :id         "8a6d561b-970f-4b0a-80b1-0baf0b9d0f0c"
                                     :word       "Polski"
                                     :score      0.57
                                     :lemma      "Polska"
                                     :norm       "polski"
                                     :morph      "Case=Gen|Gender=Fem|Number=Sing"
                                     :end        54.23999999999997
                                     :pos        "PROPN"}
                              #:word{:start      54.23999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "373f286d-a338-45af-bcdd-8a1a1dfcd97b"
                                     :word       "i"
                                     :score      0.45
                                     :lemma      "i"
                                     :norm       "i"
                                     :morph      ""
                                     :end        54.539999999999964
                                     :pos        "CCONJ"}
                              #:word{:start 54.539999999999964
                                     :is_morphed true
                                     :active false
                                     :id "6e49a927-ee50-45f1-b421-95275a0292a1"
                                     :word "chcą"
                                     :score 0.97
                                     :lemma "chcieć"
                                     :norm "chcą"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Plur|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 54.79999999999996
                                     :pos "VERB"}
                              #:word{:start      54.79999999999996
                                     :is_morphed false
                                     :active     false
                                     :id         "91e3aaf5-ecc3-4f10-b69a-829ec76dea02"
                                     :word       "móc"
                                     :score      0.51
                                     :lemma      "móc"
                                     :norm       "móc"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        55.49999999999996
                                     :pos        "VERB"}
                              #:word{:start      55.49999999999996
                                     :is_morphed false
                                     :active     false
                                     :id         "d3bd41b4-dc70-4dbd-ae55-fb2e8820bb44"
                                     :word       "porozumiewać"
                                     :score      0.79
                                     :lemma      "porozumiewać"
                                     :norm       "porozumiewać"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        56.39999999999996
                                     :pos        "VERB"}
                              #:word{:start      56.39999999999996
                                     :is_morphed false
                                     :active     false
                                     :id         "11187145-f818-43cd-bd2c-aa00f7941a68"
                                     :word       "się"
                                     :score      1
                                     :lemma      "się"
                                     :norm       "się"
                                     :morph      "PronType=Prs|Reflex=Yes"
                                     :end        56.63999999999996
                                     :pos        "PRON"}
                              #:word{:start      56.63999999999996
                                     :is_morphed false
                                     :active     false
                                     :id         "5898ea47-a56a-4b83-a245-8d60601d2907"
                                     :word       "po"
                                     :score      0.98
                                     :lemma      "po"
                                     :norm       "po"
                                     :morph      "AdpType=Prep"
                                     :end        56.73999999999997
                                     :pos        "ADP"}
                              #:word{:start      56.73999999999997
                                     :is_morphed true
                                     :active     false
                                     :id         "92963178-e783-49b8-b9fd-f2a05525e518"
                                     :word       "polsku."
                                     :score      0.91
                                     :lemma      "polski"
                                     :norm       "polsku"
                                     :morph      "PrepCase=Pre"
                                     :end        57.89999999999996
                                     :pos        "ADJ"}]
                             :translations
                             [#:translation{:id "fbd3d3e6-1004-4d77-9e82-31e6fd0ac4b2"
                                            :text
                                            "They usually want to improve their Polish because they go to Poland and want to be able to communicate in Polish."
                                            :start 49.29999999999996
                                            :end 57.71999999999996
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "e2af4832-c2e8-40d0-883c-95df24cf85a9"
                             :start 57.89999999999996
                             :end 61.49999999999996
                             :text "Chcą rozumieć, co mówią do nich Polacy."
                             :words
                             [#:word{:start 57.89999999999996
                                     :is_morphed false
                                     :active false
                                     :id "17246fdd-0043-4365-8771-d043cdecf595"
                                     :word "Chcą"
                                     :score 0.98
                                     :lemma "Chcą"
                                     :norm "chcą"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Plur|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 58.39999999999996
                                     :pos "VERB"}
                              #:word{:start      58.39999999999996
                                     :is_morphed false
                                     :active     false
                                     :id         "0d4414cf-1254-4356-92ae-80d33a79df0c"
                                     :word       "rozumieć,"
                                     :score      0.85
                                     :lemma      "rozumieć"
                                     :norm       "rozumieć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        59.83999999999996
                                     :pos        "VERB"}
                              #:word{:start 59.83999999999996
                                     :is_morphed false
                                     :active false
                                     :id "5ed1961b-0b0f-4955-b1c5-e8678a5a4d84"
                                     :word "co"
                                     :score 0.93
                                     :lemma "co"
                                     :norm "co"
                                     :morph
                                     "Case=Acc|Gender=Neut|Number=Sing|PronType=Int"
                                     :end 59.87999999999996
                                     :pos "PRON"}
                              #:word{:start 59.87999999999996
                                     :is_morphed true
                                     :active false
                                     :id "72998859-a9fb-40db-ab9e-98d1cfbfb5c5"
                                     :word "mówią"
                                     :score 0.53
                                     :lemma "mówić"
                                     :norm "mówią"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Plur|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 60.319999999999965
                                     :pos "VERB"}
                              #:word{:start      60.319999999999965
                                     :is_morphed false
                                     :active     false
                                     :id         "33b3d0e6-832e-4878-b167-c93b43796615"
                                     :word       "do"
                                     :score      0.87
                                     :lemma      "do"
                                     :norm       "do"
                                     :morph      "AdpType=Prep"
                                     :end        60.63999999999996
                                     :pos        "ADP"}
                              #:word{:start 60.63999999999996
                                     :is_morphed true
                                     :active false
                                     :id "09ace3fa-7096-4f47-b118-12ccb60c9de6"
                                     :word "nich"
                                     :score 0.97
                                     :lemma "on"
                                     :norm "nich"
                                     :morph
                                     "Animacy=Hum|Case=Gen|Gender=Masc|Number=Plur|Person=3|PrepCase=Pre|PronType=Prs|Variant=Long"
                                     :end 60.85999999999996
                                     :pos "PRON"}
                              #:word{:start 60.85999999999996
                                     :is_morphed true
                                     :active false
                                     :id "30a14576-65e6-43fc-bd0b-3cded7797392"
                                     :word "Polacy."
                                     :score 0.77
                                     :lemma "Polak"
                                     :norm "polacy"
                                     :morph "Animacy=Hum|Case=Nom|Gender=Masc|Number=Plur"
                                     :end 61.49999999999996
                                     :pos "PROPN"}]
                             :translations
                             [#:translation{:id "6881b620-15df-4ad6-9131-e6a3a645264c"
                                            :text
                                            "They want to understand what Poles say to them."
                                            :start 57.71999999999996
                                            :end 61.05999999999996
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "1250b1f4-f760-44e2-9470-adc8a33cd716"
                             :start 62.199999999999996
                             :end 67.6
                             :text
                             "Oczywiście lepiej nie zostawiać nauki na ostatnią chwilę."
                             :words [#:word{:start 62.199999999999996
                                            :is_morphed false
                                            :active false
                                            :id "de032c27-0a3e-4f74-8b87-d51a2a0a8148"
                                            :word "Oczywiście"
                                            :score 0.9
                                            :lemma "oczywiście"
                                            :norm "oczywiście"
                                            :morph "Degree=Pos"
                                            :end 63.16
                                            :pos "ADV"}
                                     #:word{:start 63.16
                                            :is_morphed true
                                            :active false
                                            :id "d27ef7dc-6c52-4e5a-954e-94c12e6cefea"
                                            :word "lepiej"
                                            :score 0.63
                                            :lemma "dobrze"
                                            :norm "lepiej"
                                            :morph "Degree=Cmp"
                                            :end 64.25999999999999
                                            :pos "ADV"}
                                     #:word{:start 64.25999999999999
                                            :is_morphed false
                                            :active false
                                            :id "d06cda01-fe92-43e9-8513-e714dbf17563"
                                            :word "nie"
                                            :score 0.89
                                            :lemma "nie"
                                            :norm "nie"
                                            :morph "Polarity=Neg"
                                            :end 64.52
                                            :pos "PART"}
                                     #:word{:start 64.52
                                            :is_morphed false
                                            :active false
                                            :id "74296949-d990-47f8-9e1c-cda2397516b0"
                                            :word "zostawiać"
                                            :score 0.93
                                            :lemma "zostawiać"
                                            :norm "zostawiać"
                                            :morph "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                            :end 65.22
                                            :pos "VERB"}
                                     #:word{:start 65.22
                                            :is_morphed true
                                            :active false
                                            :id "8514d0ff-e0e7-4a41-92c2-ed7ebbd5f33b"
                                            :word "nauki"
                                            :score 0.92
                                            :lemma "nauka"
                                            :norm "nauki"
                                            :morph "Case=Gen|Gender=Fem|Number=Sing"
                                            :end 65.66
                                            :pos "NOUN"}
                                     #:word{:start 65.66
                                            :is_morphed false
                                            :active false
                                            :id "3f2c0a2a-bad4-463f-bf3f-3895338538ce"
                                            :word "na"
                                            :score 0.99
                                            :lemma "na"
                                            :norm "na"
                                            :morph "AdpType=Prep"
                                            :end 65.94
                                            :pos "ADP"}
                                     #:word{:start 65.94
                                            :is_morphed true
                                            :active false
                                            :id "3aec5871-51aa-4119-b461-5dd91cdd4029"
                                            :word "ostatnią"
                                            :score 0.84
                                            :lemma "ostatni"
                                            :norm "ostatnią"
                                            :morph
                                            "Case=Acc|Degree=Pos|Gender=Fem|Number=Sing"
                                            :end 66.6
                                            :pos "ADJ"}
                                     #:word{:start 66.6
                                            :is_morphed true
                                            :active false
                                            :id "f869e7f9-d89d-49b0-a6e7-8ebba1e66ac9"
                                            :word "chwilę."
                                            :score 0.76
                                            :lemma "chwila"
                                            :norm "chwilę"
                                            :morph "Case=Acc|Gender=Fem|Number=Sing"
                                            :end 67.6
                                            :pos "NOUN"}]
                             :translations
                             [#:translation{:id "bec58d95-702b-48f0-ac37-04ff204939ea"
                                            :text
                                            "Of course, it's better not to leave the lessons for the last moment."
                                            :start 62.199999999999996
                                            :end 67.52
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "79c7acd1-a3d6-435d-a835-e4920572595f"
                             :start 67.87999999999998
                             :end 75.01999999999997
                             :text
                             "Ale jeśli musisz szybko poprawić swój polski, to czy jest to możliwe?"
                             :words
                             [#:word{:start      67.87999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "eff1558d-c722-4402-b8e8-9620cf43cf80"
                                     :word       "Ale"
                                     :score      0.91
                                     :lemma      "ale"
                                     :norm       "ale"
                                     :morph      ""
                                     :end        68.25999999999998
                                     :pos        "CCONJ"}
                              #:word{:start      68.25999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "ddff3eec-5353-405e-aabd-04b33c0cb35a"
                                     :word       "jeśli"
                                     :score      0.81
                                     :lemma      "jeśli"
                                     :norm       "jeśli"
                                     :morph      ""
                                     :end        68.85999999999999
                                     :pos        "SCONJ"}
                              #:word{:start 68.85999999999999
                                     :is_morphed true
                                     :active false
                                     :id "b6260c87-ad76-4bd3-95ef-6d563a42dfab"
                                     :word "musisz"
                                     :score 0.82
                                     :lemma "musieć"
                                     :norm "musisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 69.33999999999999
                                     :pos "VERB"}
                              #:word{:start      69.33999999999999
                                     :is_morphed false
                                     :active     false
                                     :id         "c255dc6d-8271-469e-9090-7493ad3c2869"
                                     :word       "szybko"
                                     :score      0.98
                                     :lemma      "szybko"
                                     :norm       "szybko"
                                     :morph      "Degree=Pos"
                                     :end        70.09999999999998
                                     :pos        "ADV"}
                              #:word{:start      70.09999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "2935b6da-e374-4f26-af2a-edc09cf9dbce"
                                     :word       "poprawić"
                                     :score      0.92
                                     :lemma      "poprawić"
                                     :norm       "poprawić"
                                     :morph      "Aspect=Perf|VerbForm=Inf|Voice=Act"
                                     :end        70.65999999999998
                                     :pos        "VERB"}
                              #:word{:start 70.65999999999998
                                     :is_morphed false
                                     :active false
                                     :id "71baec77-d0d8-4ff4-a39c-77bc60a22f3e"
                                     :word "swój"
                                     :score 0.87
                                     :lemma "swój"
                                     :norm "swój"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing|Poss=Yes|PronType=Prs|Reflex=Yes"
                                     :end 70.93999999999998
                                     :pos "DET"}
                              #:word{:start 70.93999999999998
                                     :is_morphed false
                                     :active false
                                     :id "f2da9319-359b-47d7-856b-8ea60fdf59e2"
                                     :word "polski,"
                                     :score 0.58
                                     :lemma "polski"
                                     :norm "polski"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 72.05999999999999
                                     :pos "ADJ"}
                              #:word{:start      72.47999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "69012343-f320-49ab-8fe8-b7e5e7cfcf6a"
                                     :word       "to"
                                     :score      0.95
                                     :lemma      "to"
                                     :norm       "to"
                                     :morph      ""
                                     :end        72.95999999999997
                                     :pos        "SCONJ"}
                              #:word{:start      72.95999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "6d756d21-8a2b-41fb-a7f2-3ea760ea4d7f"
                                     :word       "czy"
                                     :score      0.73
                                     :lemma      "czy"
                                     :norm       "czy"
                                     :morph      "PartType=Int"
                                     :end        73.43999999999997
                                     :pos        "PART"}
                              #:word{:start 73.43999999999997
                                     :is_morphed true
                                     :active false
                                     :id "786f13cc-7245-44c9-8d94-ea1740cfc833"
                                     :word "jest"
                                     :score 0.96
                                     :lemma "być"
                                     :norm "jest"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 73.71999999999997
                                     :pos "AUX"}
                              #:word{:start 73.71999999999997
                                     :is_morphed false
                                     :active false
                                     :id "a3d4e279-4d24-4751-a94c-d521c0f03ba5"
                                     :word "to"
                                     :score 0.96
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Case=Nom|Gender=Neut|Number=Sing|PronType=Dem"
                                     :end 73.95999999999997
                                     :pos "PRON"}
                              #:word{:start 73.95999999999997
                                     :is_morphed true
                                     :active false
                                     :id "59dc1a30-2f49-46a9-af24-6fef5e64fa6a"
                                     :word "możliwe?"
                                     :score 0.82
                                     :lemma "możliwy"
                                     :norm "możliwe"
                                     :morph "Case=Nom|Degree=Pos|Gender=Neut|Number=Sing"
                                     :end 75.01999999999997
                                     :pos "ADJ"}]
                             :translations
                             [#:translation{:id "9913f370-db53-49da-bdf6-d3d30f9921df"
                                            :text
                                            "But if you have to improve your Polish quickly, is it possible?"
                                            :start 67.79999999999998
                                            :end 75.07999999999997
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "498278c3-8f81-470e-b195-1a2a3682e593"
                             :start 76.77999999999997
                             :end 79.55999999999997
                             :text "Odpowiedź brzmi tak."
                             :words
                             [#:word{:start      76.77999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "964265fb-4477-46aa-8326-10267487f96f"
                                     :word       "Odpowiedź"
                                     :score      0.86
                                     :lemma      "odpowiedź"
                                     :norm       "odpowiedź"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        77.47999999999996
                                     :pos        "NOUN"}
                              #:word{:start 77.47999999999996
                                     :is_morphed true
                                     :active false
                                     :id "fb2edcb1-5d56-45d1-b3e8-60d86167f39f"
                                     :word "brzmi"
                                     :score 0.9
                                     :lemma "brzmieć"
                                     :norm "brzmi"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 78.27999999999997
                                     :pos "VERB"}
                              #:word{:start      78.53999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "b62b6f19-cd6a-47aa-99aa-98b2987b668c"
                                     :word       "tak."
                                     :score      0.48
                                     :lemma      "tak"
                                     :norm       "tak"
                                     :morph      "Degree=Pos|PronType=Dem"
                                     :end        79.55999999999997
                                     :pos        "ADV"}]
                             :translations
                             [#:translation{:id "dee5a2c1-45ab-4f81-8db5-10b4df32f02d"
                                            :text
                                            "But if you have to improve your Polish quickly, is it possible?"
                                            :start 67.79999999999998
                                            :end 75.07999999999997
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "06406c36-e7ba-49fe-af03-f6b9c5f2d346"
                             :start 80.25999999999998
                             :end 89.63999999999997
                             :text
                             "Jednak jeśli chcesz mieć szybkie i duże efekty, mieć duży postęp, musisz włożyć w naukę dużo pracy."
                             :words
                             [#:word{:start      80.25999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "1d34dbbc-0f47-4d13-8953-83e4d174fb27"
                                     :word       "Jednak"
                                     :score      0.97
                                     :lemma      "jednak"
                                     :norm       "jednak"
                                     :morph      ""
                                     :end        80.73999999999998
                                     :pos        "CCONJ"}
                              #:word{:start      80.73999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "3726445e-fa26-4577-957d-029213f2c04c"
                                     :word       "jeśli"
                                     :score      0.54
                                     :lemma      "jeśli"
                                     :norm       "jeśli"
                                     :morph      ""
                                     :end        81.61999999999998
                                     :pos        "SCONJ"}
                              #:word{:start 81.61999999999998
                                     :is_morphed true
                                     :active false
                                     :id "98713b75-c305-491b-b43a-d18ad0af6869"
                                     :word "chcesz"
                                     :score 0.9
                                     :lemma "chcieć"
                                     :norm "chcesz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 82.19999999999997
                                     :pos "VERB"}
                              #:word{:start      82.19999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "5f687d22-7a16-4985-bc4c-037970e83ff1"
                                     :word       "mieć"
                                     :score      0.45
                                     :lemma      "mieć"
                                     :norm       "mieć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        82.43999999999997
                                     :pos        "VERB"}
                              #:word{:start 82.43999999999997
                                     :is_morphed true
                                     :active false
                                     :id "bb4202d8-64e5-4508-b11a-2c179e430373"
                                     :word "szybkie"
                                     :score 0.8
                                     :lemma "szybki"
                                     :norm "szybkie"
                                     :morph "Case=Acc|Degree=Pos|Gender=Neut|Number=Sing"
                                     :end 83.05999999999997
                                     :pos "ADJ"}
                              #:word{:start      83.05999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "edbcdfa7-4d4b-4f28-801a-ef2ff10707c7"
                                     :word       "i"
                                     :score      0.99
                                     :lemma      "i"
                                     :norm       "i"
                                     :morph      ""
                                     :end        83.25999999999998
                                     :pos        "CCONJ"}
                              #:word{:start 83.25999999999998
                                     :is_morphed true
                                     :active false
                                     :id "25bce316-0558-42cc-8d16-aaf74d753516"
                                     :word "duże"
                                     :score 0.77
                                     :lemma "duży"
                                     :norm "duże"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Degree=Pos|Gender=Masc|Number=Plur"
                                     :end 83.57999999999998
                                     :pos "ADJ"}
                              #:word{:start 83.57999999999998
                                     :is_morphed true
                                     :active false
                                     :id "fde893ea-497f-46c8-a798-4a97b8f9eb59"
                                     :word "efekty,"
                                     :score 0.91
                                     :lemma "efekt"
                                     :norm "efekty"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Plur"
                                     :end 84.81999999999998
                                     :pos "NOUN"}
                              #:word{:start      84.81999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "29268183-4f96-4a4f-aca2-ae2f3e65b3fc"
                                     :word       "mieć"
                                     :score      0.86
                                     :lemma      "mieć"
                                     :norm       "mieć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        84.95999999999998
                                     :pos        "VERB"}
                              #:word{:start 84.95999999999998
                                     :is_morphed false
                                     :active false
                                     :id "908d73c5-cb3c-4fa5-bac2-f07ce9b330dd"
                                     :word "duży"
                                     :score 0.98
                                     :lemma "duży"
                                     :norm "duży"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 85.33999999999997
                                     :pos "ADJ"}
                              #:word{:start 85.33999999999997
                                     :is_morphed false
                                     :active false
                                     :id "5438c7d1-7f93-4978-89a1-50c13c7ab29e"
                                     :word "postęp,"
                                     :score 0.69
                                     :lemma "postęp"
                                     :norm "postęp"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing"
                                     :end 86.47999999999998
                                     :pos "NOUN"}
                              #:word{:start 86.47999999999998
                                     :is_morphed true
                                     :active false
                                     :id "c749c4ca-b875-4d24-8943-bc958d235430"
                                     :word "musisz"
                                     :score 0.86
                                     :lemma "musieć"
                                     :norm "musisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 86.75999999999998
                                     :pos "VERB"}
                              #:word{:start      86.75999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "e588f408-4a24-448f-9c45-c327117f3b27"
                                     :word       "włożyć"
                                     :score      0.94
                                     :lemma      "włożyć"
                                     :norm       "włożyć"
                                     :morph      "Aspect=Perf|VerbForm=Inf|Voice=Act"
                                     :end        87.27999999999997
                                     :pos        "VERB"}
                              #:word{:start      87.27999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "8eee9ec2-c9e9-4e47-90c3-7d4f8e602f04"
                                     :word       "w"
                                     :score      0.97
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        87.53999999999998
                                     :pos        "ADP"}
                              #:word{:start      87.53999999999998
                                     :is_morphed true
                                     :active     false
                                     :id         "661ce50d-42b1-44d1-bfad-3fb449109030"
                                     :word       "naukę"
                                     :score      0.99
                                     :lemma      "nauka"
                                     :norm       "naukę"
                                     :morph      "Case=Acc|Gender=Fem|Number=Sing"
                                     :end        88.21999999999997
                                     :pos        "NOUN"}
                              #:word{:start 88.21999999999997
                                     :is_morphed false
                                     :active false
                                     :id "af2780ea-a5c5-4e65-a0a4-044072ff675d"
                                     :word "dużo"
                                     :score 0.8
                                     :lemma "dużo"
                                     :norm "dużo"
                                     :morph
                                     "Case=Acc|Gender=Fem|NumType=Card|Number=Plur|PronType=Ind"
                                     :end 88.61999999999998
                                     :pos "DET"}
                              #:word{:start      88.61999999999998
                                     :is_morphed true
                                     :active     false
                                     :id         "5330bfb5-0361-4a4a-9cf4-e5c7e1acb907"
                                     :word       "pracy."
                                     :score      0.9
                                     :lemma      "praca"
                                     :norm       "pracy"
                                     :morph      "Case=Gen|Gender=Fem|Number=Sing"
                                     :end        89.63999999999997
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "5efbbb3f-cb5f-401d-8f01-36eda7fd218b"
                                            :text
                                            "The answer is yes. However, if you want to have fast and big effects, have a big progress, you have to put a lot of work into learning."
                                            :start 76.83999999999997
                                            :end 89.33999999999997
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "32d8cd63-708e-4468-996b-4a6ea1247d8b"
                             :start 90.17999999999996
                             :end 92.45999999999997
                             :text "W takim razie jak to zrobić?"
                             :words
                             [#:word{:start      90.17999999999996
                                     :is_morphed false
                                     :active     false
                                     :id         "c85e3fe7-79b0-4679-9a4e-b11b4d111590"
                                     :word       "W"
                                     :score      0.9
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        90.23999999999997
                                     :pos        "ADP"}
                              #:word{:start 90.23999999999997
                                     :is_morphed true
                                     :active false
                                     :id "5e090983-ac67-4c37-b9b8-45dc192d06a9"
                                     :word "takim"
                                     :score 0.98
                                     :lemma "taki"
                                     :norm "takim"
                                     :morph
                                     "Animacy=Inan|Case=Loc|Gender=Masc|Number=Sing|PronType=Dem"
                                     :end 90.47999999999996
                                     :pos "DET"}
                              #:word{:start 90.47999999999996
                                     :is_morphed true
                                     :active false
                                     :id "078baeea-0bc4-4564-a72c-c5478b237d17"
                                     :word "razie"
                                     :score 0.98
                                     :lemma "raz"
                                     :norm "razie"
                                     :morph
                                     "Animacy=Inan|Case=Loc|Gender=Masc|Number=Sing"
                                     :end 91.33999999999997
                                     :pos "NOUN"}
                              #:word{:start      91.33999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "fddef5c3-ff38-4973-a1d6-2b7c97a888b6"
                                     :word       "jak"
                                     :score      0.56
                                     :lemma      "jak"
                                     :norm       "jak"
                                     :morph      "ConjType=Comp"
                                     :end        91.67999999999996
                                     :pos        "SCONJ"}
                              #:word{:start 91.67999999999996
                                     :is_morphed false
                                     :active false
                                     :id "6b0ff50f-4d11-45e7-8416-699ab83ac499"
                                     :word "to"
                                     :score 0.98
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Case=Acc|Gender=Neut|Number=Sing|PronType=Dem"
                                     :end 91.85999999999997
                                     :pos "PRON"}
                              #:word{:start      91.85999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "32e9cde7-761c-42e4-bbc8-44aa70d1ca51"
                                     :word       "zrobić?"
                                     :score      0.86
                                     :lemma      "zrobić"
                                     :norm       "zrobić"
                                     :morph      "Aspect=Perf|VerbForm=Inf|Voice=Act"
                                     :end        92.45999999999997
                                     :pos        "VERB"}]
                             :translations
                             [#:translation{:id "ce813246-85e2-488f-9e23-83894e4aa369"
                                            :text
                                            "So how to do it? How to make progress very fast? Here are my tips."
                                            :start 89.87999999999997
                                            :end 98.94
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "c1c25123-27d8-4b0b-8bb2-95f1c08b9493"
                             :start 92.76
                             :end 96.56
                             :text "Jak można zrobić postępy bardzo szybko?"
                             :words
                             [#:word{:start      92.76
                                     :is_morphed false
                                     :active     false
                                     :id         "71596844-0eb9-45a6-affb-eeeefae19c11"
                                     :word       "Jak"
                                     :score      0.72
                                     :lemma      "jak"
                                     :norm       "jak"
                                     :morph      "Degree=Pos|PronType=Int"
                                     :end        93.38000000000001
                                     :pos        "ADV"}
                              #:word{:start 93.38000000000001
                                     :is_morphed false
                                     :active false
                                     :id "82ffd450-f5bf-4333-9708-a22242e1aaf2"
                                     :word "można"
                                     :score 0.99
                                     :lemma "można"
                                     :norm "można"
                                     :morph
                                     "Mood=Ind|Tense=Pres|VerbForm=Fin|VerbType=Quasi"
                                     :end 93.64
                                     :pos "VERB"}
                              #:word{:start      93.64
                                     :is_morphed false
                                     :active     false
                                     :id         "a87ca525-019e-45bb-aa47-8433f7e51350"
                                     :word       "zrobić"
                                     :score      0.99
                                     :lemma      "zrobić"
                                     :norm       "zrobić"
                                     :morph      "Aspect=Perf|VerbForm=Inf|Voice=Act"
                                     :end        94.08
                                     :pos        "VERB"}
                              #:word{:start 94.08
                                     :is_morphed true
                                     :active false
                                     :id "9117f75b-7b54-4edb-9824-f5f093a9a404"
                                     :word "postępy"
                                     :score 0.68
                                     :lemma "postęp"
                                     :norm "postępy"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Plur"
                                     :end 95.12
                                     :pos "NOUN"}
                              #:word{:start      95.12
                                     :is_morphed false
                                     :active     false
                                     :id         "00eaa5a6-8b59-4056-b10a-bcb11607b074"
                                     :word       "bardzo"
                                     :score      0.7
                                     :lemma      "bardzo"
                                     :norm       "bardzo"
                                     :morph      "Degree=Pos"
                                     :end        95.54
                                     :pos        "ADV"}
                              #:word{:start      95.54
                                     :is_morphed false
                                     :active     false
                                     :id         "ba975ceb-122c-41a0-8318-922c801eb84a"
                                     :word       "szybko?"
                                     :score      0.94
                                     :lemma      "szybko"
                                     :norm       "szybko"
                                     :morph      "Degree=Pos"
                                     :end        96.56
                                     :pos        "ADV"}]
                             :translations
                             [#:translation{:id "88f542b6-4971-4fd8-8c15-e6f9a278a0aa"
                                            :text
                                            "So how to do it? How to make progress very fast? Here are my tips."
                                            :start 89.87999999999997
                                            :end 98.94
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "fc9d7944-daf8-439c-97fc-595eff92ec1b"
                             :start 96.97999999999999
                             :end 99
                             :text "Oto moje rady."
                             :words
                             [#:word{:start 96.97999999999999
                                     :is_morphed false
                                     :active false
                                     :id "5eeec3a1-78cd-42d7-b296-39bbdc42636e"
                                     :word "Oto"
                                     :score 0.84
                                     :lemma "oto"
                                     :norm "oto"
                                     :morph
                                     "Mood=Ind|Tense=Pres|VerbForm=Fin|VerbType=Quasi"
                                     :end 97.8
                                     :pos "VERB"}
                              #:word{:start 97.8
                                     :is_morphed true
                                     :active false
                                     :id "f665cdad-44b4-4226-9855-1e9d33781bfc"
                                     :word "moje"
                                     :score 0.83
                                     :lemma "mój"
                                     :norm "moje"
                                     :morph
                                     "Case=Nom|Gender=Fem|Number=Plur|Number[psor]=Sing|Person=1|Poss=Yes|PronType=Prs"
                                     :end 98.16
                                     :pos "DET"}
                              #:word{:start      98.16
                                     :is_morphed true
                                     :active     false
                                     :id         "6f1a3e98-0684-4e71-bff9-5e3692dedbfb"
                                     :word       "rady."
                                     :score      0.67
                                     :lemma      "rada"
                                     :norm       "rady"
                                     :morph      "Case=Nom|Gender=Fem|Number=Plur"
                                     :end        99
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "2c2a0e19-f776-4d91-9854-54117063804d"
                                            :text "First, motivation."
                                            :start 99.8
                                            :end 102.11999999999999
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "4ebe06a0-9922-44fe-a800-adda81a69358"
                             :start 99.85999999999999
                             :end 102.11999999999999
                             :text "Po pierwsze motywacja."
                             :words [#:word{:start 99.85999999999999
                                            :is_morphed false
                                            :active false
                                            :id "444a6f12-9b9d-4f2f-ae34-fe88cc8c91cf"
                                            :word "Po"
                                            :score 0.7
                                            :lemma "po"
                                            :norm "po"
                                            :morph "AdpType=Prep"
                                            :end 99.88
                                            :pos "ADP"}
                                     #:word{:start 99.88
                                            :is_morphed true
                                            :active false
                                            :id "d6dad10f-fe46-475f-ac2d-9024e7db959b"
                                            :word "pierwsze"
                                            :score 0.97
                                            :lemma "pierwszy"
                                            :norm "pierwsze"
                                            :morph
                                            "Case=Acc|Degree=Pos|Gender=Neut|Number=Sing"
                                            :end 100.33999999999999
                                            :pos "ADJ"}
                                     #:word{:start 100.33999999999999
                                            :is_morphed false
                                            :active false
                                            :id "a560761e-d89f-4877-ae96-635af3fa20e3"
                                            :word "motywacja."
                                            :score 0.79
                                            :lemma "motywacja"
                                            :norm "motywacja"
                                            :morph "Case=Nom|Gender=Fem|Number=Sing"
                                            :end 102.11999999999999
                                            :pos "NOUN"}]
                             :translations
                             [#:translation{:id "b2e39acf-4661-494e-8e7b-ee1c151cb028"
                                            :text "First, motivation."
                                            :start 99.8
                                            :end 102.11999999999999
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "62098f55-ff49-4d02-83b6-9335b1ab5c53"
                             :start 103.46
                             :end 108.11999999999999
                             :text
                             "Po pierwsze aby osiągnąć trudny cel musisz mieć motywację."
                             :words
                             [#:word{:start      103.46
                                     :is_morphed false
                                     :active     false
                                     :id         "8cd59330-4252-42dc-a036-ee8bd92e2d70"
                                     :word       "Po"
                                     :score      0.93
                                     :lemma      "po"
                                     :norm       "po"
                                     :morph      "AdpType=Prep"
                                     :end        103.47999999999999
                                     :pos        "ADP"}
                              #:word{:start 103.47999999999999
                                     :is_morphed true
                                     :active false
                                     :id "6b9b8bd7-e5f9-4c06-90cc-3cfcee5736cb"
                                     :word "pierwsze"
                                     :score 1
                                     :lemma "pierwszy"
                                     :norm "pierwsze"
                                     :morph "Case=Acc|Degree=Pos|Gender=Neut|Number=Sing"
                                     :end 103.86
                                     :pos "ADJ"}
                              #:word{:start      103.86
                                     :is_morphed false
                                     :active     false
                                     :id         "447a1b62-6c96-4356-bfb2-12c7ae949f42"
                                     :word       "aby"
                                     :score      0.19
                                     :lemma      "aby"
                                     :norm       "aby"
                                     :morph      ""
                                     :end        104.24
                                     :pos        "SCONJ"}
                              #:word{:start      104.24
                                     :is_morphed false
                                     :active     false
                                     :id         "0c778863-1573-443b-9b51-6040b73eb07e"
                                     :word       "osiągnąć"
                                     :score      0.95
                                     :lemma      "osiągnąć"
                                     :norm       "osiągnąć"
                                     :morph      "Aspect=Perf|VerbForm=Inf|Voice=Act"
                                     :end        105.03999999999999
                                     :pos        "VERB"}
                              #:word{:start 105.03999999999999
                                     :is_morphed false
                                     :active false
                                     :id "a75d2edb-6f28-4ce3-94dc-b1f44523a1f0"
                                     :word "trudny"
                                     :score 0.77
                                     :lemma "trudny"
                                     :norm "trudny"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 105.53999999999999
                                     :pos "ADJ"}
                              #:word{:start 105.53999999999999
                                     :is_morphed false
                                     :active false
                                     :id "a12f7f4f-867d-4938-ac7a-1b629d7132a9"
                                     :word "cel"
                                     :score 0.98
                                     :lemma "cel"
                                     :norm "cel"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing"
                                     :end 105.88
                                     :pos "NOUN"}
                              #:word{:start 105.88
                                     :is_morphed true
                                     :active false
                                     :id "4443710b-9140-4860-8cb4-5781ecbed532"
                                     :word "musisz"
                                     :score 0.63
                                     :lemma "musieć"
                                     :norm "musisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 106.3
                                     :pos "VERB"}
                              #:word{:start      106.3
                                     :is_morphed false
                                     :active     false
                                     :id         "4d4903fe-59bd-4f56-ad64-6c259c4a6e56"
                                     :word       "mieć"
                                     :score      0.99
                                     :lemma      "mieć"
                                     :norm       "mieć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        106.89999999999999
                                     :pos        "VERB"}
                              #:word{:start      106.89999999999999
                                     :is_morphed true
                                     :active     false
                                     :id         "bace17ee-47a2-45d2-9ff6-2cf7b04ec8d2"
                                     :word       "motywację."
                                     :score      0.88
                                     :lemma      "motywacja"
                                     :norm       "motywację"
                                     :morph      "Case=Acc|Gender=Fem|Number=Sing"
                                     :end        108.11999999999999
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "87d53bc1-7955-43d9-af61-fe21553e5dfc"
                                            :text
                                            "First, to achieve a difficult goal you need to have motivation."
                                            :start 103.46
                                            :end 108.1
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "ff11abd1-5fa4-4544-91bb-b9e3d00e88ed"
                             :start 108.69999999999999
                             :end 114.82
                             :text
                             "Tylko dzięki wielkiej wewnętrznej sile będziesz mógł uczyć się bardzo szybko."
                             :words
                             [#:word{:start      108.69999999999999
                                     :is_morphed false
                                     :active     false
                                     :id         "06b9d668-fa59-41a9-ae12-b152e8b96673"
                                     :word       "Tylko"
                                     :score      0.97
                                     :lemma      "tylko"
                                     :norm       "tylko"
                                     :morph      ""
                                     :end        109.03999999999999
                                     :pos        "PART"}
                              #:word{:start      109.03999999999999
                                     :is_morphed false
                                     :active     false
                                     :id         "57f5002a-5efc-4323-be1b-a3ae32d24d10"
                                     :word       "dzięki"
                                     :score      0.97
                                     :lemma      "dzięki"
                                     :norm       "dzięki"
                                     :morph      "AdpType=Prep"
                                     :end        109.5
                                     :pos        "ADP"}
                              #:word{:start 109.5
                                     :is_morphed true
                                     :active false
                                     :id "f29d4c87-812c-4e4b-b462-99177db4f7f9"
                                     :word "wielkiej"
                                     :score 0.78
                                     :lemma "wielki"
                                     :norm "wielkiej"
                                     :morph "Case=Gen|Degree=Pos|Gender=Fem|Number=Sing"
                                     :end 110.66
                                     :pos "ADJ"}
                              #:word{:start 110.66
                                     :is_morphed true
                                     :active false
                                     :id "d7115917-1e14-4dae-8dd0-d2f1b0224062"
                                     :word "wewnętrznej"
                                     :score 0.93
                                     :lemma "wewnętrzny"
                                     :norm "wewnętrznej"
                                     :morph "Case=Gen|Degree=Pos|Gender=Fem|Number=Sing"
                                     :end 111.52
                                     :pos "ADJ"}
                              #:word{:start      111.52
                                     :is_morphed true
                                     :active     false
                                     :id         "d618ec3b-d5aa-44f3-bf66-3ea5736d6b03"
                                     :word       "sile"
                                     :score      0.46
                                     :lemma      "siła"
                                     :norm       "sile"
                                     :morph      "Case=Dat|Gender=Fem|Number=Sing"
                                     :end        111.86
                                     :pos        "NOUN"}
                              #:word{:start 111.86
                                     :is_morphed true
                                     :active false
                                     :id "9a285235-03bb-4391-adc7-3f695eab06c1"
                                     :word "będziesz"
                                     :score 0.87
                                     :lemma "być"
                                     :norm "będziesz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Fut|VerbForm=Fin"
                                     :end 112.22
                                     :pos "AUX"}
                              #:word{:start 112.22
                                     :is_morphed true
                                     :active false
                                     :id "f8f8c1c8-c3e0-4740-8de1-bed56ca4f79f"
                                     :word "mógł"
                                     :score 0.94
                                     :lemma "móc"
                                     :norm "mógł"
                                     :morph
                                     "Animacy=Hum|Aspect=Imp|Gender=Masc|Mood=Ind|Number=Sing|Tense=Past|VerbForm=Fin|Voice=Act"
                                     :end 113
                                     :pos "VERB"}
                              #:word{:start      113
                                     :is_morphed false
                                     :active     false
                                     :id         "8b228e25-cc6d-4e28-aa37-d610e4ff3a9b"
                                     :word       "uczyć"
                                     :score      0.87
                                     :lemma      "uczyć"
                                     :norm       "uczyć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        113.41999999999999
                                     :pos        "VERB"}
                              #:word{:start      113.41999999999999
                                     :is_morphed false
                                     :active     false
                                     :id         "f6b11a2b-6eb2-4a08-b58d-0cc6ff531605"
                                     :word       "się"
                                     :score      0.99
                                     :lemma      "się"
                                     :norm       "się"
                                     :morph      "PronType=Prs|Reflex=Yes"
                                     :end        113.8
                                     :pos        "PRON"}
                              #:word{:start      113.8
                                     :is_morphed false
                                     :active     false
                                     :id         "6015f081-76ef-41a4-85b3-5eac7f6ffe18"
                                     :word       "bardzo"
                                     :score      0.99
                                     :lemma      "bardzo"
                                     :norm       "bardzo"
                                     :morph      "Degree=Pos"
                                     :end        113.97999999999999
                                     :pos        "ADV"}
                              #:word{:start      113.97999999999999
                                     :is_morphed false
                                     :active     false
                                     :id         "ae3dec25-d247-4077-855b-d79c5f338d66"
                                     :word       "szybko."
                                     :score      0.98
                                     :lemma      "szybko"
                                     :norm       "szybko"
                                     :morph      "Degree=Pos"
                                     :end        114.82
                                     :pos        "ADV"}]
                             :translations
                             [#:translation{:id "1a76c01e-0daf-4520-adc4-75538d7b1e3c"
                                            :text
                                            "Only with great inner strength you will be able to learn very fast."
                                            :start 108.67999999999999
                                            :end 114.74
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "1df15997-12bc-45af-8d87-c94e2cdf537b"
                             :start 115.07999999999998
                             :end 116.19999999999999
                             :text "Dlaczego?"
                             :words [#:word{:start 115.07999999999998
                                            :is_morphed false
                                            :active false
                                            :id "42c101bb-a651-44b3-afb2-188986895e45"
                                            :word "Dlaczego?"
                                            :score 0.95
                                            :lemma "dlaczego"
                                            :norm "dlaczego"
                                            :morph "PronType=Int"
                                            :end 116.19999999999999
                                            :pos "ADV"}]
                             :translations
                             [#:translation{:id "802f03bc-bc2c-43d1-89dd-e3b47f2d2cd2"
                                            :text "Why?"
                                            :start 114.99999999999999
                                            :end 116.15999999999998
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "082a97ef-a81c-4c4c-b831-e9053018bfcb"
                             :start 116.81999999999998
                             :end 122.35999999999997
                             :text "Ponieważ musisz uczyć się 6-10 godzin dziennie."
                             :words
                             [#:word{:start      116.81999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "0fbc4a2f-f176-4dba-bbec-bca8e25a1851"
                                     :word       "Ponieważ"
                                     :score      0.97
                                     :lemma      "ponieważ"
                                     :norm       "ponieważ"
                                     :morph      ""
                                     :end        117.27999999999997
                                     :pos        "SCONJ"}
                              #:word{:start 117.27999999999997
                                     :is_morphed true
                                     :active false
                                     :id "703288f4-bc22-445c-a21c-b6fd3a147fa1"
                                     :word "musisz"
                                     :score 0.93
                                     :lemma "musieć"
                                     :norm "musisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 118.31999999999998
                                     :pos "VERB"}
                              #:word{:start      118.31999999999998
                                     :is_morphed false
                                     :active     false
                                     :id         "5b74182c-dac0-421c-91c2-cc9860c6343f"
                                     :word       "uczyć"
                                     :score      0.99
                                     :lemma      "uczyć"
                                     :norm       "uczyć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        118.77999999999997
                                     :pos        "VERB"}
                              #:word{:start      118.77999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "b0825d54-b58b-4fb5-9f6e-8530721e7e34"
                                     :word       "się"
                                     :score      0.99
                                     :lemma      "się"
                                     :norm       "się"
                                     :morph      "PronType=Prs|Reflex=Yes"
                                     :end        119.13999999999997
                                     :pos        "PRON"}
                              #:word{:start      119.13999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "8f62adcc-df04-4adc-a3b3-aa12d702c03a"
                                     :word       "6-10"
                                     :score      0.64
                                     :lemma      ""
                                     :morph      ""
                                     :end        120.85999999999997
                                     :pos        ""}
                              #:word{:start      120.85999999999997
                                     :is_morphed true
                                     :active     false
                                     :id         "ff0d05b0-0473-46bf-9508-7d8d09211ffd"
                                     :word       "godzin"
                                     :score      0.86
                                     :lemma      "godzina"
                                     :norm       "godzin"
                                     :morph      "Case=Gen|Gender=Fem|Number=Plur"
                                     :end        121.33999999999997
                                     :pos        "NOUN"}
                              #:word{:start      121.33999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "57c34912-c2ac-4b7b-bacb-64bfc8bf613e"
                                     :word       "dziennie."
                                     :score      0.86
                                     :lemma      "dziennie"
                                     :norm       "dziennie"
                                     :morph      "Degree=Pos"
                                     :end        122.35999999999997
                                     :pos        "ADV"}]
                             :translations
                             [#:translation{:id "c031e8c9-b893-4977-b04e-fd10b64cebb3"
                                            :text "Why?"
                                            :start 114.99999999999999
                                            :end 116.15999999999998
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "cb86f3ec-6536-4a73-af96-ef7ed7f140aa"
                             :start 122.35999999999997
                             :end 124.33999999999997
                             :text "A to wymaga wiele energii."
                             :words
                             [#:word{:start      122.35999999999997
                                     :is_morphed false
                                     :active     false
                                     :id         "790bb661-56fa-4bdc-8a97-5547bd5bbb73"
                                     :word       "A"
                                     :score      0.91
                                     :lemma      "a"
                                     :norm       "a"
                                     :morph      ""
                                     :end        122.53999999999998
                                     :pos        "CCONJ"}
                              #:word{:start 122.53999999999998
                                     :is_morphed false
                                     :active false
                                     :id "43656ef0-b700-4aac-b9b9-caa1bf542424"
                                     :word "to"
                                     :score 0.94
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Case=Nom|Gender=Neut|Number=Sing|PronType=Dem"
                                     :end 122.69999999999997
                                     :pos "PRON"}
                              #:word{:start 122.69999999999997
                                     :is_morphed true
                                     :active false
                                     :id "587b88db-94f6-4c93-a109-c3a118dd21f3"
                                     :word "wymaga"
                                     :score 0.98
                                     :lemma "wymagać"
                                     :norm "wymaga"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 123.29999999999998
                                     :pos "VERB"}
                              #:word{:start 123.29999999999998
                                     :is_morphed false
                                     :active false
                                     :id "b51d2130-ce4f-48e3-953c-5e57a48af9f1"
                                     :word "wiele"
                                     :score 0.98
                                     :lemma "wiele"
                                     :norm "wiele"
                                     :morph
                                     "Case=Nom|Gender=Fem|NumType=Card|Number=Plur|PronType=Ind"
                                     :end 123.69999999999997
                                     :pos "DET"}
                              #:word{:start      123.69999999999997
                                     :is_morphed true
                                     :active     false
                                     :id         "8069485c-3ad6-4518-8056-863cb2bd4345"
                                     :word       "energii."
                                     :score      0.88
                                     :lemma      "energia"
                                     :norm       "energii"
                                     :morph      "Case=Gen|Gender=Fem|Number=Sing"
                                     :end        124.33999999999997
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "ee0e4193-7fe0-4dc2-83fb-1cfae69277bc"
                                            :text
                                            "Because you have to study 6-10 hours a day. And it requires a lot of energy."
                                            :start 116.77999999999997
                                            :end 124.33999999999997
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "68ae300c-8cef-47a3-88ca-98193aafc94a"
                             :start 124.8
                             :end 131.35999999999999
                             :text
                             "Aby mieć duży postęp w krótkim czasie, Twoje emocje muszą być silne."
                             :words
                             [#:word{:start      124.8
                                     :is_morphed false
                                     :active     false
                                     :id         "daca796b-02d9-42c1-bacc-95edcd4a04bf"
                                     :word       "Aby"
                                     :score      0.53
                                     :lemma      "aby"
                                     :norm       "aby"
                                     :morph      ""
                                     :end        125.32
                                     :pos        "SCONJ"}
                              #:word{:start      125.32
                                     :is_morphed false
                                     :active     false
                                     :id         "76da81c4-8d7f-4e2f-be47-d1bdaa5bc720"
                                     :word       "mieć"
                                     :score      0.99
                                     :lemma      "mieć"
                                     :norm       "mieć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        125.82
                                     :pos        "VERB"}
                              #:word{:start 125.82
                                     :is_morphed false
                                     :active false
                                     :id "6eff62c2-e7f9-415a-a0c6-f21ba18325d7"
                                     :word "duży"
                                     :score 0.82
                                     :lemma "duży"
                                     :norm "duży"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 126.53999999999999
                                     :pos "ADJ"}
                              #:word{:start 126.53999999999999
                                     :is_morphed false
                                     :active false
                                     :id "b64336cf-f006-473b-8592-3dc5831ad3c3"
                                     :word "postęp"
                                     :score 0.41
                                     :lemma "postęp"
                                     :norm "postęp"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing"
                                     :end 127.25999999999999
                                     :pos "NOUN"}
                              #:word{:start      127.25999999999999
                                     :is_morphed false
                                     :active     false
                                     :id         "6ce2a11e-5bf7-44d1-aeeb-b885e4c3b7a2"
                                     :word       "w"
                                     :score      0.91
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        127.42
                                     :pos        "ADP"}
                              #:word{:start 127.42
                                     :is_morphed true
                                     :active false
                                     :id "7126f378-667d-4cf6-964e-f349b4b830db"
                                     :word "krótkim"
                                     :score 0.96
                                     :lemma "krótki"
                                     :norm "krótkim"
                                     :morph
                                     "Animacy=Inan|Case=Loc|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 127.82
                                     :pos "ADJ"}
                              #:word{:start 127.82
                                     :is_morphed true
                                     :active false
                                     :id "239ed395-feba-40fd-858c-d2ac4235d6f7"
                                     :word "czasie,"
                                     :score 0.77
                                     :lemma "czas"
                                     :norm "czasie"
                                     :morph
                                     "Animacy=Inan|Case=Loc|Gender=Masc|Number=Sing"
                                     :end 128.76
                                     :pos "NOUN"}
                              #:word{:start 128.76
                                     :is_morphed true
                                     :active false
                                     :id "8b2df375-d416-44fe-9c96-4b6e0fc4e9c3"
                                     :word "Twoje"
                                     :score 0.61
                                     :lemma "twój"
                                     :norm "twoje"
                                     :morph
                                     "Case=Nom|Gender=Fem|Number=Plur|Number[psor]=Sing|Person=2|Poss=Yes|PronType=Prs"
                                     :end 129.16
                                     :pos "DET"}
                              #:word{:start      129.16
                                     :is_morphed true
                                     :active     false
                                     :id         "f2b3b2c3-3a7f-4488-a14d-339be9f7c3d2"
                                     :word       "emocje"
                                     :score      0.91
                                     :lemma      "emocja"
                                     :norm       "emocje"
                                     :morph      "Case=Nom|Gender=Fem|Number=Plur"
                                     :end        129.68
                                     :pos        "NOUN"}
                              #:word{:start 129.68
                                     :is_morphed true
                                     :active false
                                     :id "876de09d-7261-41e3-88d4-0894bf0bca72"
                                     :word "muszą"
                                     :score 0.98
                                     :lemma "musieć"
                                     :norm "muszą"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Plur|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 130
                                     :pos "VERB"}
                              #:word{:start      130
                                     :is_morphed false
                                     :active     false
                                     :id         "7801de79-d80d-461a-907d-2fd5146d2f12"
                                     :word       "być"
                                     :score      1
                                     :lemma      "być"
                                     :norm       "być"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        130.26
                                     :pos        "AUX"}
                              #:word{:start 130.26
                                     :is_morphed true
                                     :active false
                                     :id "71fdbb1a-4e29-4bc7-b5ad-796c7199b74f"
                                     :word "silne."
                                     :score 0.68
                                     :lemma "silny"
                                     :norm "silne"
                                     :morph "Case=Nom|Degree=Pos|Gender=Neut|Number=Sing"
                                     :end 131.35999999999999
                                     :pos "ADJ"}]
                             :translations
                             [#:translation{:id "970dffb5-1d60-44eb-985c-9b3f1becdd16"
                                            :text
                                            "To have a big progress in a short time, your emotions must be strong."
                                            :start 124.8
                                            :end 131.16
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "5d2b6c9f-00a1-484a-807f-5f39ee933425"
                             :start 131.6
                             :end 134.46
                             :text "Musisz mieć wielką chęć do nauki."
                             :words
                             [#:word{:start 131.6
                                     :is_morphed false
                                     :active false
                                     :id "57b4e2ec-f669-4c5a-b3f8-4352c57bfc27"
                                     :word "Musisz"
                                     :score 0.89
                                     :lemma "musisz"
                                     :norm "musisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 131.82
                                     :pos "VERB"}
                              #:word{:start      131.82
                                     :is_morphed false
                                     :active     false
                                     :id         "e8e88406-1fcc-43d2-86f8-508cf24a42b7"
                                     :word       "mieć"
                                     :score      1
                                     :lemma      "mieć"
                                     :norm       "mieć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        132.16
                                     :pos        "VERB"}
                              #:word{:start 132.16
                                     :is_morphed true
                                     :active false
                                     :id "55967b60-e429-4a09-a4ec-51ed7dbc3aad"
                                     :word "wielką"
                                     :score 0.98
                                     :lemma "wielki"
                                     :norm "wielką"
                                     :morph "Case=Acc|Degree=Pos|Gender=Fem|Number=Sing"
                                     :end 132.92000000000002
                                     :pos "ADJ"}
                              #:word{:start      132.92000000000002
                                     :is_morphed false
                                     :active     false
                                     :id         "e28b5f62-529a-48ad-aefb-30223455a35e"
                                     :word       "chęć"
                                     :score      0.72
                                     :lemma      "chęć"
                                     :norm       "chęć"
                                     :morph      "Case=Acc|Gender=Fem|Number=Sing"
                                     :end        133.34
                                     :pos        "NOUN"}
                              #:word{:start      133.34
                                     :is_morphed false
                                     :active     false
                                     :id         "bc90ae9c-8d91-40aa-b201-787a2496d298"
                                     :word       "do"
                                     :score      0.97
                                     :lemma      "do"
                                     :norm       "do"
                                     :morph      "AdpType=Prep"
                                     :end        133.54000000000002
                                     :pos        "ADP"}
                              #:word{:start      133.54000000000002
                                     :is_morphed true
                                     :active     false
                                     :id         "54f40c43-7fc8-4b2e-bd43-a69744473231"
                                     :word       "nauki."
                                     :score      0.96
                                     :lemma      "nauka"
                                     :norm       "nauki"
                                     :morph      "Case=Gen|Gender=Fem|Number=Sing"
                                     :end        134.46
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "ea8fbc0a-03b0-4f5b-8dee-3d77eccf8360"
                                            :text "You must have a great desire to learn."
                                            :start 131.4
                                            :end 134.4
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "fb9ef5f4-6b53-4956-9156-669402b2ae05"
                             :start 134.72000000000003
                             :end 137.72000000000003
                             :text "To musi być ogromna motywacja."
                             :words
                             [#:word{:start 134.72000000000003
                                     :is_morphed false
                                     :active false
                                     :id "bf5fa8c5-ef1d-4108-b465-95cc87860fc3"
                                     :word "To"
                                     :score 0.95
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Case=Nom|Gender=Neut|Number=Sing|PronType=Dem"
                                     :end 134.74000000000004
                                     :pos "PRON"}
                              #:word{:start 134.74000000000004
                                     :is_morphed true
                                     :active false
                                     :id "b4506b65-3990-4339-86ce-3528e9cb8fb2"
                                     :word "musi"
                                     :score 0.98
                                     :lemma "musieć"
                                     :norm "musi"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 135.02000000000004
                                     :pos "VERB"}
                              #:word{:start      135.02000000000004
                                     :is_morphed false
                                     :active     false
                                     :id         "c23eca8a-1de6-4306-b5f0-21131268e5ba"
                                     :word       "być"
                                     :score      1
                                     :lemma      "być"
                                     :norm       "być"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        135.72000000000003
                                     :pos        "AUX"}
                              #:word{:start 135.72000000000003
                                     :is_morphed true
                                     :active false
                                     :id "35c9886a-aea1-425a-b3c7-f30d62d3a786"
                                     :word "ogromna"
                                     :score 0.98
                                     :lemma "ogromny"
                                     :norm "ogromna"
                                     :morph "Case=Nom|Degree=Pos|Gender=Fem|Number=Sing"
                                     :end 136.42000000000002
                                     :pos "ADJ"}
                              #:word{:start      136.42000000000002
                                     :is_morphed false
                                     :active     false
                                     :id         "2b1c2bc0-92f0-4850-a0b9-44f69e0b1d1e"
                                     :word       "motywacja."
                                     :score      0.95
                                     :lemma      "motywacja"
                                     :norm       "motywacja"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        137.72000000000003
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "9de7906c-0a12-448d-9dfc-014448795cbe"
                                            :text "It must be a huge motivation."
                                            :start 134.66000000000003
                                            :end 137.38000000000002
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "5dd3e2cf-4633-4818-8686-135f516b9232"
                             :start 138.02000000000007
                             :end 142.46000000000006
                             :text "Pamiętaj, że 80% sukcesu to Twoje emocje,"
                             :words
                             [#:word{:start 138.02000000000007
                                     :is_morphed true
                                     :active false
                                     :id "79eba57d-aa74-44a5-a0ff-5d74b0d7a8c6"
                                     :word "Pamiętaj,"
                                     :score 0.85
                                     :lemma "pamiętać"
                                     :norm "pamiętaj"
                                     :morph
                                     "Aspect=Imp|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 138.34000000000006
                                     :pos "VERB"}
                              #:word{:start      138.34000000000006
                                     :is_morphed false
                                     :active     false
                                     :id         "ffe8095f-69c8-4590-9d02-787591d26dd1"
                                     :word       "że"
                                     :score      1
                                     :lemma      "że"
                                     :norm       "że"
                                     :morph      ""
                                     :end        139.00000000000006
                                     :pos        "SCONJ"}
                              #:word{:start      139.00000000000006
                                     :is_morphed false
                                     :active     false
                                     :id         "fa6d6305-dc95-4e9c-85f7-1fdff69ddae8"
                                     :word       "80%"
                                     :score      0.86
                                     :lemma      ""
                                     :morph      ""
                                     :end        140.28000000000006
                                     :pos        ""}
                              #:word{:start 140.28000000000006
                                     :is_morphed true
                                     :active false
                                     :id "e294f1a1-62aa-46fc-8960-2fd53a82fa66"
                                     :word "sukcesu"
                                     :score 0.96
                                     :lemma "sukces"
                                     :norm "sukcesu"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Gender=Masc|Number=Sing"
                                     :end 140.98000000000008
                                     :pos "NOUN"}
                              #:word{:start 140.98000000000008
                                     :is_morphed false
                                     :active false
                                     :id "ef69cce4-8c3c-4308-a6de-d07c010f1a6c"
                                     :word "to"
                                     :score 0.83
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Mood=Ind|Tense=Pres|VerbForm=Fin|VerbType=Quasi"
                                     :end 141.18000000000006
                                     :pos "AUX"}
                              #:word{:start 141.18000000000006
                                     :is_morphed true
                                     :active false
                                     :id "d03b01c0-0ef5-4a75-a220-1a63d27cba6f"
                                     :word "Twoje"
                                     :score 0.81
                                     :lemma "twój"
                                     :norm "twoje"
                                     :morph
                                     "Case=Nom|Gender=Fem|Number=Plur|Number[psor]=Sing|Person=2|Poss=Yes|PronType=Prs"
                                     :end 141.58000000000007
                                     :pos "DET"}
                              #:word{:start      141.58000000000007
                                     :is_morphed true
                                     :active     false
                                     :id         "f80fb186-e8b9-41a6-8f2b-ca23cf01446f"
                                     :word       "emocje,"
                                     :score      0.79
                                     :lemma      "emocja"
                                     :norm       "emocje"
                                     :morph      "Case=Nom|Gender=Fem|Number=Plur"
                                     :end        142.46000000000006
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "ed9143be-c848-48d7-918a-61f7fddef7c5"
                                            :text
                                            "Remember that 80% of success is your emotions, your faith and your strength."
                                            :start 137.68000000000006
                                            :end 145.40000000000012
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "6cad7273-4600-4aca-846e-dbee1eb750ff"
                             :start 142.8200000000001
                             :end 145.4400000000001
                             :text "Twoja wiara i Twoja siła."
                             :words
                             [#:word{:start 142.8200000000001
                                     :is_morphed true
                                     :active false
                                     :id "30069228-1017-45a5-a79b-4c5654dffa58"
                                     :word "Twoja"
                                     :score 0.82
                                     :lemma "Twój"
                                     :norm "twoja"
                                     :morph
                                     "Case=Nom|Gender=Fem|Number=Sing|Number[psor]=Sing|Person=2|Poss=Yes|PronType=Prs"
                                     :end 143.1600000000001
                                     :pos "DET"}
                              #:word{:start      143.1600000000001
                                     :is_morphed false
                                     :active     false
                                     :id         "e85e8ca4-2896-4f82-81c4-9864cded709d"
                                     :word       "wiara"
                                     :score      0.57
                                     :lemma      "wiara"
                                     :norm       "wiara"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        143.74000000000012
                                     :pos        "NOUN"}
                              #:word{:start      143.74000000000012
                                     :is_morphed false
                                     :active     false
                                     :id         "caaaea2a-03f1-4cfb-bb71-3c69e9389d9e"
                                     :word       "i"
                                     :score      0.46
                                     :lemma      "i"
                                     :norm       "i"
                                     :morph      ""
                                     :end        144.1000000000001
                                     :pos        "CCONJ"}
                              #:word{:start 144.1000000000001
                                     :is_morphed true
                                     :active false
                                     :id "4a6b2d62-7581-4478-ac60-40dc7d1c888e"
                                     :word "Twoja"
                                     :score 0.95
                                     :lemma "Twój"
                                     :norm "twoja"
                                     :morph
                                     "Case=Nom|Gender=Fem|Number=Sing|Number[psor]=Sing|Person=2|Poss=Yes|PronType=Prs"
                                     :end 144.4400000000001
                                     :pos "DET"}
                              #:word{:start      144.4400000000001
                                     :is_morphed false
                                     :active     false
                                     :id         "420275ac-6f4a-46a4-a675-f5974c1d23fa"
                                     :word       "siła."
                                     :score      0.79
                                     :lemma      "siła"
                                     :norm       "siła"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        145.4400000000001
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "90a044aa-0338-45cb-84dd-b171e9fa8033"
                                            :text "And 20% is the way you will learn."
                                            :start 145.84000000000015
                                            :end 149.92000000000013
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "ebe897c1-41dd-4ce5-9c1f-ad867f4bc239"
                             :start 145.88000000000014
                             :end 150.10000000000014
                             :text "A 20% to sposób, w jaki będziesz się uczyć."
                             :words
                             [#:word{:start      145.88000000000014
                                     :is_morphed false
                                     :active     false
                                     :id         "5530c61f-d773-4d50-a242-d31573297a47"
                                     :word       "A"
                                     :score      0.91
                                     :lemma      "a"
                                     :norm       "a"
                                     :morph      ""
                                     :end        145.88000000000014
                                     :pos        "CCONJ"}
                              #:word{:start      145.88000000000014
                                     :is_morphed false
                                     :active     false
                                     :id         "913b7e8a-0a4e-49b8-8fa3-23ccf16121f4"
                                     :word       "20%"
                                     :score      0.66
                                     :lemma      ""
                                     :morph      ""
                                     :end        147.08000000000013
                                     :pos        ""}
                              #:word{:start 147.08000000000013
                                     :is_morphed false
                                     :active false
                                     :id "e7127776-9d8b-47a2-90e8-c1b40444244d"
                                     :word "to"
                                     :score 0.87
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Mood=Ind|Tense=Pres|VerbForm=Fin|VerbType=Quasi"
                                     :end 147.40000000000015
                                     :pos "AUX"}
                              #:word{:start 147.40000000000015
                                     :is_morphed false
                                     :active false
                                     :id "ccd36450-1f0a-4362-bf37-4dc204e50cde"
                                     :word "sposób,"
                                     :score 0.51
                                     :lemma "sposób"
                                     :norm "sposób"
                                     :morph
                                     "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing"
                                     :end 147.94000000000014
                                     :pos "NOUN"}
                              #:word{:start      147.94000000000014
                                     :is_morphed false
                                     :active     false
                                     :id         "1ece4099-9ad4-46f6-9a71-f7eff9de9da6"
                                     :word       "w"
                                     :score      0.78
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        148.24000000000012
                                     :pos        "ADP"}
                              #:word{:start 148.24000000000012
                                     :is_morphed false
                                     :active false
                                     :id "95a7b9f8-1131-4076-912b-7a0900bba12a"
                                     :word "jaki"
                                     :score 0.95
                                     :lemma "jaki"
                                     :norm "jaki"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing|PronType=Rel"
                                     :end 148.52000000000012
                                     :pos "DET"}
                              #:word{:start 148.52000000000012
                                     :is_morphed true
                                     :active false
                                     :id "0c2b3552-3dff-4f80-82ca-d5df526a2b80"
                                     :word "będziesz"
                                     :score 0.71
                                     :lemma "być"
                                     :norm "będziesz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Fut|VerbForm=Fin"
                                     :end 148.94000000000014
                                     :pos "AUX"}
                              #:word{:start      148.94000000000014
                                     :is_morphed false
                                     :active     false
                                     :id         "7764e10a-fdb9-4c31-ab8d-28caf8fe7006"
                                     :word       "się"
                                     :score      0.97
                                     :lemma      "się"
                                     :norm       "się"
                                     :morph      "PronType=Prs|Reflex=Yes"
                                     :end        149.22000000000014
                                     :pos        "PRON"}
                              #:word{:start      149.22000000000014
                                     :is_morphed false
                                     :active     false
                                     :id         "52d63fab-dbad-403a-b547-ea7f22a2a51f"
                                     :word       "uczyć."
                                     :score      0.92
                                     :lemma      "uczyć"
                                     :norm       "uczyć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        150.10000000000014
                                     :pos        "VERB"}]
                             :translations
                             [#:translation{:id "c30af5c0-4b9c-4dc9-b0ea-ce8685fcb827"
                                            :text "And 20% is the way you will learn."
                                            :start 145.84000000000015
                                            :end 149.92000000000013
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "a720b1b4-2aea-4864-8e97-d203850c20fc"
                             :start 150.96000000000015
                             :end 155.72000000000014
                             :text
                             "Musisz mieć duży powód, by uczyć się polskiego i bardzo tego chcieć."
                             :words
                             [#:word{:start 150.96000000000015
                                     :is_morphed false
                                     :active false
                                     :id "255a9b2e-4b9f-4f2c-b104-2011f4467ea2"
                                     :word "Musisz"
                                     :score 0.98
                                     :lemma "musisz"
                                     :norm "musisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 151.20000000000016
                                     :pos "VERB"}
                              #:word{:start      151.20000000000016
                                     :is_morphed false
                                     :active     false
                                     :id         "fee6e851-22e2-44fb-a828-170e35cf7f25"
                                     :word       "mieć"
                                     :score      0.98
                                     :lemma      "mieć"
                                     :norm       "mieć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        151.52000000000015
                                     :pos        "VERB"}
                              #:word{:start 151.52000000000015
                                     :is_morphed false
                                     :active false
                                     :id "a6f54a5e-bd1f-48f7-8572-f1eae261e2a1"
                                     :word "duży"
                                     :score 0.99
                                     :lemma "duży"
                                     :norm "duży"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 151.92000000000016
                                     :pos "ADJ"}
                              #:word{:start 151.92000000000016
                                     :is_morphed false
                                     :active false
                                     :id "3488a7d7-d3ef-446b-97f3-46ff6aad833e"
                                     :word "powód,"
                                     :score 0.72
                                     :lemma "powód"
                                     :norm "powód"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing"
                                     :end 152.96000000000015
                                     :pos "NOUN"}
                              #:word{:start      152.96000000000015
                                     :is_morphed false
                                     :active     false
                                     :id         "bb0abdc6-87c5-4c61-a9e3-40ed32314da5"
                                     :word       "by"
                                     :score      0.14
                                     :lemma      "by"
                                     :norm       "by"
                                     :morph      ""
                                     :end        153.00000000000014
                                     :pos        "SCONJ"}
                              #:word{:start      153.00000000000014
                                     :is_morphed false
                                     :active     false
                                     :id         "4cbfdbcc-76cb-49a9-a1c4-acb1381ec32c"
                                     :word       "uczyć"
                                     :score      0.94
                                     :lemma      "uczyć"
                                     :norm       "uczyć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        153.30000000000015
                                     :pos        "VERB"}
                              #:word{:start      153.30000000000015
                                     :is_morphed false
                                     :active     false
                                     :id         "9214e248-dd79-4c13-af3c-a247d93898ce"
                                     :word       "się"
                                     :score      0.91
                                     :lemma      "się"
                                     :norm       "się"
                                     :morph      "PronType=Prs|Reflex=Yes"
                                     :end        153.58000000000015
                                     :pos        "PRON"}
                              #:word{:start 153.58000000000015
                                     :is_morphed true
                                     :active false
                                     :id "c9050a8d-fd8a-4d13-95db-a6445869d4f6"
                                     :word "polskiego"
                                     :score 0.87
                                     :lemma "polski"
                                     :norm "polskiego"
                                     :morph "Case=Gen|Degree=Pos|Gender=Neut|Number=Sing"
                                     :end 154.06000000000014
                                     :pos "ADJ"}
                              #:word{:start      154.06000000000014
                                     :is_morphed false
                                     :active     false
                                     :id         "a08ed972-02a1-4197-b883-5dd5f8cc5f16"
                                     :word       "i"
                                     :score      0.98
                                     :lemma      "i"
                                     :norm       "i"
                                     :morph      ""
                                     :end        154.44000000000014
                                     :pos        "CCONJ"}
                              #:word{:start      154.44000000000014
                                     :is_morphed false
                                     :active     false
                                     :id         "4fa52ae6-81be-4907-851a-95552adf8528"
                                     :word       "bardzo"
                                     :score      0.98
                                     :lemma      "bardzo"
                                     :norm       "bardzo"
                                     :morph      "Degree=Pos"
                                     :end        154.86000000000016
                                     :pos        "ADV"}
                              #:word{:start 154.86000000000016
                                     :is_morphed true
                                     :active false
                                     :id "e4fb1d4c-ab6c-4fc8-8334-be071d6449ca"
                                     :word "tego"
                                     :score 0.97
                                     :lemma "to"
                                     :norm "tego"
                                     :morph
                                     "Case=Gen|Gender=Neut|Number=Sing|PronType=Dem"
                                     :end 155.18000000000015
                                     :pos "PRON"}
                              #:word{:start      155.18000000000015
                                     :is_morphed false
                                     :active     false
                                     :id         "8410ae02-d11b-4fbe-bc62-eec51550d0ef"
                                     :word       "chcieć."
                                     :score      0.68
                                     :lemma      "chcieć"
                                     :norm       "chcieć"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        155.72000000000014
                                     :pos        "VERB"}]
                             :translations
                             [#:translation{:id "2e4baf19-f1fb-4df9-b2c1-61bef19e57bc"
                                            :text
                                            "You must have a big reason to learn Polish and really want it."
                                            :start 150.78000000000014
                                            :end 155.50000000000014
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "9bb87553-d8b1-4250-ac59-fcbe346554ba"
                             :start 156.18
                             :end 161.86
                             :text "Duży powód to duża chęć i pasja do nauki."
                             :words
                             [#:word{:start 156.18
                                     :is_morphed false
                                     :active false
                                     :id "2cb94fa9-f174-4245-b52c-6daa7dc20974"
                                     :word "Duży"
                                     :score 0.73
                                     :lemma "duży"
                                     :norm "duży"
                                     :morph
                                     "Animacy=Inan|Case=Nom|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 156.92000000000002
                                     :pos "ADJ"}
                              #:word{:start 156.92000000000002
                                     :is_morphed false
                                     :active false
                                     :id "0a536117-2026-4b6a-8ef8-9eb9445f1464"
                                     :word "powód"
                                     :score 0.94
                                     :lemma "powód"
                                     :norm "powód"
                                     :morph
                                     "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing"
                                     :end 157.58
                                     :pos "NOUN"}
                              #:word{:start 157.58
                                     :is_morphed false
                                     :active false
                                     :id "f57d5731-aff0-4c1a-b500-e8c6d6ca98c5"
                                     :word "to"
                                     :score 0.34
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Mood=Ind|Tense=Pres|VerbForm=Fin|VerbType=Quasi"
                                     :end 158.08
                                     :pos "AUX"}
                              #:word{:start 158.08
                                     :is_morphed true
                                     :active false
                                     :id "09676de2-b1a9-4056-adc8-0e221da6680c"
                                     :word "duża"
                                     :score 0.75
                                     :lemma "duży"
                                     :norm "duża"
                                     :morph "Case=Nom|Degree=Pos|Gender=Fem|Number=Sing"
                                     :end 158.52
                                     :pos "ADJ"}
                              #:word{:start      158.52
                                     :is_morphed false
                                     :active     false
                                     :id         "94ebcd51-913d-4de4-8fee-291cc58f0d98"
                                     :word       "chęć"
                                     :score      0.71
                                     :lemma      "chęć"
                                     :norm       "chęć"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        159.20000000000002
                                     :pos        "NOUN"}
                              #:word{:start      159.20000000000002
                                     :is_morphed false
                                     :active     false
                                     :id         "b1ecb11e-169a-4343-9da3-4a90a23cce60"
                                     :word       "i"
                                     :score      0.86
                                     :lemma      "i"
                                     :norm       "i"
                                     :morph      ""
                                     :end        159.86
                                     :pos        "CCONJ"}
                              #:word{:start      159.86
                                     :is_morphed false
                                     :active     false
                                     :id         "b7824a05-6c7d-4a60-9f56-aa9775815eaa"
                                     :word       "pasja"
                                     :score      0.74
                                     :lemma      "pasja"
                                     :norm       "pasja"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        160.42000000000002
                                     :pos        "NOUN"}
                              #:word{:start      160.42000000000002
                                     :is_morphed false
                                     :active     false
                                     :id         "4fab571f-dbab-4dbe-a7f0-1bfabc0f15ab"
                                     :word       "do"
                                     :score      0.93
                                     :lemma      "do"
                                     :norm       "do"
                                     :morph      "AdpType=Prep"
                                     :end        160.64000000000001
                                     :pos        "ADP"}
                              #:word{:start      160.64000000000001
                                     :is_morphed true
                                     :active     false
                                     :id         "2df11f9a-3328-4219-aeb6-3359af1ca897"
                                     :word       "nauki."
                                     :score      0.91
                                     :lemma      "nauka"
                                     :norm       "nauki"
                                     :morph      "Case=Gen|Gender=Fem|Number=Sing"
                                     :end        161.86
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "af510aa1-0969-438b-bfbc-d67fe92e84bf"
                                            :text
                                            "A big reason is a big desire and passion for learning."
                                            :start 156.18
                                            :end 161.72
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "a1c83b62-da43-4a32-a68a-40ddc2c4a0d6"
                             :start 161.86
                             :end 166.74000000000004
                             :text "Duża pasja to duży i szybki sukces."
                             :words
                             [#:word{:start 161.86
                                     :is_morphed true
                                     :active false
                                     :id "c7c601b3-b6d7-44d0-a299-adbfd4aa1ed3"
                                     :word "Duża"
                                     :score 0.72
                                     :lemma "duży"
                                     :norm "duża"
                                     :morph "Case=Nom|Degree=Pos|Gender=Fem|Number=Sing"
                                     :end 162.12
                                     :pos "ADJ"}
                              #:word{:start      162.12
                                     :is_morphed false
                                     :active     false
                                     :id         "208152f8-f693-49a8-a56c-bc41b20c21ef"
                                     :word       "pasja"
                                     :score      0.98
                                     :lemma      "pasja"
                                     :norm       "pasja"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        162.74
                                     :pos        "NOUN"}
                              #:word{:start 162.74
                                     :is_morphed false
                                     :active false
                                     :id "78b5f219-7631-451e-bf0d-0479be0b588a"
                                     :word "to"
                                     :score 0.79
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Mood=Ind|Tense=Pres|VerbForm=Fin|VerbType=Quasi"
                                     :end 163.54000000000002
                                     :pos "AUX"}
                              #:word{:start 163.94000000000005
                                     :is_morphed false
                                     :active false
                                     :id "eb53ae1e-ffcf-4123-afd6-f6947ab03a4e"
                                     :word "duży"
                                     :score 0.83
                                     :lemma "duży"
                                     :norm "duży"
                                     :morph
                                     "Animacy=Nhum|Case=Nom|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 164.62000000000003
                                     :pos "ADJ"}
                              #:word{:start      164.62000000000003
                                     :is_morphed false
                                     :active     false
                                     :id         "eeeb8577-2b45-4f0c-869d-a48720717074"
                                     :word       "i"
                                     :score      0.96
                                     :lemma      "i"
                                     :norm       "i"
                                     :morph      ""
                                     :end        165.02000000000004
                                     :pos        "CCONJ"}
                              #:word{:start 165.02000000000004
                                     :is_morphed false
                                     :active false
                                     :id "57b2da0d-c4b1-444f-b568-5e8607b3095b"
                                     :word "szybki"
                                     :score 0.96
                                     :lemma "szybki"
                                     :norm "szybki"
                                     :morph
                                     "Animacy=Inan|Case=Nom|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 165.50000000000003
                                     :pos "ADJ"}
                              #:word{:start 165.50000000000003
                                     :is_morphed false
                                     :active false
                                     :id "5f59e702-af10-4cf9-8f94-aa60b86bcef3"
                                     :word "sukces."
                                     :score 0.95
                                     :lemma "sukces"
                                     :norm "sukces"
                                     :morph
                                     "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing"
                                     :end 166.74000000000004
                                     :pos "NOUN"}]
                             :translations
                             [#:translation{:id "aa5d14ad-3918-420b-a793-a0ffb1125b8d"
                                            :text
                                            "A big passion is a big and fast success."
                                            :start 161.72
                                            :end 166.72000000000003
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "e2e9ef5e-5f8e-4c87-8816-4d89e8f5ac36"
                             :start 168.14000000000007
                             :end 174.4000000000001
                             :text
                             "Drugi klucz do sukcesu to słuchanie po polsku w ogromnych ilościach."
                             :words
                             [#:word{:start 168.14000000000007
                                     :is_morphed false
                                     :active false
                                     :id "3445ec64-2d21-4dd3-9a3d-8e3dfb2841a1"
                                     :word "Drugi"
                                     :score 0.59
                                     :lemma "drugi"
                                     :norm "drugi"
                                     :morph
                                     "Animacy=Inan|Case=Nom|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 168.46000000000006
                                     :pos "ADJ"}
                              #:word{:start 168.46000000000006
                                     :is_morphed false
                                     :active false
                                     :id "cd3fbf58-f4ce-4a8b-9f90-fb14b3be2e5b"
                                     :word "klucz"
                                     :score 0.95
                                     :lemma "klucz"
                                     :norm "klucz"
                                     :morph
                                     "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing"
                                     :end 168.94000000000008
                                     :pos "NOUN"}
                              #:word{:start      168.94000000000008
                                     :is_morphed false
                                     :active     false
                                     :id         "a404b1d1-b1aa-4786-b558-640d376e977e"
                                     :word       "do"
                                     :score      0.94
                                     :lemma      "do"
                                     :norm       "do"
                                     :morph      "AdpType=Prep"
                                     :end        169.08000000000007
                                     :pos        "ADP"}
                              #:word{:start 169.08000000000007
                                     :is_morphed true
                                     :active false
                                     :id "6062b28c-2b45-44a3-8c79-a4f1df908822"
                                     :word "sukcesu"
                                     :score 0.98
                                     :lemma "sukces"
                                     :norm "sukcesu"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Gender=Masc|Number=Sing"
                                     :end 169.68000000000006
                                     :pos "NOUN"}
                              #:word{:start 169.68000000000006
                                     :is_morphed false
                                     :active false
                                     :id "e68ce31b-8e93-4e85-8654-3405dd194f38"
                                     :word "to"
                                     :score 0.95
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Mood=Ind|Tense=Pres|VerbForm=Fin|VerbType=Quasi"
                                     :end 170.26000000000008
                                     :pos "AUX"}
                              #:word{:start 170.26000000000008
                                     :is_morphed true
                                     :active false
                                     :id "e2b8a90f-4473-46d3-b67c-33482e84a8f2"
                                     :word "słuchanie"
                                     :score 0.9
                                     :lemma "słuchać"
                                     :norm "słuchanie"
                                     :morph
                                     "Aspect=Imp|Case=Nom|Gender=Neut|Number=Sing|Polarity=Pos|VerbForm=Vnoun"
                                     :end 171.02000000000007
                                     :pos "NOUN"}
                              #:word{:start      171.02000000000007
                                     :is_morphed false
                                     :active     false
                                     :id         "3d45c1b2-8662-418d-a101-0d32a2b95c0e"
                                     :word       "po"
                                     :score      0.53
                                     :lemma      "po"
                                     :norm       "po"
                                     :morph      "AdpType=Prep"
                                     :end        171.26000000000008
                                     :pos        "ADP"}
                              #:word{:start      171.26000000000008
                                     :is_morphed true
                                     :active     false
                                     :id         "b154c379-6a23-4c9c-9570-b87c0b2c8000"
                                     :word       "polsku"
                                     :score      0.88
                                     :lemma      "polski"
                                     :norm       "polsku"
                                     :morph      "PrepCase=Pre"
                                     :end        172.3400000000001
                                     :pos        "ADJ"}
                              #:word{:start      172.3400000000001
                                     :is_morphed false
                                     :active     false
                                     :id         "fd0f761b-76d0-4d06-af7b-a32deeb31b29"
                                     :word       "w"
                                     :score      0.93
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        172.72000000000008
                                     :pos        "ADP"}
                              #:word{:start 172.72000000000008
                                     :is_morphed true
                                     :active false
                                     :id "b3cbc145-ef9b-49fd-b5bb-b890bd66fede"
                                     :word "ogromnych"
                                     :score 0.97
                                     :lemma "ogromny"
                                     :norm "ogromnych"
                                     :morph "Case=Loc|Degree=Pos|Gender=Fem|Number=Plur"
                                     :end 173.30000000000007
                                     :pos "ADJ"}
                              #:word{:start      173.30000000000007
                                     :is_morphed true
                                     :active     false
                                     :id         "4b91c6d3-7fa5-4599-a008-724422ecb69e"
                                     :word       "ilościach."
                                     :score      0.92
                                     :lemma      "ilość"
                                     :norm       "ilościach"
                                     :morph      "Case=Loc|Gender=Fem|Number=Plur"
                                     :end        174.4000000000001
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "96f11bb2-2812-4eb8-945b-6674931f58bb"
                                            :text
                                            "The second key to success is listening to Polish in huge quantities."
                                            :start 168.12000000000006
                                            :end 174.38000000000008
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "76466d43-5805-4066-a336-cad97b448325"
                             :start 174.9600000000001
                             :end 178.4200000000001
                             :text "Nie trać czasu na naukę gramatyki i słówek."
                             :words
                             [#:word{:start      174.9600000000001
                                     :is_morphed false
                                     :active     false
                                     :id         "216b8fa9-ab91-4578-80d1-e6f3331eefad"
                                     :word       "Nie"
                                     :score      0.91
                                     :lemma      "nie"
                                     :norm       "nie"
                                     :morph      "Polarity=Neg"
                                     :end        175.18000000000012
                                     :pos        "PART"}
                              #:word{:start 175.18000000000012
                                     :is_morphed false
                                     :active false
                                     :id "4c261fd3-c7d6-45bf-a3ef-a4ccf0fbc3ce"
                                     :word "trać"
                                     :score 0.76
                                     :lemma "trać"
                                     :norm "trać"
                                     :morph
                                     "Aspect=Imp|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 175.62000000000012
                                     :pos "VERB"}
                              #:word{:start 175.62000000000012
                                     :is_morphed true
                                     :active false
                                     :id "d54e4f5c-a87d-4af6-94c1-dd108890bc40"
                                     :word "czasu"
                                     :score 0.9
                                     :lemma "czas"
                                     :norm "czasu"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Gender=Masc|Number=Sing"
                                     :end 176.06000000000012
                                     :pos "NOUN"}
                              #:word{:start      176.06000000000012
                                     :is_morphed false
                                     :active     false
                                     :id         "03f89a29-ca9e-4e7d-a71c-49e09d5d6c14"
                                     :word       "na"
                                     :score      0.99
                                     :lemma      "na"
                                     :norm       "na"
                                     :morph      "AdpType=Prep"
                                     :end        176.34000000000012
                                     :pos        "ADP"}
                              #:word{:start      176.34000000000012
                                     :is_morphed true
                                     :active     false
                                     :id         "5c9bb537-06b4-4c33-bfce-305c336714b4"
                                     :word       "naukę"
                                     :score      0.99
                                     :lemma      "nauka"
                                     :norm       "naukę"
                                     :morph      "Case=Acc|Gender=Fem|Number=Sing"
                                     :end        176.6400000000001
                                     :pos        "NOUN"}
                              #:word{:start      176.6400000000001
                                     :is_morphed true
                                     :active     false
                                     :id         "e0693c76-345e-4a5c-a9a0-4d73cf783d2b"
                                     :word       "gramatyki"
                                     :score      0.77
                                     :lemma      "gramatyka"
                                     :norm       "gramatyki"
                                     :morph      "Case=Gen|Gender=Fem|Number=Sing"
                                     :end        177.3600000000001
                                     :pos        "NOUN"}
                              #:word{:start      177.3600000000001
                                     :is_morphed false
                                     :active     false
                                     :id         "fd23e83d-783b-4c5f-a8d5-142c8acda8e1"
                                     :word       "i"
                                     :score      0.89
                                     :lemma      "i"
                                     :norm       "i"
                                     :morph      ""
                                     :end        177.5400000000001
                                     :pos        "CCONJ"}
                              #:word{:start      177.5400000000001
                                     :is_morphed true
                                     :active     false
                                     :id         "ad0aad6f-8f20-4243-9cd5-392533cb95b5"
                                     :word       "słówek."
                                     :score      0.75
                                     :lemma      "słówko"
                                     :norm       "słówek"
                                     :morph      "Case=Gen|Gender=Neut|Number=Plur"
                                     :end        178.4200000000001
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "8a4feb63-d96c-4432-9d51-a15553d01642"
                                            :text
                                            "Don't waste time learning grammar and words."
                                            :start 174.9400000000001
                                            :end 178.28000000000011
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "844a037a-2b68-4087-97fc-9cb5f7199ca1"
                             :start 178.68000000000015
                             :end 182.04000000000016
                             :text "Nie trać czasu na próbowaniu mówić po polsku."
                             :words
                             [#:word{:start      178.68000000000015
                                     :is_morphed false
                                     :active     false
                                     :id         "dea39486-7560-43b0-aa7c-e048df2d572b"
                                     :word       "Nie"
                                     :score      1
                                     :lemma      "nie"
                                     :norm       "nie"
                                     :morph      "Polarity=Neg"
                                     :end        178.76000000000016
                                     :pos        "PART"}
                              #:word{:start 178.76000000000016
                                     :is_morphed false
                                     :active false
                                     :id "a46ff80a-09ec-41d4-b535-c17d8c8b4fe0"
                                     :word "trać"
                                     :score 1
                                     :lemma "trać"
                                     :norm "trać"
                                     :morph
                                     "Aspect=Imp|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 179.18000000000015
                                     :pos "VERB"}
                              #:word{:start 179.18000000000015
                                     :is_morphed true
                                     :active false
                                     :id "4351bbc3-de7f-423d-8a20-d21fcf8fc659"
                                     :word "czasu"
                                     :score 1
                                     :lemma "czas"
                                     :norm "czasu"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Gender=Masc|Number=Sing"
                                     :end 179.58000000000015
                                     :pos "NOUN"}
                              #:word{:start      179.58000000000015
                                     :is_morphed false
                                     :active     false
                                     :id         "d31dbbe6-2648-4312-912c-a8e3c7e5d67d"
                                     :word       "na"
                                     :score      0.99
                                     :lemma      "na"
                                     :norm       "na"
                                     :morph      "AdpType=Prep"
                                     :end        179.88000000000017
                                     :pos        "ADP"}
                              #:word{:start 179.88000000000017
                                     :is_morphed true
                                     :active false
                                     :id "a33c34ce-d78f-4fa5-a415-3d361db6920c"
                                     :word "próbowaniu"
                                     :score 0.79
                                     :lemma "próbować"
                                     :norm "próbowaniu"
                                     :morph
                                     "Aspect=Perf|Case=Loc|Gender=Neut|Number=Sing|Polarity=Pos|VerbForm=Vnoun"
                                     :end 180.48000000000016
                                     :pos "NOUN"}
                              #:word{:start      180.48000000000016
                                     :is_morphed false
                                     :active     false
                                     :id         "fa6f660e-31db-4bb8-8b47-e14e3352052b"
                                     :word       "mówić"
                                     :score      0.91
                                     :lemma      "mówić"
                                     :norm       "mówić"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        180.94000000000017
                                     :pos        "VERB"}
                              #:word{:start      180.94000000000017
                                     :is_morphed false
                                     :active     false
                                     :id         "1d395222-bdbf-4eb2-8912-d1c4340eb2dc"
                                     :word       "po"
                                     :score      0.99
                                     :lemma      "po"
                                     :norm       "po"
                                     :morph      "AdpType=Prep"
                                     :end        181.14000000000016
                                     :pos        "ADP"}
                              #:word{:start      181.14000000000016
                                     :is_morphed true
                                     :active     false
                                     :id         "1d9ae9e4-a35f-4a3f-adc0-d4ce51acd33a"
                                     :word       "polsku."
                                     :score      0.93
                                     :lemma      "polski"
                                     :norm       "polsku"
                                     :morph      "PrepCase=Pre"
                                     :end        182.04000000000016
                                     :pos        "ADJ"}]
                             :translations
                             [#:translation{:id "249d8f0f-c044-4197-b22d-617791ab5a2e"
                                            :text
                                            "Don't waste time trying to speak Polish."
                                            :start 178.54000000000016
                                            :end 181.88000000000017
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "eabdc0da-9df3-4869-9a07-8db44af9137a"
                             :start 182.5600000000002
                             :end 187.7200000000002
                             :text
                             "Musisz spędzić swój czas słuchając polskiego lub czytając po polsku."
                             :words
                             [#:word{:start 182.5600000000002
                                     :is_morphed false
                                     :active false
                                     :id "c1d03c42-a7ca-4988-987e-f3210d23779c"
                                     :word "Musisz"
                                     :score 0.53
                                     :lemma "musisz"
                                     :norm "musisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 182.8400000000002
                                     :pos "VERB"}
                              #:word{:start      182.8400000000002
                                     :is_morphed false
                                     :active     false
                                     :id         "668d9b1c-d31c-436d-b9e9-6fe43dd7c597"
                                     :word       "spędzić"
                                     :score      0.92
                                     :lemma      "spędzić"
                                     :norm       "spędzić"
                                     :morph      "Aspect=Perf|VerbForm=Inf|Voice=Act"
                                     :end        183.4000000000002
                                     :pos        "VERB"}
                              #:word{:start 183.4000000000002
                                     :is_morphed false
                                     :active false
                                     :id "30ef89d5-2771-4a1a-b427-1e31c921fb7a"
                                     :word "swój"
                                     :score 0.94
                                     :lemma "swój"
                                     :norm "swój"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing|Poss=Yes|PronType=Prs|Reflex=Yes"
                                     :end 183.7800000000002
                                     :pos "DET"}
                              #:word{:start 183.7800000000002
                                     :is_morphed false
                                     :active false
                                     :id "c78e1322-f55d-478f-b5a8-6aa313d68437"
                                     :word "czas"
                                     :score 0.98
                                     :lemma "czas"
                                     :norm "czas"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing"
                                     :end 184.2200000000002
                                     :pos "NOUN"}
                              #:word{:start 184.2200000000002
                                     :is_morphed true
                                     :active false
                                     :id "2c080993-5536-413a-b2e5-54c8ad170e2e"
                                     :word "słuchając"
                                     :score 0.68
                                     :lemma "słuchać"
                                     :norm "słuchając"
                                     :morph
                                     "Aspect=Imp|Tense=Pres|VerbForm=Conv|Voice=Act"
                                     :end 184.7800000000002
                                     :pos "VERB"}
                              #:word{:start 184.7800000000002
                                     :is_morphed true
                                     :active false
                                     :id "40c4e308-6e80-48a8-bfbc-1e8b9cc5f50e"
                                     :word "polskiego"
                                     :score 0.93
                                     :lemma "polski"
                                     :norm "polskiego"
                                     :morph "Case=Gen|Degree=Pos|Gender=Neut|Number=Sing"
                                     :end 185.64000000000019
                                     :pos "ADJ"}
                              #:word{:start      185.64000000000019
                                     :is_morphed false
                                     :active     false
                                     :id         "f62c8bdf-c2d6-4d3b-85a3-bf5f0b67e94f"
                                     :word       "lub"
                                     :score      0.98
                                     :lemma      "lub"
                                     :norm       "lub"
                                     :morph      ""
                                     :end        186.4400000000002
                                     :pos        "CCONJ"}
                              #:word{:start 186.4400000000002
                                     :is_morphed true
                                     :active false
                                     :id "3a703f9a-c7ff-401f-ba48-c17e89e82507"
                                     :word "czytając"
                                     :score 0.95
                                     :lemma "czytać"
                                     :norm "czytając"
                                     :morph
                                     "Aspect=Imp|Tense=Pres|VerbForm=Conv|Voice=Act"
                                     :end 187.0600000000002
                                     :pos "VERB"}
                              #:word{:start      187.0600000000002
                                     :is_morphed false
                                     :active     false
                                     :id         "96d0bf35-2a4e-4398-aca3-508a70ee1d02"
                                     :word       "po"
                                     :score      0.99
                                     :lemma      "po"
                                     :norm       "po"
                                     :morph      "AdpType=Prep"
                                     :end        187.2000000000002
                                     :pos        "ADP"}
                              #:word{:start      187.2000000000002
                                     :is_morphed true
                                     :active     false
                                     :id         "1dfb7f0a-e906-45ed-b54f-25b20d14dfeb"
                                     :word       "polsku."
                                     :score      0.98
                                     :lemma      "polski"
                                     :norm       "polsku"
                                     :morph      "PrepCase=Pre"
                                     :end        187.7200000000002
                                     :pos        "ADJ"}]
                             :translations
                             [#:translation{:id "7d5cbb88-5627-44e2-be09-c5c8ccf55e36"
                                            :text
                                            "You have to spend your time listening to Polish or reading Polish."
                                            :start 182.4000000000002
                                            :end 187.3400000000002
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "d9a1fcb6-0c8a-4b30-977b-b8d052912f91"
                             :start 188.18
                             :end 191.72
                             :text "Musisz poświęcić na to mnóstwo czasu."
                             :words
                             [#:word{:start 188.18
                                     :is_morphed false
                                     :active false
                                     :id "b76db225-73ad-4d54-b343-26f2311737a2"
                                     :word "Musisz"
                                     :score 0.74
                                     :lemma "musisz"
                                     :norm "musisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 188.88
                                     :pos "VERB"}
                              #:word{:start      188.88
                                     :is_morphed false
                                     :active     false
                                     :id         "e30fdf5b-baa7-4f8b-85d5-31b5fed85dec"
                                     :word       "poświęcić"
                                     :score      0.89
                                     :lemma      "poświęcić"
                                     :norm       "poświęcić"
                                     :morph      "Aspect=Perf|VerbForm=Inf|Voice=Act"
                                     :end        189.56
                                     :pos        "VERB"}
                              #:word{:start      189.56
                                     :is_morphed false
                                     :active     false
                                     :id         "dadc79ff-0bd5-4613-bbbe-ea0e4268b20a"
                                     :word       "na"
                                     :score      0.97
                                     :lemma      "na"
                                     :norm       "na"
                                     :morph      "AdpType=Prep"
                                     :end        189.82
                                     :pos        "ADP"}
                              #:word{:start 189.82
                                     :is_morphed false
                                     :active false
                                     :id "9ca45d2b-d04f-4afe-a19d-048437f3664d"
                                     :word "to"
                                     :score 0.99
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Case=Acc|Gender=Neut|Number=Sing|PronType=Dem"
                                     :end 190.38
                                     :pos "PRON"}
                              #:word{:start      190.38
                                     :is_morphed false
                                     :active     false
                                     :id         "09dfb969-408c-4c07-bad9-11c4f7831eec"
                                     :word       "mnóstwo"
                                     :score      0.79
                                     :lemma      "mnóstwo"
                                     :norm       "mnóstwo"
                                     :morph      "Case=Acc|Gender=Neut|Number=Sing"
                                     :end        190.9
                                     :pos        "NOUN"}
                              #:word{:start 190.9
                                     :is_morphed true
                                     :active false
                                     :id "91645eba-5103-4fbb-87a4-2fa19c656ad4"
                                     :word "czasu."
                                     :score 0.82
                                     :lemma "czas"
                                     :norm "czasu"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Gender=Masc|Number=Sing"
                                     :end 191.72
                                     :pos "NOUN"}]
                             :translations
                             [#:translation{:id "8693c06b-9336-4171-9f80-de5667246121"
                                            :text
                                            "You have to devote a lot of time to it. It's the best and fastest method."
                                            :start 188.18
                                            :end 195.26000000000002
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "913e2e78-c447-4dc0-9cd9-7fda08b4bd12"
                             :start 192.14000000000001
                             :end 195.24
                             :text "To najlepsza i najszybsza metoda."
                             :words
                             [#:word{:start 192.14000000000001
                                     :is_morphed false
                                     :active false
                                     :id "b7cab7b9-32cc-4a69-a3f2-bbfa3219524d"
                                     :word "To"
                                     :score 0.85
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Mood=Ind|Tense=Pres|VerbForm=Fin|VerbType=Quasi"
                                     :end 192.34000000000003
                                     :pos "AUX"}
                              #:word{:start 192.34000000000003
                                     :is_morphed false
                                     :active false
                                     :id "e851aa32-8c43-479d-a1f2-25fea966a86b"
                                     :word "najlepsza"
                                     :score 0.98
                                     :lemma "najlepsza"
                                     :norm "najlepsza"
                                     :morph "Case=Nom|Degree=Sup|Gender=Fem|Number=Sing"
                                     :end 193.02
                                     :pos "ADJ"}
                              #:word{:start      193.02
                                     :is_morphed false
                                     :active     false
                                     :id         "25d1c149-0961-4f3d-ad2a-37697ed9fa9d"
                                     :word       "i"
                                     :score      0.89
                                     :lemma      "i"
                                     :norm       "i"
                                     :morph      ""
                                     :end        193.70000000000002
                                     :pos        "CCONJ"}
                              #:word{:start 193.70000000000002
                                     :is_morphed false
                                     :active false
                                     :id "58cda786-97da-4c3e-a748-0ce891c71e2a"
                                     :word "najszybsza"
                                     :score 0.77
                                     :lemma "najszybsza"
                                     :norm "najszybsza"
                                     :morph "Case=Nom|Degree=Sup|Gender=Fem|Number=Sing"
                                     :end 194.3
                                     :pos "ADJ"}
                              #:word{:start      194.3
                                     :is_morphed false
                                     :active     false
                                     :id         "f916d466-0674-42a1-bd1b-13a2ec799b08"
                                     :word       "metoda."
                                     :score      0.8
                                     :lemma      "metoda"
                                     :norm       "metoda"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        195.24
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "fc9808ec-2f34-402b-93ea-a96b81e0a3a9"
                                            :text "Listen to Polish everywhere."
                                            :start 196.02000000000004
                                            :end 197.76000000000005
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "9d4e2cf4-7f39-49d1-98cf-81617df4986a"
                             :start 196.00000000000003
                             :end 197.76000000000005
                             :text "Słuchaj polskiego wszędzie."
                             :words
                             [#:word{:start 196.00000000000003
                                     :is_morphed true
                                     :active false
                                     :id "8e876994-48fa-44d8-9036-91e84a0fb84a"
                                     :word "Słuchaj"
                                     :score 0.81
                                     :lemma "słuchać"
                                     :norm "słuchaj"
                                     :morph
                                     "Aspect=Imp|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 196.30000000000004
                                     :pos "VERB"}
                              #:word{:start 196.30000000000004
                                     :is_morphed true
                                     :active false
                                     :id "586d6d45-9cea-455b-ab49-33834fb766c6"
                                     :word "polskiego"
                                     :score 0.81
                                     :lemma "polski"
                                     :norm "polskiego"
                                     :morph
                                     "Animacy=Inan|Case=Gen|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 196.80000000000004
                                     :pos "ADJ"}
                              #:word{:start      196.80000000000004
                                     :is_morphed false
                                     :active     false
                                     :id         "27e2a09d-507c-4543-9f49-932f70363312"
                                     :word       "wszędzie."
                                     :score      0.82
                                     :lemma      "wszędzie"
                                     :norm       "wszędzie"
                                     :morph      "PronType=Tot"
                                     :end        197.76000000000005
                                     :pos        "ADV"}]
                             :translations
                             [#:translation{:id "c40635fd-5f64-4876-b000-d45c8d0efad0"
                                            :text "Listen to Polish everywhere."
                                            :start 196.02000000000004
                                            :end 197.76000000000005
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "2bcefd69-7641-448a-b733-bdb913284cb6"
                             :start 198.20000000000007
                             :end 208.4000000000001
                             :text
                             "Zabieraj ze sobą odtwarzacz MP3 i słuchaj w domu, w samochodzie, na spacerze, na siłowni, wszędzie."
                             :words
                             [#:word{:start 198.20000000000007
                                     :is_morphed false
                                     :active false
                                     :id "819c87f2-8016-4316-a6c3-0d8db286f5ba"
                                     :word "Zabieraj"
                                     :score 0.71
                                     :lemma "Zabieraj"
                                     :norm "zabieraj"
                                     :morph
                                     "Aspect=Perf|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 198.88000000000008
                                     :pos "VERB"}
                              #:word{:start      198.88000000000008
                                     :is_morphed true
                                     :active     false
                                     :id         "e2754c42-38a3-4329-9f91-3556b55867ab"
                                     :word       "ze"
                                     :score      0.87
                                     :lemma      "z"
                                     :norm       "ze"
                                     :morph      "AdpType=Prep|Variant=Long"
                                     :end        199.08000000000007
                                     :pos        "ADP"}
                              #:word{:start      199.08000000000007
                                     :is_morphed true
                                     :active     false
                                     :id         "adedbb47-f986-4341-83f4-513762c44635"
                                     :word       "sobą"
                                     :score      0.99
                                     :lemma      "siebie"
                                     :norm       "sobą"
                                     :morph      "Case=Ins|PronType=Prs|Reflex=Yes"
                                     :end        199.52000000000007
                                     :pos        "PRON"}
                              #:word{:start 199.52000000000007
                                     :is_morphed false
                                     :active false
                                     :id "9b82ef63-dd83-4e2e-aab7-3b6f0d6f87e6"
                                     :word "odtwarzacz"
                                     :score 0.35
                                     :lemma "odtwarzacz"
                                     :norm "odtwarzacz"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing"
                                     :end 200.38000000000008
                                     :pos "NOUN"}
                              #:word{:start 200.38000000000008
                                     :is_morphed false
                                     :active false
                                     :id "9ed86349-b5be-4ae7-a76d-2b1f3252e9f6"
                                     :word "MP3"
                                     :score 0.81
                                     :lemma "MP3"
                                     :norm "mp3"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Sing"
                                     :end 201.44000000000005
                                     :pos "PROPN"}
                              #:word{:start      201.44000000000005
                                     :is_morphed false
                                     :active     false
                                     :id         "8b28c89c-b186-4f6d-acad-52a98ee43b77"
                                     :word       "i"
                                     :score      0.79
                                     :lemma      "i"
                                     :norm       "i"
                                     :morph      ""
                                     :end        201.86000000000007
                                     :pos        "CCONJ"}
                              #:word{:start 201.86000000000007
                                     :is_morphed true
                                     :active false
                                     :id "f927f3a6-1886-4329-bb88-e53fc50bacd3"
                                     :word "słuchaj"
                                     :score 0.98
                                     :lemma "słuchać"
                                     :norm "słuchaj"
                                     :morph
                                     "Aspect=Imp|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 202.22000000000006
                                     :pos "VERB"}
                              #:word{:start      202.22000000000006
                                     :is_morphed false
                                     :active     false
                                     :id         "482cf9a6-1cc2-4a13-8563-cf9708330cb0"
                                     :word       "w"
                                     :score      0.97
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        202.44000000000005
                                     :pos        "ADP"}
                              #:word{:start 202.44000000000005
                                     :is_morphed true
                                     :active false
                                     :id "0d36aea3-0028-4bd8-8791-4220aea9ccc3"
                                     :word "domu,"
                                     :score 0.51
                                     :lemma "dom"
                                     :norm "domu"
                                     :morph
                                     "Animacy=Inan|Case=Loc|Gender=Masc|Number=Sing"
                                     :end 203.20000000000007
                                     :pos "NOUN"}
                              #:word{:start      203.20000000000007
                                     :is_morphed false
                                     :active     false
                                     :id         "b3d1fc38-e9c8-42d1-8746-c8981d3e79e5"
                                     :word       "w"
                                     :score      0.94
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        203.24000000000007
                                     :pos        "ADP"}
                              #:word{:start 203.24000000000007
                                     :is_morphed true
                                     :active false
                                     :id "287c2933-86ad-4cfb-b18a-d46d99d1af7d"
                                     :word "samochodzie,"
                                     :score 0.6
                                     :lemma "samochód"
                                     :norm "samochodzie"
                                     :morph
                                     "Animacy=Inan|Case=Loc|Gender=Masc|Number=Sing"
                                     :end 204.44000000000005
                                     :pos "NOUN"}
                              #:word{:start      204.44000000000005
                                     :is_morphed false
                                     :active     false
                                     :id         "0a541816-b051-4f2f-a146-3cdf8c87c7ed"
                                     :word       "na"
                                     :score      0.57
                                     :lemma      "na"
                                     :norm       "na"
                                     :morph      "AdpType=Prep"
                                     :end        204.46000000000006
                                     :pos        "ADP"}
                              #:word{:start 204.46000000000006
                                     :is_morphed true
                                     :active false
                                     :id "a21f923b-82b0-40bc-9b5b-87f3115d90da"
                                     :word "spacerze,"
                                     :score 0.8
                                     :lemma "spacer"
                                     :norm "spacerze"
                                     :morph
                                     "Animacy=Inan|Case=Loc|Gender=Masc|Number=Sing"
                                     :end 205.68000000000006
                                     :pos "NOUN"}
                              #:word{:start      205.68000000000006
                                     :is_morphed false
                                     :active     false
                                     :id         "e2b6b995-26ee-46b4-81f0-ad4a328d5e5a"
                                     :word       "na"
                                     :score      0.77
                                     :lemma      "na"
                                     :norm       "na"
                                     :morph      "AdpType=Prep"
                                     :end        205.84000000000006
                                     :pos        "ADP"}
                              #:word{:start      205.84000000000006
                                     :is_morphed true
                                     :active     false
                                     :id         "f46f115d-bfdc-4797-8a14-d4a608c9ddeb"
                                     :word       "siłowni,"
                                     :score      0.82
                                     :lemma      "siłownia"
                                     :norm       "siłowni"
                                     :morph      "Case=Loc|Gender=Fem|Number=Sing"
                                     :end        207.00000000000006
                                     :pos        "NOUN"}
                              #:word{:start      207.36000000000007
                                     :is_morphed false
                                     :active     false
                                     :id         "495874a7-db70-43e3-b3ee-4b9a4f9e4566"
                                     :word       "wszędzie."
                                     :score      0.7
                                     :lemma      "wszędzie"
                                     :norm       "wszędzie"
                                     :morph      "PronType=Tot"
                                     :end        208.4000000000001
                                     :pos        "ADV"}]
                             :translations
                             [#:translation{:id "9939d5a1-853a-4252-bced-7c105c553d56"
                                            :text
                                            "Take a MP3 player with you and listen at home, in the car, on a walk, at the gym, everywhere."
                                            :start 198.20000000000007
                                            :end 209.0400000000001
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "b77f2386-c257-41e6-b16f-86f6f50d333b"
                             :start 209.0000000000001
                             :end 217.7200000000001
                             :text
                             "Słuchaj łatwych historyjek, słuchaj tej samej historyjki w różnych czasach, słuchaj pytań i odpowiedzi."
                             :words
                             [#:word{:start 209.0000000000001
                                     :is_morphed true
                                     :active false
                                     :id "ae293a2b-9f66-4311-bde2-0a281a85b415"
                                     :word "Słuchaj"
                                     :score 0.92
                                     :lemma "słuchać"
                                     :norm "słuchaj"
                                     :morph
                                     "Aspect=Imp|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 209.68000000000012
                                     :pos "VERB"}
                              #:word{:start 209.68000000000012
                                     :is_morphed true
                                     :active false
                                     :id "e9c64aa8-7db1-4fbb-81e9-72a4c145bfc5"
                                     :word "łatwych"
                                     :score 0.29
                                     :lemma "łatwy"
                                     :norm "łatwych"
                                     :morph "Case=Gen|Degree=Pos|Gender=Fem|Number=Plur"
                                     :end 210.4400000000001
                                     :pos "ADJ"}
                              #:word{:start      210.4400000000001
                                     :is_morphed true
                                     :active     false
                                     :id         "4ec58f87-df11-4c8b-bffb-bd52b94f3fc0"
                                     :word       "historyjek,"
                                     :score      0.39
                                     :lemma      "historyjka"
                                     :norm       "historyjek"
                                     :morph      "Case=Gen|Gender=Fem|Number=Plur"
                                     :end        211.4400000000001
                                     :pos        "NOUN"}
                              #:word{:start 211.4400000000001
                                     :is_morphed true
                                     :active false
                                     :id "a5b02994-e288-40bf-9351-398fe1cea93c"
                                     :word "słuchaj"
                                     :score 1
                                     :lemma "słuchać"
                                     :norm "słuchaj"
                                     :morph
                                     "Aspect=Imp|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 212.1000000000001
                                     :pos "VERB"}
                              #:word{:start 212.1000000000001
                                     :is_morphed true
                                     :active false
                                     :id "dbaec4c0-7d8f-4777-b48f-54adc395e4e1"
                                     :word "tej"
                                     :score 0.45
                                     :lemma "ten"
                                     :norm "tej"
                                     :morph "Case=Gen|Gender=Fem|Number=Sing|PronType=Dem"
                                     :end 212.5800000000001
                                     :pos "DET"}
                              #:word{:start 212.5800000000001
                                     :is_morphed true
                                     :active false
                                     :id "a4782b95-fa1b-46ca-9f4a-96ea3e49146c"
                                     :word "samej"
                                     :score 0.85
                                     :lemma "sam"
                                     :norm "samej"
                                     :morph "Case=Gen|Degree=Pos|Gender=Fem|Number=Sing"
                                     :end 213.12000000000012
                                     :pos "ADJ"}
                              #:word{:start      213.12000000000012
                                     :is_morphed true
                                     :active     false
                                     :id         "3e5388a4-02a2-48aa-843e-57c26aed7018"
                                     :word       "historyjki"
                                     :score      0.83
                                     :lemma      "historyjka"
                                     :norm       "historyjki"
                                     :morph      "Case=Gen|Gender=Fem|Number=Sing"
                                     :end        213.8000000000001
                                     :pos        "NOUN"}
                              #:word{:start      213.8000000000001
                                     :is_morphed false
                                     :active     false
                                     :id         "925bcf3c-0130-4d98-909f-07e03ab85224"
                                     :word       "w"
                                     :score      0.63
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        213.9200000000001
                                     :pos        "ADP"}
                              #:word{:start 213.9200000000001
                                     :is_morphed true
                                     :active false
                                     :id "3c8581df-04a1-4a2f-9943-4f175d99ed86"
                                     :word "różnych"
                                     :score 0.98
                                     :lemma "różny"
                                     :norm "różnych"
                                     :morph
                                     "Animacy=Inan|Case=Loc|Degree=Pos|Gender=Masc|Number=Plur"
                                     :end 214.2400000000001
                                     :pos "ADJ"}
                              #:word{:start 214.2400000000001
                                     :is_morphed true
                                     :active false
                                     :id "ad285f51-ac49-49fb-be0a-ebf0a9e201e5"
                                     :word "czasach,"
                                     :score 0.92
                                     :lemma "czas"
                                     :norm "czasach"
                                     :morph
                                     "Animacy=Inan|Case=Loc|Gender=Masc|Number=Plur"
                                     :end 215.3400000000001
                                     :pos "NOUN"}
                              #:word{:start 215.3400000000001
                                     :is_morphed true
                                     :active false
                                     :id "d594c8b3-2008-4737-b90d-69939e666fbf"
                                     :word "słuchaj"
                                     :score 1
                                     :lemma "słuchać"
                                     :norm "słuchaj"
                                     :morph
                                     "Aspect=Imp|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 216.0800000000001
                                     :pos "VERB"}
                              #:word{:start      216.0800000000001
                                     :is_morphed true
                                     :active     false
                                     :id         "1d9bbc47-21d5-410a-827c-34855ac6295f"
                                     :word       "pytań"
                                     :score      0.68
                                     :lemma      "pytanie"
                                     :norm       "pytań"
                                     :morph      "Case=Gen|Gender=Neut|Number=Plur"
                                     :end        216.8400000000001
                                     :pos        "NOUN"}
                              #:word{:start      216.8400000000001
                                     :is_morphed false
                                     :active     false
                                     :id         "9be3f9b6-d111-4e3b-880c-362fc370f399"
                                     :word       "i"
                                     :score      1
                                     :lemma      "i"
                                     :norm       "i"
                                     :morph      ""
                                     :end        217.0000000000001
                                     :pos        "CCONJ"}
                              #:word{:start      217.0000000000001
                                     :is_morphed true
                                     :active     false
                                     :id         "ba0e72ab-3742-42e7-8031-0f8bc8ba2890"
                                     :word       "odpowiedzi."
                                     :score      0.91
                                     :lemma      "odpowiedź"
                                     :norm       "odpowiedzi"
                                     :morph      "Case=Gen|Gender=Fem|Number=Plur"
                                     :end        217.7200000000001
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "03badf5f-df75-4154-815b-293325af4699"
                                            :text
                                            "Listen to easy stories, listen to the same story at different times, listen to questions and answers."
                                            :start 209.0400000000001
                                            :end 217.5400000000001
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "f66dfc7a-a804-4719-b5bb-960e5c9c0556"
                             :start 218.76000000000002
                             :end 224.12000000000003
                             :text "Słuchaj tylko tego, co rozumiesz przynajmniej w 80%."
                             :words
                             [#:word{:start 218.76000000000002
                                     :is_morphed true
                                     :active false
                                     :id "86b635a1-bf4c-4dfd-ba4a-b128c6b18c6c"
                                     :word "Słuchaj"
                                     :score 0.85
                                     :lemma "słuchać"
                                     :norm "słuchaj"
                                     :morph
                                     "Aspect=Imp|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 219.4
                                     :pos "VERB"}
                              #:word{:start      219.4
                                     :is_morphed false
                                     :active     false
                                     :id         "a8a3e119-d9e7-442d-97cd-d399e76950f5"
                                     :word       "tylko"
                                     :score      0.99
                                     :lemma      "tylko"
                                     :norm       "tylko"
                                     :morph      ""
                                     :end        219.74
                                     :pos        "PART"}
                              #:word{:start 219.74
                                     :is_morphed true
                                     :active false
                                     :id "7b533b5c-ac93-4f25-81cc-78cfd9ffe489"
                                     :word "tego,"
                                     :score 0.66
                                     :lemma "to"
                                     :norm "tego"
                                     :morph
                                     "Case=Gen|Gender=Neut|Number=Sing|PronType=Dem"
                                     :end 220.18
                                     :pos "PRON"}
                              #:word{:start 220.18
                                     :is_morphed false
                                     :active false
                                     :id "ce2c8b1c-8cb3-4a05-8750-0aac68c5bb6b"
                                     :word "co"
                                     :score 0.84
                                     :lemma "co"
                                     :norm "co"
                                     :morph
                                     "Case=Acc|Gender=Neut|Number=Sing|PronType=Rel"
                                     :end 220.38000000000002
                                     :pos "PRON"}
                              #:word{:start 220.38000000000002
                                     :is_morphed true
                                     :active false
                                     :id "63323c8f-7b8a-4a25-9d84-5534b24ec46e"
                                     :word "rozumiesz"
                                     :score 0.94
                                     :lemma "rozumieć"
                                     :norm "rozumiesz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 220.98000000000002
                                     :pos "VERB"}
                              #:word{:start      220.98000000000002
                                     :is_morphed false
                                     :active     false
                                     :id         "6b092cc2-322b-4f0b-89f7-6142b2c22356"
                                     :word       "przynajmniej"
                                     :score      0.69
                                     :lemma      "przynajmniej"
                                     :norm       "przynajmniej"
                                     :morph      ""
                                     :end        222.02
                                     :pos        "PART"}
                              #:word{:start      222.02
                                     :is_morphed false
                                     :active     false
                                     :id         "e0cc6a8b-1ae6-48a8-b7d7-c2ddf2cb23e0"
                                     :word       "w"
                                     :score      0.7
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        222.4
                                     :pos        "ADP"}
                              #:word{:start      222.4
                                     :is_morphed false
                                     :active     false
                                     :id         "40e58751-8a4a-45e5-b436-abc700d20f27"
                                     :word       "80%."
                                     :score      0.35
                                     :lemma      ""
                                     :morph      ""
                                     :end        224.12000000000003
                                     :pos        ""}]
                             :translations
                             [#:translation{:id "a6c853df-aad6-486a-8d42-341dd5d5843b"
                                            :text
                                            "Listen only to what you understand, at least 80%."
                                            :start 218.76000000000002
                                            :end 224.16000000000003
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "cf653b66-b827-4807-b63d-5dbbf9b38442"
                             :start 225.82000000000005
                             :end 229.54000000000005
                             :text "Trzecia moja rada to ogromna intensywność."
                             :words
                             [#:word{:start 225.82000000000005
                                     :is_morphed true
                                     :active false
                                     :id "04a9996a-f227-448e-80e3-cc36578311ac"
                                     :word "Trzecia"
                                     :score 0.76
                                     :lemma "trzeciy"
                                     :norm "trzecia"
                                     :morph "Case=Nom|Degree=Pos|Gender=Fem|Number=Sing"
                                     :end 226.44000000000003
                                     :pos "ADJ"}
                              #:word{:start 226.44000000000003
                                     :is_morphed true
                                     :active false
                                     :id "d7ff5a30-6b52-4f8c-8b50-f2e47340557e"
                                     :word "moja"
                                     :score 0.86
                                     :lemma "mój"
                                     :norm "moja"
                                     :morph
                                     "Case=Nom|Gender=Fem|Number=Sing|Number[psor]=Sing|Person=1|Poss=Yes|PronType=Prs"
                                     :end 226.70000000000005
                                     :pos "DET"}
                              #:word{:start      226.70000000000005
                                     :is_morphed false
                                     :active     false
                                     :id         "aeded699-1c41-4942-a7ed-6664853ad7ad"
                                     :word       "rada"
                                     :score      0.83
                                     :lemma      "rada"
                                     :norm       "rada"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        227.06000000000003
                                     :pos        "NOUN"}
                              #:word{:start 227.06000000000003
                                     :is_morphed false
                                     :active false
                                     :id "b0b4da77-3d24-44cf-9cb1-9c2c2aa23c33"
                                     :word "to"
                                     :score 0.92
                                     :lemma "to"
                                     :norm "to"
                                     :morph
                                     "Mood=Ind|Tense=Pres|VerbForm=Fin|VerbType=Quasi"
                                     :end 227.66000000000003
                                     :pos "AUX"}
                              #:word{:start 227.66000000000003
                                     :is_morphed true
                                     :active false
                                     :id "757afba1-c5e2-45b0-80c1-e58a313fe95e"
                                     :word "ogromna"
                                     :score 0.97
                                     :lemma "ogromny"
                                     :norm "ogromna"
                                     :morph "Case=Nom|Degree=Pos|Gender=Fem|Number=Sing"
                                     :end 228.26000000000005
                                     :pos "ADJ"}
                              #:word{:start      228.26000000000005
                                     :is_morphed false
                                     :active     false
                                     :id         "72c4bc6e-e970-45d5-9c0b-9d606d9d14d9"
                                     :word       "intensywność."
                                     :score      0.88
                                     :lemma      "intensywność"
                                     :norm       "intensywność"
                                     :morph      "Case=Nom|Gender=Fem|Number=Sing"
                                     :end        229.54000000000005
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "1dd8bfd8-a67a-4f94-84d6-f9cb4f17d4a2"
                                            :text
                                            "My third tip is to be extremely intense."
                                            :start 225.86000000000004
                                            :end 229.56000000000003
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "96807626-edce-480c-a735-abcaa12e6086"
                             :start 230.02000000000007
                             :end 238.58000000000004
                             :text
                             "Aby mówić płynnie po polsku w 2-3 miesiące, musisz słuchać lub czytać po polsku bardzo dużo."
                             :words
                             [#:word{:start      230.02000000000007
                                     :is_morphed false
                                     :active     false
                                     :id         "2b12d423-71f4-47f5-ba31-20e9dafd875b"
                                     :word       "Aby"
                                     :score      0.7
                                     :lemma      "aby"
                                     :norm       "aby"
                                     :morph      ""
                                     :end        230.46000000000006
                                     :pos        "SCONJ"}
                              #:word{:start      230.46000000000006
                                     :is_morphed false
                                     :active     false
                                     :id         "aa8962c1-816d-4762-8bd8-5b9776029203"
                                     :word       "mówić"
                                     :score      0.82
                                     :lemma      "mówić"
                                     :norm       "mówić"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        231.40000000000006
                                     :pos        "VERB"}
                              #:word{:start      231.40000000000006
                                     :is_morphed false
                                     :active     false
                                     :id         "f5f2e5a3-4d4f-4fcb-b7e9-83c06c19394a"
                                     :word       "płynnie"
                                     :score      0.95
                                     :lemma      "płynnie"
                                     :norm       "płynnie"
                                     :morph      "Degree=Pos"
                                     :end        231.92000000000004
                                     :pos        "ADV"}
                              #:word{:start      231.92000000000004
                                     :is_morphed false
                                     :active     false
                                     :id         "ac5ba950-dcc7-400f-a887-e0f0ada2ee9c"
                                     :word       "po"
                                     :score      0.56
                                     :lemma      "po"
                                     :norm       "po"
                                     :morph      "AdpType=Prep"
                                     :end        232.08000000000004
                                     :pos        "ADP"}
                              #:word{:start      232.08000000000004
                                     :is_morphed true
                                     :active     false
                                     :id         "c055a991-aa66-427b-be8a-280f08da280b"
                                     :word       "polsku"
                                     :score      0.85
                                     :lemma      "polski"
                                     :norm       "polsku"
                                     :morph      "PrepCase=Pre"
                                     :end        232.66000000000005
                                     :pos        "ADJ"}
                              #:word{:start      232.66000000000005
                                     :is_morphed false
                                     :active     false
                                     :id         "203372e5-0e2f-41e1-9867-c5f16a929ba8"
                                     :word       "w"
                                     :score      0.89
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        232.96000000000006
                                     :pos        "ADP"}
                              #:word{:start      232.96000000000006
                                     :is_morphed false
                                     :active     false
                                     :id         "69da3fb5-5735-4914-8fc5-182eaabe3a51"
                                     :word       "2-3"
                                     :score      0.64
                                     :lemma      ""
                                     :morph      ""
                                     :end        233.46000000000006
                                     :pos        ""}
                              #:word{:start 233.46000000000006
                                     :is_morphed true
                                     :active false
                                     :id "43290bf0-936d-4fa0-918b-b9bc4f6e2e65"
                                     :word "miesiące,"
                                     :score 0.88
                                     :lemma "miesiąc"
                                     :norm "miesiące"
                                     :morph
                                     "Animacy=Inan|Case=Acc|Gender=Masc|Number=Plur"
                                     :end 234.02000000000007
                                     :pos "NOUN"}
                              #:word{:start 234.02000000000007
                                     :is_morphed true
                                     :active false
                                     :id "e6c9d024-ef37-44eb-8327-84afa553481f"
                                     :word "musisz"
                                     :score 0.71
                                     :lemma "musieć"
                                     :norm "musisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 234.34000000000006
                                     :pos "VERB"}
                              #:word{:start      234.34000000000006
                                     :is_morphed false
                                     :active     false
                                     :id         "97a602b7-e4f8-452a-bca6-0a03ed33f776"
                                     :word       "słuchać"
                                     :score      0.85
                                     :lemma      "słuchać"
                                     :norm       "słuchać"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        235.30000000000007
                                     :pos        "VERB"}
                              #:word{:start      235.30000000000007
                                     :is_morphed false
                                     :active     false
                                     :id         "1e994e4a-21af-4b87-90b1-c75bda8f994e"
                                     :word       "lub"
                                     :score      0.94
                                     :lemma      "lub"
                                     :norm       "lub"
                                     :morph      ""
                                     :end        235.52000000000007
                                     :pos        "CCONJ"}
                              #:word{:start      235.52000000000007
                                     :is_morphed false
                                     :active     false
                                     :id         "cdef3924-d3d5-4b43-9e0a-4738ecd59d07"
                                     :word       "czytać"
                                     :score      0.96
                                     :lemma      "czytać"
                                     :norm       "czytać"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        235.92000000000004
                                     :pos        "VERB"}
                              #:word{:start      235.92000000000004
                                     :is_morphed false
                                     :active     false
                                     :id         "a0b58682-a93a-44c0-b0b5-272a723a2a50"
                                     :word       "po"
                                     :score      0.99
                                     :lemma      "po"
                                     :norm       "po"
                                     :morph      "AdpType=Prep"
                                     :end        236.10000000000005
                                     :pos        "ADP"}
                              #:word{:start      236.10000000000005
                                     :is_morphed true
                                     :active     false
                                     :id         "735bc9b8-fbc4-4c7e-bb87-11d8e98abb61"
                                     :word       "polsku"
                                     :score      1
                                     :lemma      "polski"
                                     :norm       "polsku"
                                     :morph      "PrepCase=Pre"
                                     :end        236.60000000000005
                                     :pos        "ADJ"}
                              #:word{:start      236.60000000000005
                                     :is_morphed false
                                     :active     false
                                     :id         "6b5dcaa1-b23d-4991-aa7e-19a055706c7b"
                                     :word       "bardzo"
                                     :score      0.98
                                     :lemma      "bardzo"
                                     :norm       "bardzo"
                                     :morph      "Degree=Pos"
                                     :end        237.26000000000005
                                     :pos        "ADV"}
                              #:word{:start 237.26000000000005
                                     :is_morphed false
                                     :active false
                                     :id "b34351c7-bedf-4f1b-a4fd-b5fcf99ad48d"
                                     :word "dużo."
                                     :score 0.75
                                     :lemma "dużo"
                                     :norm "dużo"
                                     :morph
                                     "Animacy=Hum|Case=Nom|Gender=Masc|NumType=Card|Number=Plur|PronType=Ind"
                                     :end 238.58000000000004
                                     :pos "DET"}]
                             :translations
                             [#:translation{:id "336574d6-533c-4b8e-aea8-d2310f09011f"
                                            :text
                                            "To speak Polish fluently in 2-3 months, you have to listen or read a lot of Polish."
                                            :start 230.04000000000005
                                            :end 238.40000000000006
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "c8f6717c-61a6-4e1e-911c-d070ea589252"
                             :start 239.24000000000007
                             :end 241.70000000000005
                             :text "Język polski musi być wszędzie."
                             :words
                             [#:word{:start 239.24000000000007
                                     :is_morphed false
                                     :active false
                                     :id "9946a03c-922d-4474-b358-ea836582637f"
                                     :word "Język"
                                     :score 0.44
                                     :lemma "Język"
                                     :norm "język"
                                     :morph
                                     "Animacy=Inan|Case=Nom|Gender=Masc|Number=Sing"
                                     :end 239.64000000000004
                                     :pos "NOUN"}
                              #:word{:start 239.64000000000004
                                     :is_morphed false
                                     :active false
                                     :id "30d75925-d67d-4c1d-818d-27b24e347070"
                                     :word "polski"
                                     :score 0.47
                                     :lemma "polski"
                                     :norm "polski"
                                     :morph
                                     "Animacy=Inan|Case=Nom|Degree=Pos|Gender=Masc|Number=Sing"
                                     :end 240.06000000000006
                                     :pos "ADJ"}
                              #:word{:start 240.06000000000006
                                     :is_morphed true
                                     :active false
                                     :id "1b7a4a19-ba02-4180-bf9d-7897282baf5f"
                                     :word "musi"
                                     :score 0.93
                                     :lemma "musieć"
                                     :norm "musi"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 240.30000000000004
                                     :pos "VERB"}
                              #:word{:start      240.30000000000004
                                     :is_morphed false
                                     :active     false
                                     :id         "029ea19e-b246-420c-9333-a280fb8aa608"
                                     :word       "być"
                                     :score      1
                                     :lemma      "być"
                                     :norm       "być"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        240.58000000000004
                                     :pos        "VERB"}
                              #:word{:start      240.58000000000004
                                     :is_morphed false
                                     :active     false
                                     :id         "d306675e-b894-4625-82a2-d0f4879f957e"
                                     :word       "wszędzie."
                                     :score      0.86
                                     :lemma      "wszędzie"
                                     :norm       "wszędzie"
                                     :morph      "PronType=Tot"
                                     :end        241.70000000000005
                                     :pos        "ADV"}]
                             :translations
                             [#:translation{:id "7260a727-b35a-4f5f-a3a7-da517382afd9"
                                            :text "Polish language has to be everywhere."
                                            :start 239.06000000000006
                                            :end 241.64000000000004
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "6da6951f-04f8-47bf-83fe-ac2985e85f3a"
                             :start 242.12000000000006
                             :end 246.96000000000006
                             :text "Codziennie słuchaj i czytaj co najmniej 6-10 godzin."
                             :words
                             [#:word{:start      242.12000000000006
                                     :is_morphed false
                                     :active     false
                                     :id         "9dabea05-a5fb-4b46-a95a-772ffdd75f48"
                                     :word       "Codziennie"
                                     :score      0.43
                                     :lemma      "codziennie"
                                     :norm       "codziennie"
                                     :morph      "Degree=Pos"
                                     :end        242.68000000000006
                                     :pos        "ADV"}
                              #:word{:start 242.68000000000006
                                     :is_morphed true
                                     :active false
                                     :id "3c92b723-c028-4a72-b892-d0e673f51547"
                                     :word "słuchaj"
                                     :score 0.92
                                     :lemma "słuchać"
                                     :norm "słuchaj"
                                     :morph
                                     "Aspect=Imp|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 243.42000000000007
                                     :pos "VERB"}
                              #:word{:start      243.42000000000007
                                     :is_morphed false
                                     :active     false
                                     :id         "59668484-3613-4ac5-b26a-55ee6c7dc3d2"
                                     :word       "i"
                                     :score      0.95
                                     :lemma      "i"
                                     :norm       "i"
                                     :morph      ""
                                     :end        243.68000000000006
                                     :pos        "CCONJ"}
                              #:word{:start 243.68000000000006
                                     :is_morphed true
                                     :active false
                                     :id "4eb60f18-f4a1-40e4-92b4-2b67f524ff83"
                                     :word "czytaj"
                                     :score 0.79
                                     :lemma "czytać"
                                     :norm "czytaj"
                                     :morph
                                     "Aspect=Imp|Mood=Imp|Number=Sing|Person=2|VerbForm=Fin|Voice=Act"
                                     :end 244.14000000000007
                                     :pos "VERB"}
                              #:word{:start      244.14000000000007
                                     :is_morphed false
                                     :active     false
                                     :id         "5501febd-1880-4f34-bd9e-dd4a454f1b12"
                                     :word       "co"
                                     :score      0.43
                                     :lemma      "co"
                                     :norm       "co"
                                     :morph      ""
                                     :end        244.30000000000007
                                     :pos        "PART"}
                              #:word{:start      244.30000000000007
                                     :is_morphed true
                                     :active     false
                                     :id         "1aa8dcf4-d58a-4495-889a-798acc70476f"
                                     :word       "najmniej"
                                     :score      0.99
                                     :lemma      "mało"
                                     :norm       "najmniej"
                                     :morph      "Degree=Sup"
                                     :end        244.80000000000007
                                     :pos        "ADV"}
                              #:word{:start      244.80000000000007
                                     :is_morphed false
                                     :active     false
                                     :id         "8a6869e7-890a-463a-b478-38402fe39734"
                                     :word       "6-10"
                                     :score      0.72
                                     :lemma      ""
                                     :morph      ""
                                     :end        246.42000000000007
                                     :pos        ""}
                              #:word{:start      246.42000000000007
                                     :is_morphed true
                                     :active     false
                                     :id         "0cfb430c-28d3-4102-b819-3d5293316dc3"
                                     :word       "godzin."
                                     :score      0.9
                                     :lemma      "godzina"
                                     :norm       "godzin"
                                     :morph      "Case=Gen|Gender=Fem|Number=Plur"
                                     :end        246.96000000000006
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "e6198831-43d3-4b5b-a27b-879b7f2211fd"
                                            :text "Polish language has to be everywhere."
                                            :start 239.06000000000006
                                            :end 241.64000000000004
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "0c103a85-c1bf-4c46-ba3c-8ab587f7a57b"
                             :start 247.46
                             :end 251.54000000000002
                             :text "Musisz słuchać, a potem czytać to samo."
                             :words
                             [#:word{:start 247.46
                                     :is_morphed false
                                     :active false
                                     :id "c4662b6b-ada5-404f-853d-ebf035f66927"
                                     :word "Musisz"
                                     :score 0.77
                                     :lemma "musisz"
                                     :norm "musisz"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=2|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 248.16
                                     :pos "VERB"}
                              #:word{:start      248.16
                                     :is_morphed false
                                     :active     false
                                     :id         "25d81ca5-04b9-4890-98f0-8e677dd5e631"
                                     :word       "słuchać,"
                                     :score      0.81
                                     :lemma      "słuchać"
                                     :norm       "słuchać"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        249.46
                                     :pos        "VERB"}
                              #:word{:start      249.46
                                     :is_morphed false
                                     :active     false
                                     :id         "66dbabcb-0eb0-4672-ba78-8b9e04bae468"
                                     :word       "a"
                                     :score      0.98
                                     :lemma      "a"
                                     :norm       "a"
                                     :morph      ""
                                     :end        249.56
                                     :pos        "CCONJ"}
                              #:word{:start      249.56
                                     :is_morphed false
                                     :active     false
                                     :id         "b1ae9d38-3870-4ba6-94ff-58cebac245e0"
                                     :word       "potem"
                                     :score      1
                                     :lemma      "potem"
                                     :norm       "potem"
                                     :morph      ""
                                     :end        249.88
                                     :pos        "ADV"}
                              #:word{:start      249.88
                                     :is_morphed false
                                     :active     false
                                     :id         "eb2b2962-87ac-4b5c-a67b-671bd96d1f16"
                                     :word       "czytać"
                                     :score      0.85
                                     :lemma      "czytać"
                                     :norm       "czytać"
                                     :morph      "Aspect=Imp|VerbForm=Inf|Voice=Act"
                                     :end        250.42000000000002
                                     :pos        "VERB"}
                              #:word{:start 250.42000000000002
                                     :is_morphed true
                                     :active false
                                     :id "c0f92a48-7dab-4e8b-9214-a1f98cf99be1"
                                     :word "to"
                                     :score 0.98
                                     :lemma "ten"
                                     :norm "to"
                                     :morph
                                     "Case=Acc|Gender=Neut|Number=Sing|PronType=Dem"
                                     :end 250.68
                                     :pos "PRON"}
                              #:word{:start 250.68
                                     :is_morphed true
                                     :active false
                                     :id "091bd1aa-9b36-4b82-bf7d-e8d77a36f624"
                                     :word "samo."
                                     :score 0.79
                                     :lemma "sam"
                                     :norm "samo"
                                     :morph "Case=Acc|Degree=Pos|Gender=Neut|Number=Sing"
                                     :end 251.54000000000002
                                     :pos "ADJ"}]
                             :translations
                             [#:translation{:id "13902c1e-c106-4313-a10e-17fd7b594352"
                                            :text "It helps a lot in understanding."
                                            :start 251.5
                                            :end 253.76000000000002
                                            :lang "en"
                                            :visible? false}]}
                   #:segment{:id "7a8f1484-a93d-4477-9cd3-210f426a771c"
                             :start 251.54000000000002
                             :end 254.1
                             :text "To doskonale pomaga w zrozumieniu."
                             :words
                             [#:word{:start      251.54000000000002
                                     :is_morphed false
                                     :active     false
                                     :id         "07401e0a-16c3-45d0-a73b-236b397ae86d"
                                     :word       "To"
                                     :score      0.93
                                     :lemma      "to"
                                     :norm       "to"
                                     :morph      ""
                                     :end        251.68
                                     :pos        "PART"}
                              #:word{:start      251.68
                                     :is_morphed false
                                     :active     false
                                     :id         "cb5341be-9fa9-49bb-946a-e2b4d07bd952"
                                     :word       "doskonale"
                                     :score      0.83
                                     :lemma      "doskonale"
                                     :norm       "doskonale"
                                     :morph      "Degree=Pos"
                                     :end        252.46
                                     :pos        "ADV"}
                              #:word{:start 252.46
                                     :is_morphed true
                                     :active false
                                     :id "8fa60935-4f68-45c5-b0af-bd36e7d25b9b"
                                     :word "pomaga"
                                     :score 0.86
                                     :lemma "pomagać"
                                     :norm "pomaga"
                                     :morph
                                     "Aspect=Imp|Mood=Ind|Number=Sing|Person=3|Tense=Pres|VerbForm=Fin|Voice=Act"
                                     :end 252.9
                                     :pos "VERB"}
                              #:word{:start      252.9
                                     :is_morphed false
                                     :active     false
                                     :id         "ee1960d3-e0d6-4335-abb3-99492c48f758"
                                     :word       "w"
                                     :score      0.43
                                     :lemma      "w"
                                     :norm       "w"
                                     :morph      "AdpType=Prep|Variant=Short"
                                     :end        253.08
                                     :pos        "ADP"}
                              #:word{:start      253.08
                                     :is_morphed true
                                     :active     false
                                     :id         "b4e3419c-0769-4799-bc1b-1ddd53912565"
                                     :word       "zrozumieniu."
                                     :score      0.95
                                     :lemma      "zrozumienie"
                                     :norm       "zrozumieniu"
                                     :morph      "Case=Loc|Gender=Neut|Number=Sing"
                                     :end        254.1
                                     :pos        "NOUN"}]
                             :translations
                             [#:translation{:id "b53be416-dbb3-411c-a94f-3ef9d3efc41a"
                                            :text "It helps a lot in understanding."
                                            :start 251.5
                                            :end 253.76000000000002
                                            :lang "en"
                                            :visible? false}]} ...]
                  :>/transcript-switcher
                  #:transcript-switcher{:all-transcripts
                                        [#:transcript{:id
                                                      "dc51793c-5f09-4f99-8cc0-bc4bf38fce2a"
                                                      :label "Freediving.mp3"}
                                         #:transcript{:id
                                                      "8089db3b-d8dd-4b23-8923-3d065ed8ea3b"
                                                      :label
                                                      "091C-DailyPolishStory-QA.mp3"}
                                         #:transcript{:id
                                                      "f7017546-4a9e-4529-930f-e1f9e1bbe8c4"
                                                      :label "realpolish-hint1.mp3"}
                                         #:transcript{:id
                                                      "651ecb09-93d9-4099-9431-6ffbd54fe3e4"
                                                      :label
                                                      "090C-DailyPolishStory-QA.mp3"}]}
                  :>/player #:transcript{:id "f7017546-4a9e-4529-930f-e1f9e1bbe8c4"
                                         :audio-filename
                                         "/audio_and_transcript/realpolish-hint1.mp3"}
                  :>/player-controls
                  {:transcript/id       "f7017546-4a9e-4529-930f-e1f9e1bbe8c4"
                   :ui-player/doing     :loading
                   :ui-player/scroll-to-active true
                   :>/language-controls {:transcript/id
                                         "f7017546-4a9e-4529-930f-e1f9e1bbe8c4"
                                         :ui-translation-controls/languages
                                         [#:ui-translation-control{:language "en"
                                                                   :visible-translations?
                                                                   false}]}}}})
# Transcript Editor for editing AI generated

I'm very much enjoying my exploration of [fulcro](https://github.com/fulcrologic/fulcro) thanks Tony Kay for all the work you have put into this interesting and powerful approach to web application development. I thought I would start documenting my progress here, it'll be fun and motivating to share the process, writing will help me to clarify my thinking and I expect I will get some helpful and much-needed feedback. As a starter project to learn the ropes, I am building a nice UI for a subtitle editing app, code is here. I am getting word-level time stamps from whisperx that I have set up as a web service. I want to:

* visualise the audio waveform and the timestamps with https://wavesurfer.xyz/ 
* be able to edit timestamps with their regions plugin which allows dragging of borders of the regions 
* to edit the words themselves in an interactive transcript displayed below the waveform.

This is a first step towards making a web application where a language learner can upload any audio file or link to a youtube video and get editable interactive transcripts useful for language learning with grammatical analysis provided by [spacy](https://spacy.io) I have a prototype of the interactive transcripts I want to achieve here coded in python: [prototype](https://submergedstructure.github.io/Polish%20Dialogues.html)

@Jakub Holý (HolyJak) is going to be doing code review along the way and has been giving me some tips.

I'm using Jakub's minimalist backendless fulcro starter to initially concentrate on the frontend and am also using his troubleshooting library.

See the code running here: [automatic gihtub pages deploy](https://dev.submergedstructure.com/)

## Todo

* I need to check the code selecting the currently active word based on the current time reported by the player. Seems it some cases it is selecting the wrong word.
* I need to make the timestamps editable.
* I need to make the words themselves editable.
* I'd like to add a button that toggles the scroll to current word as the current word changes, defaulting to on.
* Ability to move forward and backward by segments or words as well as moving the audio forward / backward by 5 seconds.
* Add keyboard shortcuts.
* Add communication to a server to save edited transcripts.
* Add download of trnanscripts in various formats.
* Add a form to upload an audio file or link to an audio file or youtube video or similar and have the audio transcribed by whisperx through [my web service](https://github.com/submergedstructure/runpodWhisperx).
  * Add ability to specify instructions for whisperx about transcription and control other parameters of whisperx.
  * Add ability to rerun time stamping to generate new time stamps based on changed words in transcript.


## Based on minimalist-fulcro-template-backendless

A template for starting a new, frontend-only Fulcro application with in-browser Pathom. Intended for playing with and learning Fulcro, not for production apps, and therefore simpler than the official [fulcro-template](https://github.com/fulcrologic/fulcro-template). It is a good starting point for your learning projects that is hopefully simple enough for you to understand.

For a template that has an actual backend, see [minimalist-fulcro-template](https://github.com/holyjak/minimalist-fulcro-template).


## Note on alternatives for Pathom 3 and Fulcro Inspect's Index Explorer

Fulcro Inspect's Index Explorer is not compatible with Pathom 3. You need to use a standalone Pathom Viz ([see my article for details](https://blog.jakubholy.net/2023/pathom-viz-for-fulcro-pathom3/).). What you need to do then is to 

1. [download Pathom Viz app](https://github.com/wilkerlucio/pathom-viz/releases)
2. Make `com.submerged-structure.pathom/enable-pathom-viz` true
3. Reload your app and connect P. Viz to it

P. Viz support is off by default because otherwise it [logs tons of errors](https://clojurians.slack.com/archives/C87NB2CFN/p1696016550457039) and warnings, until you connect it.

## Explanation

You will run shadow-cljs, which will watch, compile, and update the sources and also run a HTTP server to serve the application.

## GitHub pages automatic deployment

A GitHub workflows action in .github/workflows/main.yml automatically deploys the code to github pages in order. If you clone this repo you just need to go to the settings page of your github repository and select the "Pages" subpage and make sure the Build and Deployment source is set to "Github Actions".

My repo https://github.com/submergedstructure/transcript-editor deploys to https://dev.submergedstructure.com/


## Usage

### Alternative 1: run locally

Prerequisites: [same as shadow-cljs'](https://github.com/thheller/shadow-cljs#requirements).

First, install frontend dependencies via npm, yarn, or similar:

    npm install # or yarn install # reportedly yarn < v3

then start the application either via

    npx shadow-cljs watch main

or, if you have [Babashka](https://babashka.org/) installed, via

    bb run

NOTE: For Calva, do instead start the REPL from the editor - [run Jack-in](https://calva.io/connect/#jack-in-let-calva-start-the-repl-for-you), selecting _shadow-cljs_ then the `:main` build. Remember to load the page in the browser, see below.

Finally, navigate to http://localhost:8000 and, _after that_, connect to the shadow-cljs nREPL at port 9001\* and switch to the browser REPL by evaluating `(shadow/repl :main)` (Calva does the latter for you).

### Alternative 2: Run with VS Code Dev Containers

If you opened the cloned repo in VS Code, it will offer you to open it using Dev Containers,
i.e. inside a pre-configured Docker instance (provided that you have Docker installed).
This may be a great option if you run into problems that could be caused by the environment.

It will take a while (to build the container for the first time). Eventially, a _Terminal_ should open, telling you that everything is fine and to press any key. After you do that, a normal terminal will open, where you should execute shadow-cljs:

```
vscode ➜ /workspaces/minimalist-fulcro-template-backendless (main) $ npx shadow-cljs watch main
```

(Or you could just run Calva Jack-in and select shadow-cljs and the :main build.)

Eventually, Code will inform you that "Your application running on port 8000 is available.", which you can agree to. (Ignore the previous message about 9001, that is nrepl). Then wait for the terminal to show `[:main] Build completed.` and reload the web page. You should be now able to _Calva: Connect to a Running REPL Server in the Project_. When asked, select `shadow-cljs` as the kind of the repl and then the `:main` build.

### Create a standalone build

You can also compile the sources into a directory via

    npx shadow-cljs release main
    # or: bb build

and then serve the whole website using a HTTP server, e.g.:

    cd resources/public/
    python3 -m http.server 8000

## Why is this not suitable for production?

No thought was given to security, performance, monitoring, error tracking and other important production concerns. It also bakes in fulcro-troubleshooting, which you do not want unnecessarily increasing your bundle size in production settings. So if you want to use the template as a starting point for a production application, you will need to add those yourself.

## TODO

* Can we display an error in the UI when we remove the `i-fail` resolver from Pathom? Currently it returns `::p/errors ::p/not-found`, which Fulcro ignores

## License

Copyleft © 2021 Jakub Holý

Distributed under the [Unlicense](https://unlicense.org/).

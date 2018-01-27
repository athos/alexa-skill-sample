(defproject alexa-skill-sample "0.1.0-SNAPSHOT"
  :description "A sample project to develop an Alexa Skill in ClojureScript"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [kitchen-async "0.1.0-SNAPSHOT"]]

  :plugins [[lein-cljsbuild "1.1.7"]]

  :cljsbuild {:builds
              [{:id "app"
                :source-paths ["src"]
                :compiler {:output-to "target/app/alexa_skill_sample.js"
                           :output-dir "target/app"
                           :main alexa-skill-sample.main
                           :optimizations :simple
                           :target :nodejs
                           :npm-deps {"alexa-sdk" "^1.0.25"
                                      "@slack/client" "3.15.0"}
                           :install-deps true}}]}

  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.2"]]}}
  )

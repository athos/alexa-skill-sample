(ns alexa-skill-sample.main
  (:require [alexa-sdk :as alexa]))

(enable-console-print!)

(defn main []
  (println "Hello, World!"))

(set! *main-cli-fn* main)

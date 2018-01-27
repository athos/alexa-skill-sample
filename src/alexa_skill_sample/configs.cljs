(ns alexa-skill-sample.configs
  (:require [goog.object :as obj]
            process))

(defn- ensure-env-var [var-name]
  (let [val (obj/get process/env (name var-name))]
    (assert (not (nil? val))
            (str "environment variable " var-name " has not been set"))
    val))

(def SLACK_ACCESS_TOKEN
  (ensure-env-var "SLACK_ACCESS_TOKEN"))

(def SLACK_CHANNEL_ID
  (ensure-env-var "SLACK_CHANNEL_ID"))

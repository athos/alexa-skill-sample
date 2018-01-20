(ns alexa-skill-sample.main
  (:require [alexa-sdk :as alexa]))

(enable-console-print!)

(def APP_ID js/undefined)

(def handlers
  {:LaunchRequest (fn [this]
                    (.emit this ":tell" "こんにちは"))
   :Unhandled (fn [this]
                (.emit this ":tell" "すみません、よく分かりません。"))
   :AMAZON.HelpIntent (fn [this]
                        (.emit this ":tell" "ヘルプはありません"))
   :AMAZON.CancelIntent (fn [this]
                          (.emit this "AMAZON.StopIntent"))
   :AMAZON.StopIntent (fn [this]
                        (.emit this ":tell" "さようなら"))})

(defn map->handlers [m]
  (clj->js (into {} (map (fn [[k f]] [k #(this-as this (f this))])) m)))

(defn main [event context callback]
  (let [alexa (alexa/handler event context callback)]
    (set! (.-appId alexa) APP_ID)
    (.registerHandlers alexa (map->handlers handlers))
    (.execute alexa)))

(set! (.-handler js/exports) main)

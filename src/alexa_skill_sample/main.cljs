(ns alexa-skill-sample.main
  (:require [alexa-sdk :as alexa]
            [alexa-skill-sample.messages.ja-jp :as ja-jp]))

(enable-console-print!)

(def APP_ID js/undefined)

(def lang-strs
  #js{:ja-JP #js{:translation ja-jp/messages}})

(def handlers
  {:LaunchRequest (fn [this]
                    (.emit this ":ask" (.t this "DESCRIPTION") ""))
   :BusinessDayIntent (fn [this]
                        (.emit this ":ask" (.t this "BUSINESS_DAY_INSTRUCTION") ""))
   :FinishIntent (fn [this]
                   (.emit this "AMAZON.StopIntent"))
   :Unhandled (fn [this]
                (.emit this ":ask"
                       (.t this "SORRY_MESSAGE")
                       (.t this "TRY_AGAIN_MESSAGE")))
   :AMAZON.HelpIntent (fn [this]
                        (.emit this ":ask" (.t this "HELP_MESSAGE") ""))
   :AMAZON.CancelIntent (fn [this]
                          (.emit this "AMAZON.StopIntent"))
   :AMAZON.StopIntent (fn [this]
                        (.emit this ":tell" (.t this "THANKS_MESSAGE")))})

(defn map->handlers [m]
  (clj->js (into {} (map (fn [[k f]] [k #(this-as this (f this))])) m)))

(defn main [event context callback]
  (let [alexa (alexa/handler event context callback)]
    (set! (.-appId alexa) APP_ID)
    (set! (.-resources alexa) lang-strs)
    (.registerHandlers alexa (map->handlers handlers))
    (.execute alexa)))

(set! (.-handler js/exports) main)

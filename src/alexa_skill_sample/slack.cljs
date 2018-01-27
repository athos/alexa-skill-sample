(ns alexa-skill-sample.slack
  (:require ["@slack/client" :as slack]
            [alexa-skill-sample.configs :as configs]
            [clojure.core.async :as a]
            [kitchen-async.promise :as p]
            kitchen-async.promise.from-channel))

(defn- make-client [callbacks]
  (let [rtm (slack/RtmClient. configs/SLACK_ACCESS_TOKEN
                              #js{:dataStore false, :useRtmConnect true})]
    (reduce-kv (fn [rtm event callback]
                 (.on rtm event (fn [& args] (apply callback rtm args))))
               rtm
               callbacks)))

(defn- callbacks [ch message]
  {slack/CLIENT_EVENTS.RTM.RTM_CONNECTION_OPENED
   (fn [rtm]
     (.sendMessage rtm message configs/SLACK_CHANNEL_ID))
   slack/RTM_EVENTS.MESSAGE
   (fn [rtm message]
     (when (= (.-channel message) configs/SLACK_CHANNEL_ID)
       (a/put! ch message)))})

(defn prompt [message timeout-ms]
  (let [ch (a/chan)
        rtm (make-client (callbacks ch message))]
    (p/let [msg (p/try
                  (.start rtm)
                  (p/race [ch (p/timeout timeout-ms)])
                  (p/catch :default e
                    (when (.-connected rtm)
                      (.disconnect rtm))
                    (throw e)))]
      (.disconnect rtm)
      (some-> msg .-text))))

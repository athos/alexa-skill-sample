(ns alexa-skill-sample.handlers.congestion-check
  (:require [alexa-skill-sample.handlers.core :as h :refer [defhandler]]
            [alexa-skill-sample.slack :as slack]
            [kitchen-async.promise :as p]))

(def SLACK_TIMEOUT 8000)

(def SLACK_MESSAGE
  (str "現在の混雑状況を次の中から回答して下さい：\n"
       "1. 席はほとんど空いています\n"
       "2. 席は3割程埋まっています\n"
       "3. 席は半分以上埋まっています\n"
       "4. 席はほぼ満席です"))

(defhandler :CongestionCheckIntent
  (p/let [msg (slack/prompt SLACK_MESSAGE SLACK_TIMEOUT)
          choice (some->> msg (re-matches #"[1-4]"))]
    (if-not choice
      (h/emit ":tell" "すみません、現在の混雑状況は分かりませんでした。しばらく待ってから再度ご確認下さい。")
      (h/emit ":tell"
              (str "現在、席は"
                   (case (js/parseInt choice)
                     1 "ほとんど空いています"
                     2 "3割程埋まっています"
                     3 "半分以上埋まっています"
                     4 "ほぼ満席です"))))))

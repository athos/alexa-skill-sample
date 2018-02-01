(ns alexa-skill-sample.handlers.congestion-check
  (:require [alexa-skill-sample.handlers.core :as h :refer [defhandler]]))

(defhandler :CongestionCheckIntent
  (h/emit ":tell" "現在の混雑状況ですね。"))

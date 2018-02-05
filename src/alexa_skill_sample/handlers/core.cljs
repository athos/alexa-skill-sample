(ns alexa-skill-sample.handlers.core
  (:require-macros [alexa-skill-sample.handlers.core]))

(def %handlers (atom {}))

(defn handlers []
  (clj->js @%handlers))

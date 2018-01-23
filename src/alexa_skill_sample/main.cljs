(ns alexa-skill-sample.main
  (:require [alexa-sdk :as alexa]
            [alexa-skill-sample.handlers]
            [alexa-skill-sample.handlers.core :as handlers]
            [alexa-skill-sample.messages.ja-jp :as ja-jp]))

(enable-console-print!)

(def APP_ID js/undefined)

(def lang-strs
  #js{:ja-JP #js{:translation ja-jp/messages}})

(defn main [event context callback]
  (let [alexa (alexa/handler event context callback)]
    (set! (.-appId alexa) APP_ID)
    (set! (.-resources alexa) lang-strs)
    (.registerHandlers alexa (handlers/handlers))
    (.execute alexa)))

(set! (.-handler js/exports) main)

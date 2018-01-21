(ns alexa-skill-sample.main
  (:require [alexa-sdk :as alexa]))

(enable-console-print!)

(def APP_ID js/undefined)

(def handlers
  {:LaunchRequest (fn [this]
                    (.emit this ":ask" "コワーキングスペース、ハレイクについてご案内します。メニューを知りたい場合はヘルプ、とお伝え下さい。終了する場合は終了、とお伝え下さい。" ""))
   :BusinessDayIntent (fn [this]
                        (.emit this ":ask" "ハレイクは水曜以外のすべての曜日で営業しています。"))
   :FinishIntent (fn [this]
                   (.emit this "AMAZON.StopIntent"))
   :Unhandled (fn [this]
                (.emit this ":ask" "すみません、よく分かりませんでした。" "もう一度メニューをお選び下さい。"))
   :AMAZON.HelpIntent (fn [this]
                        (.emit this ":ask" "ご利用可能なメニューは次のとおりです。「営業日」でハレイクの営業日の確認ができます。" ""))
   :AMAZON.CancelIntent (fn [this]
                          (.emit this "AMAZON.StopIntent"))
   :AMAZON.StopIntent (fn [this]
                        (.emit this ":tell" "ご利用ありがとうございました"))})

(defn map->handlers [m]
  (clj->js (into {} (map (fn [[k f]] [k #(this-as this (f this))])) m)))

(defn main [event context callback]
  (let [alexa (alexa/handler event context callback)]
    (set! (.-appId alexa) APP_ID)
    (.registerHandlers alexa (map->handlers handlers))
    (.execute alexa)))

(set! (.-handler js/exports) main)

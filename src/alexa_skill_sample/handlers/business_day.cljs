(ns alexa-skill-sample.handlers.business-day
  (:require [alexa-skill-sample.handlers.core :as h :refer [defhandler]]
            [kitchen-async.promise :as p]))

(defhandler :BusinessDayIntent
  (h/emit ":ask" (h/t "BUSINESS_DAY_INSTRUCTION") ""))

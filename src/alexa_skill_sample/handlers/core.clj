(ns alexa-skill-sample.handlers.core)

(defmacro defhandler [intent & body]
  `(defmethod handle-intent ~intent [_#]
     ~@body))

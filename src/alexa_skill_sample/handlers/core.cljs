(ns alexa-skill-sample.handlers.core
  (:require-macros [alexa-skill-sample.handlers.core]))

(defmulti handle-intent identity)

(def ^:private ^:dynamic *self*)

(defn emit
  ([x] (.emit *self* x))
  ([x y] (.emit *self* x y))
  ([x y z] (.emit *self* x y z)))

(defn t [x]
  (.t *self* x))

(defn handlers []
  (->> (methods handle-intent)
       (into {} (map (fn [[k f]]
                       [k #(this-as this
                             (binding [*self* this] (f k)))])))
       clj->js))

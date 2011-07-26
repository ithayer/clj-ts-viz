(ns clj-ts-viz.model.kmeans
  (:use clj-ts-viz.model.process))

(defn num-to-keyword
  [x]
  (keyword (str x)))



;; testing only   
(require '[clj-ts-viz.data :as data])
(def data (data/gen-dataset))
(def val (get-slopes data))
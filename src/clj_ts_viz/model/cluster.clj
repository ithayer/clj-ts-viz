(ns clj-ts-viz.model.cluster  
  (:use (clj-ts-viz.model qpart)))

(defn add-cluster-col
  "Top level function that takes a dataset and returns a new dataset with a new column of cluster label"
  [coll n]
  (quantile-partition coll n))

;; testing only   
;(require '[clj-ts-viz.data :as data])
;(def data (data/gen-dataset))
;(def a (add-cluster-col data 3))
;(map :cluster a)
(ns clj-ts-viz.model.cluster  
  (:use (clj-ts-viz.model kmeans qpart)))

(comment
  
(defn add-cluster-col
  "Top level function that takes a dataset and returns a new dataset with a new column of cluster label"
  [coll n]
  (quantile-partition coll n))

) ; end of comment

(defn- add-cluster-col
  [f]
  (fn [coll n]
    (f coll n)))

;; implementations
(def add-kmeans-cluster (add-cluster-col kmeans-cluster))
(def add-quantile-partition (add-cluster-col quantile-partition))

;; TODO check correlation of different implementations with magic number or test set

;; testing only   
(require '[clj-ts-viz.data :as data])
(def data (data/gen-dataset))
(def k (add-kmeans-cluster data 3))
(map :cluster k)
(def q (add-quantile-partition data 3))
(map :cluster q)
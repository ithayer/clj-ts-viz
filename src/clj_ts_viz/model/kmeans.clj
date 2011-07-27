(ns clj-ts-viz.model.kmeans
  "k-means clustering"
  (:use clj-ts-viz.model.process)
  (:use [clojure.contrib.math :only (floor)])
  (:use (incanter [stats :only (mean)])))

(defn- random-parition
  "Randomly assign a cluster for initialization"
  [coll n]
  (let [[cell & more]		coll
        rand-clus				(floor (rand n))]
    (while (seq cell)
      (cons (set-cluster rand-clus cell) (random-parition more n)))))

(defn- cluster-mean
  "Calculate the mean for a given cluster"
  [coll cluster]
  (let [subset	(filter #(= (:cluster %) cluster) coll)
        slopes	(get-slopes subset)]		; TODO generalize
    (mean slopes)))

;; TODO (i) slopes-only, (ii) slopes and means, (iii) generalize 

(comment

(defn kmeans-cluster
  "Takes a dataset and returns a new dataset with a new column of cluster label using quantile paritioning"
  [coll n]
  (let [slopes		(get-slopes coll)		; TODO abstract out to generalize
        quantiles	(get-quantiles slopes n)]
    (map (partial set-quantile-partition quantiles) coll)))

) ; end of comment

;; testing only   
(require '[clj-ts-viz.data :as data])
(def data (data/gen-dataset))
(def xs (get-slopes data))
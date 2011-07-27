(ns clj-ts-viz.model.kmeans
  "k-means clustering"
  (:use clj-ts-viz.model.process)
  (:use (incanter [core :only (abs pow sqrt)]
          				[stats :only (mean)])))

(defn- random-parition
  "Randomly assign a cluster for initialization"
  [coll n]
  (let [[cell & more]		coll
        rand-clus				(int (rand n))]
    (if (seq cell)
      (cons (set-cluster rand-clus cell) (random-parition more n))
      '())))

;; TODO k-means on (i) slopes-only (done), (ii) slopes and means, (iii) slopes, means, volatility?

(defrecord ClusterCentroid [id slopes-mean])

(defn- cluster-centroid
  "Calculate the centroid for a given cluster"
  [coll cluster]
  (let [subset	(filter #(= (:cluster %) cluster) coll)
        slopes	(get-slopes subset)]		; TODO abstract out to generalize
    (ClusterCentroid. cluster (mean slopes))))

(defn- all-cluster-centroids
  "Get centroid node for every cluster"
  [coll]
  (let [cluster-set		(set (map :cluster coll))]
    (for [c	cluster-set]
      (cluster-centroid coll c))))

(defn- euclid-distance
  ([a b]					(abs (- a b)))
  ([x1 y1 x2 y2]	(sqrt (+ (pow (- x1 x2) 2) 
                         	 (pow (- y1 y2) 2)))))	; Eclidean distance

(defn- distance-from-centroid
  [cell cs-node]
  (let [cell-slope		(get-slope-memo cell)		; TODO abstract out to generalize
        cluster-slope	(:slopes-mean cs-node)]
    (euclid-distance cell-slope cluster-slope)))

(defn- closest-cluster
  "Returns the closest cluster given a set of means"
  [cell cc-nodes]
	{:pre [(seq cc-nodes)]}
 (:id (first (sort-by #(distance-from-centroid cell %) cc-nodes))))

(defn- reset-to-closest-cluster  
  [coll]
  {:post [(= (count coll) (count %))]}
  (let [cc-nodes		(all-cluster-centroids coll)]
    (map #(set-cluster (closest-cluster % cc-nodes) %) coll)))

(defn- update 
  [coll]
  (let [updated		(reset-to-closest-cluster	coll)]
    (if (= coll updated)
      updated				; convergence
      (update updated))))

(defn kmeans-cluster
  "Takes a dataset and returns a new dataset with a new column of cluster label using k-means"
  [coll n]
  (let [init	(random-parition coll n)]
    (update init)))

;; testing only   
;(require '[clj-ts-viz.data :as data])
;(def data (data/gen-dataset))
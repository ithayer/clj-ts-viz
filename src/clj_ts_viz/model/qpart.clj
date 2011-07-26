(ns clj-ts-viz.model.qpart
  (:use clj-ts-viz.model.process)
  (:use (incanter [stats :only (quantile)])))

(defn- get-quantiles
  "Given a list of values, return its n-quantile boundaries"
  [vals n]
  (let [quantiles		(map #(/ % n) (range 1 n))]
    (quantile vals :probs quantiles)))      

(defn- set-quantile-partition
  "Clustering by quantile partitioning of slopes"
  [quantiles cell]
  (let	[slope		(get-slope-memo cell)		; TODO: abstract out to generalize
         q-checks	(map (partial <= slope) quantiles)
         idx			(.indexOf q-checks true)		; returns -1 if not found
         quantile	(if (= idx -1) (inc (count quantiles)) idx)]
    (set-cluster quantile cell)))

(defn quantile-partition
  "Takes a dataset and returns a new dataset with a new column of cluster label using quantile paritioning"
  [coll n]
  (let [slopes		(get-slopes coll)		; TODO: abstract out to generalize
        quantiles	(get-quantiles slopes n)]
    (map (partial set-quantile-partition quantiles) coll)))
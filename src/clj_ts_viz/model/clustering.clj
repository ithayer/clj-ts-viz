(ns clj-ts-viz.model.clustering
  (:require [clj-ts-viz.data :as data])
  (:use (incanter core stats)))			;; use :only

(defrecord id-stats [id slope])

(declare dimension-reduction)

(defn cluster-ts
  "Top level function that takes a dataset and returns a new dataset with a new column of cluster label"
  [coll]
  (let [sloped-coll	(map dimension-reduction coll)]))
        ;; clustered-coll]))		;; work in progress

;(def ts (dimension-reduction (first data/example-raw)))
(defn dimension-reduction
  "Reduce dimension of ts to static value(s)"
  [cell]
  (let [id				(:id cell)
        ts				(:balances cell)
        ds				(to-dataset ts)
        time			($ :timestamp ds)
        balance		($ :balance ds)
       	lm				(linear-model balance time)		;; java.lang.IllegalArgumentException: Matrix is singular.
       	slope			(last (:coefs lm))]		;; y ~ a + b(x), where b is slope
    (assoc cell :slope slope)))

(defn fetch-slopes
  "Fetch all the slopes of the collection as a collection"
  [coll]
  (let [[x & coll]	coll]
  	(cons (:slope x) (fetch-slopes coll))))
(ns clj-ts-viz.model.clustering
  (:require [clj-ts-viz.data :as data])
  (:use (incanter core stats)))			;; use :only

(defrecord id-stats [id slope])

(declare dimension-reduction)

(defn cluster-ts
  [col]
  (map dimension-reduction col))

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
    (id-stats. id slope)))

(defn sift-through-data
  [col]
  (map dimension-reduction col))
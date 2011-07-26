(ns clj-ts-viz.model.clustering
  (:require [clj-ts-viz.data :as data])
  (:use (incanter core stats)))			;; use :only

(defrecord id-stats [id slope])

;(def ts (dimension-reduction (first data/example-raw)))
; (sel ts :cols 0)
(defn dimension-reduction
  "Reduce dimension of ts to static value(s)"
  [cell]
  (let [id				(:id cell)
        ts				(:balances cell)
        dataset		(to-dataset ts)
        time			($ :timestamp dataset)
        balance		($ :balance dataset)
       	lm				(linear-model balance time)
       	slope			(last (:coefs lm))]		;; y ~ a + b(x), where b is slope
    (id-stats. id slope)))
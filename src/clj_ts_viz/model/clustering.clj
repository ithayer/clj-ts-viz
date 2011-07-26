(ns clj-ts-viz.model.clustering
  (:require [clj-ts-viz.data :as data])
  (:use (incanter core stats)))			;; use :only after complete



(defn- dimension-reduction
  "Reduce dimension of ts to static value(s)"
  [cell]
  (let [id				(:id cell)
        ts				(:balances cell)
        time			(map :timestamp (:balances cell))
        balance		(map :balance (:balances cell))
       	lm				(linear-model balance time)
       	slope			(last (:coefs lm))]		;; y ~ a + b(x), where b is slope
    (assoc cell :slope slope)))

(defn- fetch-slopes-quantiles
  "Fetch all the slopes of the collection as a collection"
  [n coll]
  (let [slopes			(map :slope coll)
        quantiles		(map #(/ % n) (range 1 n))]
    (quantile slopes :probs quantiles)))      

(defn add-cluster-col
  "Top level function that takes a dataset and returns a new dataset with a new column of cluster label"
  [n coll]
  (let [sloped-coll	(map dimension-reduction coll)]))
        ;; clustered-coll]))		;; work in progress

; testing only        
(def data (data/gen-dataset))		
(def sc (map dimension-reduction data))
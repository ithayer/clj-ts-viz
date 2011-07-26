(ns clj-ts-viz.model.clustering
  (:require [clj-ts-viz.data :as data])
  (:use (incanter core stats)))			;; use :only after complete

(defn get-id
  "Getter method for user-id"
  [cell]
  {:pre	[(:id cell)]}								;; throws assert for debug clarity
  (:id cell))

(defn get-balances
  "Getter method for balances time-series"
  [cell]
  {:pre	[(seq (:balances cell))]}		;; throws assert for debug clarity
  (:balances cell))

(defn get-time-balance
  "Getter method for time and balance vectors"
  [balances-ts]
  [(map :timestamp balances-ts) (map :balance balances-ts)])

(defn- get-slope
  "Reduce dimension of balances time-series to a descriptive parameter, slope"
  [cell]
  {:post	[(number? %)]}		
  (let [id							(get-id cell)
        bal-ts					(get-balances cell)
        [time balance]	(get-time-balance bal-ts)
       	lm							(linear-model balance time)
       	slope						(last (:coefs lm))]		;; y ~ a + b(x), where b is slope
    slope))

(def ^{:private true
       :doc "memoized version of get-slope"}
      get-slope-memo (memoize get-slope))

(defn- get-slopes-quantiles
  "Get all the slopes of the collection as a list"
  [n coll]
  (let [slopes			(map get-slope-memo coll)
        quantiles		(map #(/ % n) (range 1 n))]
    (quantile slopes :probs quantiles)))      

(comment
  
(defn- assoc-cluster
  [cell quantiles]
  (cond
		(<  
)))        
        
(defn add-cluster-col
  "Top level function that takes a dataset and returns a new dataset with a new column of cluster label"
  [n coll]
  (let [sloped-coll	(map dimension-reduction coll)]))
        ;; clustered-coll]))		;; work in progress
)	; end of comment

; testing only        
(def data (data/gen-dataset))
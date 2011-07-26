(ns clj-ts-viz.model.process
  (:use (incanter [stats :only (linear-model)])))

(defn get-id
  "Getter method for user-id"
  [cell]
  {:pre	[(:id cell)]}								; for debug clarity
  (:id cell))

(defn get-balances
  "Getter method for balances time-series"
  [cell]
  {:pre	[(seq (:balances cell))]}		; for debug clarity
  (:balances cell))

(defn get-time-balance
  "Getter method for time and balance vectors"
  [balances-ts]
  [(map :timestamp balances-ts) (map :balance balances-ts)])

(defn get-clusters
  "Returns a sorted set of cluster values"
  [coll]
  (into (sorted-set) (map :cluster coll)))

(defn set-cluster
  "Setter method for adding cluster key/val to a cell"
  [cluster cell]
  {:pre	[(number? cluster) (map? cell)]}
  (assoc cell :cluster cluster))

;; TODO: abstract out dimension reduction for different descriptive statistics

(defn- get-slope
  "Reduce dimension of balances time-series to a descriptive parameter, slope"
  [cell]
  {:post	[(number? %)]}		
  (let [id							(get-id cell)
        bal-ts					(get-balances cell)
        [time balance]	(get-time-balance bal-ts)
       	lm							(linear-model balance time)
       	slope						(last (:coefs lm))]		; y ~ a + b(x), where b is slope
    slope))

(def ^{:doc "memoized version of get-slope"}
      get-slope-memo (memoize get-slope))

(defn get-slopes
  "Get all slopes as a list"
  [coll]
  (map get-slope-memo coll))
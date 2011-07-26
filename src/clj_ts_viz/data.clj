(ns clj-ts-viz.data
  (:require [clojure.contrib.json :as json])
  (:require [clojure.contrib.probabilities.monte-carlo :as mc]))

(def live-data (atom nil))

(def example-raw
  [{:id 0
    :balances
    [{:balance 100 :timestamp 1311638600}
     {:balance 150 :timestamp 1311638601}
     {:balance 200 :timestamp 1311638602}
     {:balance 150 :timestamp 1311638603}
     {:balance 180 :timestamp 1311638604}
     {:balance 160 :timestamp 1311638605}
     {:balance 200 :timestamp 1311638606}
     {:balance 210 :timestamp 1311638607}]
    :other-property 10}])

(def example-clustered
  [{:id 0
    :cluster 1
    :balances
    [{:balance 0 :timestamp 1311638600}
     {:balance 0 :timestamp 1311638601}
     {:balance 0 :timestamp 1311638602}
     {:balance 0 :timestamp 1311638603}
     {:balance 0 :timestamp 1311638604}
     {:balance 0 :timestamp 1311638605}
     {:balance 0 :timestamp 1311638606}
     {:balance 0 :timestamp 1311638607}]
    :other-property 10}])

(defn load-json [fname]
  (json/read-json (slurp fname)))

(defn jitter-timeline-piece [[timestamp balance]]
  [(+ (- (rand-int (* 2 86400)) 86400) timestamp)
   (* (+ 1.0 (- (rand 0.20) 0.05)) balance)])

(defn jitter-timeline [timeline]
  (sort-by first (map jitter-timeline-piece timeline)))

(defn jitter [record]
  "Jitters the data, just to make it un-recognizable."
  {:id             (hash (str (:uname record) "-salt:" (rand)))
   :fake-score     (+ (:score record) (- (rand-int 50) 25))
   :timeline       (jitter-timeline (:timeline record))})

;;(defn generate-random-record
;; (dist/draw (dist/normal-distribution -2 (sqrt 0.5)))
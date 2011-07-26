(ns clj-ts-viz.data
  (:require [clojure.contrib.json :as json]))

(def live-data (atom nil))

(def example-raw
  [{:id 0
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

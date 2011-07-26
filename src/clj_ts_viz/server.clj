(ns clj-ts-viz.server
  (:require [clj-ts-viz.data :as data]
            [noir.server     :as server]))

(server/load-views "src/clj_ts_viz/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8000"))]
    (let [compute-correlation-fn nil
          result (if compute-correlation-fn
                   (compute-correlation-fn data/example-raw)
                   data/example-clustered)]
      (reset! data/live-data result)
      (server/start port {:mode mode
                          :ns 'clj-ts-viz}))))


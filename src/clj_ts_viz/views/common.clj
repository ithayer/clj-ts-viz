(ns clj-ts-viz.views.common
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))


(def includes {:jquery (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js")
               :d3 (include-js "/js/d3.js")
               :reset.css (include-css "/css/reset.css")
               :default.css (include-css "/css/default.css")
               :viz (include-js "/js/viz.js")})

(defn add-includes [incs]
  (map includes incs))

(defpartial layout [& content]
            (html5
              [:head
               [:title "clj-ts-viz"]
               (add-includes [:jquery :d3 :viz :reset.css :default.css])]
              [:body
               [:div#wrapper
                content]]))

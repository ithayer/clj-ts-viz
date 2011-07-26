(ns clj-ts-viz.views.visuals
  (:require [clj-ts-viz.views.common :as common]
            [clj-json.core :as json]
            [clj-ts-viz.data :as data]
            [noir.content.pages :as pages])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpage "/" []
         (common/layout
           [:script "var finData = " (json/generate-string @data/live-data)]
           [:h1 "Welcome to clj-ts-viz"]
           [:div#vizContainer ]))


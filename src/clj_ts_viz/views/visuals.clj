(ns clj-ts-viz.views.welcome
  (:require [clj-ts-viz.views.common :as common]
            [noir.content.pages :as pages])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to clj-ts-viz"]))


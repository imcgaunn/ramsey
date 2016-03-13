(ns budget.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.pprint :refer [pprint]]
            [clojure.data.json :as json]
            [budget.rules :as rules]
            [ring.util.response :as resp]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (resp/redirect "/index.html"))
  (POST "/compute-budget" req 
        (try
          (json/write-str (rules/form-data->computed-budget (:params req)))
          (catch AssertionError e
            {:status 400 :body "invalid budget rules"})))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes (assoc site-defaults :security false)))

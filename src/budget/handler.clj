(ns budget.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.pprint :refer [pprint]]
            [clojure.data.json :as json]
            [budget.rules :as rules]
            [budget.database :as db]
            [ring.util.response :as resp]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" []
       (resp/content-type
         (resp/resource-response "index.html" {:root "public"})
         "text/html"))
  (GET "/month/:m/year/:y" [m y] (db/monthly-budget m y))
  (POST "/compute-budget" req 
        (try
          (let [params (:params req)
                budget (json/write-str (rules/form-data->computed-budget params))
                date   (str (:month params) "-" (:year params))
                income (:income params)]
            (db/insert-month-budget! {:mdate date
                                      :budget budget
                                      :income income})
            {:status 200 :body "successfully inserted budget data"})
          (catch AssertionError e
            {:status 400 :body "invalid budget rules"})
          (catch Exception e
            {:status 400 :body "couldn't insert budget"})))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes (assoc site-defaults :security false)))

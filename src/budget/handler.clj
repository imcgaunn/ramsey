(ns budget.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (resp/redirect "/index.html"))
  (GET "/compute-budget" [] "Reponse text")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes (assoc site-defaults :security false)))

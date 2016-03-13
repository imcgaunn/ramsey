(ns budget.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [budget.rules :as rules]
            [budget.handler :refer :all]))

(def valid-budget-map {:cat1-title "Title 1"
                       :cat1-val "30"
                       :cat2-title "Title 2"
                       :cat2-val "70"})

(def invalid-budget-map {:cat1-title "Title 1"
                       :cat1-val "30"
                       :cat2-title "Title 2"
                       :cat2-val "80"})

; TODO: fix this test 
(deftest test-app
  (testing "compute-budget route"
    (let [response (app (mock/request :post "/compute-budget" 
                                      valid-budget-map))]
      (println response))))

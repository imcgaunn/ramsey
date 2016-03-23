(ns budget.database-test
  (:require [clojure.test :refer :all]
            [budget.database :refer :all]
            [budget.db-util :as ut]
            [budget.rules :refer [form-data->computed-budget]]
            [clojure.data.json :as json]))

;; starting point for unit tests, makes sure is
;; empty table 
(defn delete-and-create-db! []
  (delete-budget-db!)
  (create-budget-db!))

;; a bunch of generic data for use in test-cases
(def test-title-val-map 
  {:cat1-title "Alternate 1", :cat1-val "20",
   :cat2-title "Title 2", :cat2-val "40",
   :cat3-title "Title 3", :cat3-val "40",
   :income "4000"})

(def todays-ym (ut/year-date-format (ut/todays-date)))
(def next-months-ym (ut/year-date-format (ut/add-months (ut/todays-date) 1)))
(def two-months-ym (ut/year-date-format (ut/add-months (ut/todays-date) 2)))
(def test-budget (json/write-str (form-data->computed-budget test-title-val-map)))

(deftest t-create-and-delete!
  (testing "creation and deletion of db"
    (is (= 0 (delete-and-create-db!)))))

(deftest t-insert-month-budget!
  (testing "inserting budget row into budgets table"
    (delete-and-create-db!)
    (is (= 1 (insert-month-budget! {:mdate todays-ym
                                    :budget test-budget
                                    :income (:income test-title-val-map)})))
    (is (not (= '() (get-all-budgets))))))

(deftest t-get-budget-by-date
  (testing "retrieving budget row from budgets table"
    (delete-and-create-db!)
    (insert-month-budget! {:mdate todays-ym
                           :budget test-budget
                           :income (:income test-title-val-map)})
    (let [res (first (get-budget-by-date {:mdate todays-ym}))]
      (is (= true (contains? res :budget_date)))
      (is (= true (contains? res :categories)))
      (is (= true (contains? res :income)))
      (is (= false (contains? res :nonsense))))))

(deftest t-get-all-budgets
  (testing "retrieving all rows from budgets table"
    (delete-and-create-db!)
    ;; insert a bunch of budgets
    (insert-month-budget! {:mdate todays-ym
                           :budget test-budget
                           :income (:income test-title-val-map)})
    (insert-month-budget! {:mdate next-months-ym
                           :budget test-budget
                           :income (:income test-title-val-map)})
    (insert-month-budget! {:mdate two-months-ym
                           :budget test-budget
                           :income (:income test-title-val-map)})
    (is (= 3 (count (get-all-budgets))))))
 

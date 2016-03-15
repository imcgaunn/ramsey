(ns budget.database
  (:require [yesql.core :refer [defquery defqueries]]
            [budget.rules :as rules]
            [clojure.data.json :as json]
            [budget.db-util :as util]))

(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname "resources/db/budgets.db"
              :user "imcgaunn"})

(defquery create-budget-db! "queries/create_budget_db.sql"
  {:connection db-spec})

(defquery delete-budget-db! "queries/delete_budget_db.sql"
  {:connection db-spec})

(defquery insert-month-budget! "queries/insert_month_budget.sql"
  {:connection db-spec})

;; queries for retrieving info from budgets table
(defqueries "queries/get_budgets.sql"
  {:connection db-spec})

(defn monthly-budget [month]
  "Queries database for budget info from parameter month"
  (str "Not Implemented"))


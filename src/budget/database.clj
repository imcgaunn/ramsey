(ns budget.database
  (:require [yesql.core :refer [defquery]]))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname "//localhost:5432/imcgaunn"
              :user "imcgaunn"})

(defquery users-by-country "queries/test_query.sql"
  {:connection db-spec})

(users-by-country {:country "USA"})


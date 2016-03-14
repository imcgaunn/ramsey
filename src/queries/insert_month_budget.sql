-- Inserts row into budgets table with information for a given month
INSERT INTO budgets VALUES (:mdate, :budget::json, :income::numeric)

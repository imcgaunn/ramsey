-- Inserts row into budgets table with information for a given month
INSERT OR REPLACE INTO budgets VALUES (:mdate, :budget, :income)

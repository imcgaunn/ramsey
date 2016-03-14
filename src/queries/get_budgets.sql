-- name: get-all-budgets
-- Retrieves all budget information from budgets table
SELECT * FROM budgets

-- name: get-budget-by-date
-- Retrieves budget for specified month
SELECT *
FROM BUDGETS
WHERE budget_date = :mdate

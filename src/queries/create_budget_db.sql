-- Creates budget database table if not already created
CREATE TABLE IF NOT EXISTS budgets (
  budget_date date,
  categories json,
  income numeric(15, 2)
);

-- Creates budget database table if not already created
CREATE TABLE IF NOT EXISTS budgets (
  budget_date text PRIMARY KEY,
  categories text,
  income real
);

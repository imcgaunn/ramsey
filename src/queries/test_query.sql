-- Gets users in a given country
SELECT name 
FROM users
WHERE country = :country

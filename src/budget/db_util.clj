(ns budget.db-util)

(defn sql-timestamp
  "returns sql timestamp form of a java.util.Date object"
  [date]
  (new java.sql.Timestamp (.getTime date)))

(defn add-months
  "Takes a java.util.Date instance and adds m months.
  (there's no reason you couldn't add negative months)"
  [date m]
  (let [cal (java.util.Calendar/getInstance)]
    (.setTime cal date)
    (.add cal java.util.Calendar/MONTH m)
    (.getTime cal)))

(defn todays-date
  "returns a java.util.Date object representing today's date"
  []
  (new java.util.Date))

(ns budget.rules)

(def weeks-per-month 4)

(defn monthly-income 
  "computes pay per month based on a pay amount and an interval in weeks"
  [pay interval]
  (* pay (/ weeks-per-month interval)))

(defn valid-rules? 
  "Ensures that the ruleset allocates a maximum of 100% of income" 
  [rules]
  (<= (reduce + (vals rules)) 100))

(defn resources-per-category 
  "Computes allocation of resources per category based on rule"
  [income rules]
  (let [amnts 
        (map (fn [rule] (* income (/ (second rule) 100))) 
             rules)]
    (zipmap (keys rules) amnts)))


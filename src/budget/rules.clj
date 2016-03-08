(ns budget.rules
  (:require [clojure.string :as str]
            [tupelo.core :refer [it->]]))

(def weeks-per-month 4)
(def test-map {:cat1-title "Title 1", :cat1-val "30",
               :cat2-title "Title 2", :cat2-val "40",
               :cat3-title "Title 3", :cat3-val "40"})

;; determines if keyword is a *-title keyword
(defn is-title-kw [arg] (re-matches #".*-title" (name arg)))

;; finds corresponding value *-title
(defn title-kw->val-kw [arg] (it-> arg
                                (name it)
                                (str/replace it #"-title" "-val")
                                (keyword it)))

(defn create-budget-mapping
  "Convert mappings form the frontend into the format compatible with
   backend rules"
  [form-mapping]
  (let [title-kws (filter #(is-title-kw %) (keys form-mapping))]
    (into {}
          (for [title-kw title-kws]
            (let [val-kw (title-kw->val-kw title-kw)
                  title-str (title-kw form-mapping)
                  val-str (val-kw form-mapping)]
              {title-str val-str})))))

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


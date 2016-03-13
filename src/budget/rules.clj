(ns budget.rules
  (:require [clojure.string :as str]
            [tupelo.core :refer [it->]]))

(def weeks-per-month 4)
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
    (assoc
      (into {}
            (for [title-kw title-kws]
              (let [val-kw    (title-kw->val-kw title-kw)
                    title-str (get form-mapping title-kw)
                    val-str   (get form-mapping val-kw)
                    val-num   (read-string val-str)]
                {title-str val-num})))
      :income (read-string (:income form-mapping)))))

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
  [rules]
  ;; make sure user supplied a valid rule 
  (assert (valid-rules? (dissoc rules :income)) "Invalid ruleset")
  (let [income     (:income rules)
                   ;; keys that aren't the income key
        categories (keys (dissoc rules :income))]
    (into {}
      (for [cat categories]
        (let [percentage (get rules cat)]
          {cat (* income (/ percentage 100))})))))

(defn form-data->computed-budget
  "Takes a monthly income and a form-mapping and builds a budget mapping"
  [form-mapping]
  (let [bmapping (create-budget-mapping form-mapping)]
    (resources-per-category bmapping)))


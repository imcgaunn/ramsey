(ns budget.rules-test
  (:require [clojure.test :refer :all]
            [budget.rules :refer :all]
            [tupelo.core :refer [it->]]))


(def valid-rule-1 {:first 10 :second 10 :third 80})
(def valid-rule-2 {:first 40 :second 10 :third 10})
(def invalid-rule-1 {:first 40 :second 40 :third 40})

(deftest t-valid-rules?
  (testing "rule validator"
    (is (= true (valid-rules? valid-rule-1)))
    (is (= true (valid-rules? valid-rule-2)))
    (is (= false (valid-rules? invalid-rule-1)))))

(deftest t-is-title-kw
  (testing "is title keyword"
    (is (is-title-kw :cat1-title))
    (is (is-title-kw :cat2-title))
    (is (not (is-title-kw :cat1-val)))
    (is (not (is-title-kw :cat2-val)))))

(deftest t-title-kw->val-kw
  (testing "transforming title keyword to corresponding val keyword"
    (is (= :cat1-val (title-kw->val-kw :cat1-title)))
    (is (= :cat2-val (title-kw->val-kw :cat2-title)))))

(def test-title-val-map {:cat1-title "Title 1", :cat1-val "30",
               :cat2-title "Title 2", :cat2-val "40",
               :cat3-title "Title 3", :cat3-val "40"})

(deftest t-create-budget-mapping
  (is (= (create-budget-mapping test-map)
         { "Title 1" "30",
           "Title 2" "40",
           "Title 3" "40"})))
  

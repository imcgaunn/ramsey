(ns budget.rules-test
  (:require [clojure.test :refer :all]
            [budget.rules :refer :all]))


(def valid-rule-1 {:first 10 :second 10 :third 80})
(def valid-rule-2 {:first 40 :second 10 :third 10})
(def invalid-rule-1 {:first 40 :second 40 :third 40})

(deftest test-valid-rules?
  (testing "rule validator"
    (is (= true (valid-rules? valid-rule-1)))
    (is (= true (valid-rules? valid-rule-2)))
    (is (= false (valid-rules? invalid-rule-1)))))

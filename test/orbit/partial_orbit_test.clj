(ns orbit.partial-orbit-test
  (:require [clojure.test :refer :all]
            [orbit.core :as orbit]))

(deftest test-partial-orbit
  (let [f (fn [x] (hash-set (conj x (inc (count x)))))
        cand? (fn [x] (<= (count x) 11))
        sol? (fn [x] (= (count x) 11))
        sol (orbit/partial-orbit #{0} f cand? sol?)]
    (testing "Testing partial orbit."
      (is (= 11 (count sol))))))

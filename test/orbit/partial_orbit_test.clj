(ns orbit.partial-orbit-test
  (:require [clojure.test :refer :all]
            [orbit.core :as orbit]))

(deftest test-partial-orbit
  (let [f (fn [x] (hash-set (conj x (inc (count x)))))
        cand? (fn [x] (<= (count x) 11))
        sol? (fn [x] (= (count x) 11))
        bulksol (orbit/partial-orbit-bulk #{0} f cand? sol?)
        singlesol (orbit/partial-orbit-single #{0} f cand? sol?)]
    (testing "Testing bulk and single extension first solutions."
      (is (not (some nil? [bulksol singlesol]))))))

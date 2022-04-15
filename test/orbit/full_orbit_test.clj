(ns orbit.full-orbit-test
  (:require [clojure.test :refer :all]
            [orbit.core :as orbit]))

;;testing with generating subsets
(defn covering-subsets
  "All covering subsets of a collection, those that miss only one element
  from the collections. The collection is assumed to be a set."
  [coll]
  (map (fn [x]
         (remove (partial = x) coll))
       coll))

(deftest test-subsets-full-orbit
  (let [n 10
        S (set (range n))
        subsets1 (orbit/full-orbit [S] covering-subsets)
        subsets2 (orbit/pfull-orbit [S] covering-subsets)]
    (testing "Testing full orbit with subset calculations."
      (is (== (Math/pow 2 n) (count subsets1)))
      (is (= (set subsets1) (set subsets2) )))))

(deftest test-full-orbit-single-op
  (let [result (orbit/full-orbit-single-op #{1}
                                           (fn [x] (mod (* 2 x) 11)))]
    (testing "Testing full orbit with a single value action."
      (is (= 10 (count result))))))

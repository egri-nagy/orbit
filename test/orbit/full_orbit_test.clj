(ns orbit.full-orbit-test
  (:require [clojure.test :refer :all]
            [orbit.core :as orbit]))

;;testing with operators that construct subsets of a set
(defn subset-covers
  "All covering (missing a single element only) subsets of a collection.
  The collection is assumed to be a set."
  [coll]
  (map (fn [x]
         (remove (partial = x) coll))
       coll))

(deftest test-full-orbit
  (let [subsets (orbit/full-orbit [#{1 2 3 4 5 6 7 8}] subset-covers)]
    (testing "Testing full orbit with subsets."
      (is (= 256 (count subsets))))))

(deftest test-pfull-orbit
  (let [res (orbit/pfull-orbit [(range 16)] subset-covers)]
    (testing "Testing parallel full-orbit search with subsets."
      (is (= 65536 (count res))))))

(deftest test-full-orbit-single-op
  (let [result (orbit/full-orbit-single-op #{1 3}
                                           (fn [x] (mod (inc x) 10)))]
    (testing "Testing full orbit with a single value action."
      (is (= 10 (count result))))))

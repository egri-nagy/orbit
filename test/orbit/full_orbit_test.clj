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
  (let [bulkres (orbit/full-orbit-bulk [#{1 2 3 4 5 6 7 8}] subset-covers)
        singleres (orbit/full-orbit-single [#{1 2 3 4 5 6 7 8}] subset-covers)]
    (testing "Testing single and bulk extensions for full-orbit."
      (is (= 256 (count bulkres)))
      (is (= bulkres singleres)))))

(deftest test-full-orbit
  (let [res (orbit/full-orbit-parallel [(range 16)] subset-covers)]
    (testing "Testing parallel full-orbit search."
      (is (= 65536 (count res))))))

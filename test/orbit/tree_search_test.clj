(ns orbit.tree-search-test
  (:require [clojure.test :refer :all]
            [orbit.core :as orbit]))

(deftest test-tree-search
  (let [f (fn [x] (if (< (count x) 8) ;generating all bitstrings of length 8
                    #{(conj x 0) (conj x 1)}
                    #{})) ; generation supposed to stop when no more solutions
        sol? (fn [x] (= (count x) 8))
        sols (orbit/tree-search #{[]} f  sol?)]
    (testing "Testing tree search."
      (is (= 256 (count sols))))))

(deftest test-ptree-search
  (let [n 17
        f (fn [x] (if (< (count x) n) ;generating all bitstrings of length 8
                    #{(conj x 0) (conj x 1)}
                    #{})) ; generation supposed to stop when no more solutions
        sol? (fn [x] (= (count x) n))
        sols (orbit/ptree-search #{[]} f  sol?)]
    (testing "Testing tree search."
      (is (== (Math/pow 2 n) (count sols))))))

(ns orbit.tree-search-test
  (:require [clojure.test :refer [deftest testing is]]
            [orbit.core :as orbit]))

;; generating fixed length bitstrings
(def N 16)

(deftest test-tree-search
  (let [generator-fn
        (fn [bitstring]
          (if (< (count bitstring) N) ;generating all bitstrings of length N
            #{(conj bitstring 0) (conj bitstring 1)}
            #{})) ; generation supposed to stop when no more solutions
        sol? (fn [bitstring] (= (count bitstring) N))
        sols (orbit/tree-search #{[]} generator-fn  sol?)]
    (testing "Testing tree search."
      (is (== (Math/pow 2 N) (count sols))))))

(deftest test-terminating-tree-search
  (let [generator-fn (fn [bitstring]
                       #{(conj bitstring 0) (conj bitstring 1)})
        sol? (fn [bitstring] (= (count bitstring) N))
        sols (orbit/terminating-tree-search #{[]} generator-fn  sol?)]
    (testing "Testing terminating tree search for generating N-bit strings."
      (is (== (Math/pow 2 N) (count sols))))))

(deftest test-ptree-search
  (let [generator-fn
        (fn [bitstring]
          (if (< (count bitstring) N) ;generating all bitstrings of length N
            #{(conj bitstring 0) (conj bitstring 1)}
            #{})) ; generation supposed to stop when no more solutions
        sol? (fn [bitstring] (= (count bitstring) N))
        sols (orbit/ptree-search #{[]} generator-fn  sol?)
        sols2 (orbit/ptree-search-depth-first #{[]} generator-fn  sol?)]
    (testing "Testing parallel tree search."
      (is (== (Math/pow 2 N) (count sols) (count sols2)))
      (is (=  sols sols2)))))

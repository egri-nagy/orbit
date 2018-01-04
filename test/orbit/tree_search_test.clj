(ns orbit.tree-search-test
  (:require [clojure.test :refer :all]
            [orbit.core :as orbit]))

(deftest test-tree-search
  (let [f (fn [x] (if (< (count x) 8)
                    #{(conj x 0) (conj x 1)}
                    #{})) ; generation supposed to stop when no more solutions
        sol? (fn [x] (= (count x) 8))
        bulksol (orbit/tree-search-bulk #{[]} f  sol?)
        singlesol (orbit/tree-search-single #{[]} f  sol?)]
    (testing "Testing bulk and single extension for tree searches."
      (is (= 256 (count bulksol) (count singlesol))))))

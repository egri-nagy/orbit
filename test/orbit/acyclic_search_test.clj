(ns orbit.acyclic-search-test
  (:require [clojure.test :refer :all]
            [orbit.core :as orbit]))

(deftest test-acyclic-search
  (let [f (fn [x] (if (< (count x) 8)
                    #{(conj x 0) (conj x 1)}
                    #{})) ; generation supposed to stop when no more solutions
        sol? (fn [x] (= (count x) 8))
        bulksol (orbit/acyclic-search-bulk #{[]} f  sol?)
        singlesol (orbit/acyclic-search-single #{[]} f  sol?)]
    (testing "Testing bulk and single extension for acyclic searches."
      (is (= 256 (count bulksol) (count singlesol))))))

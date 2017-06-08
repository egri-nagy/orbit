(use '[orbit.core :as o])
(use '[criterium.core])

;;testing with operators that construct subsets of a set
;;a set-valued operator
(defn subset-covers [coll]
  (map (fn [x]
         (set (remove (partial = x) coll)))
       coll))

(println "Single")

(binding [orbit.core/*task-size* 32]
  (bench
   (o/full-orbit-single [#{1 2 3 4 5 6 7 8 9}]
                        subset-covers)
   :verbose))


(println "Bulk")

(binding [orbit.core/*task-size* 32]
  (bench
   (o/full-orbit-bulk [#{1 2 3 4 5 6 7 8 9}]
                          subset-covers)
   :verbose))


(println "Parallel")

(binding [orbit.core/*task-size* 32]
  (bench
   (o/full-orbit-parallel [#{1 2 3 4 5 6 7 8 9}]
                          subset-covers)
   :verbose))

(println)

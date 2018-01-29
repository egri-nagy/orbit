(use '[orbit.core :as o])
(use '[criterium.core])

;;testing with operators that construct subsets of a set
;;a set-valued operator
(defn subset-covers [coll]
  (map (fn [x]
         (set (remove (partial = x) coll)))
       coll))


(println "Serial")

(binding [orbit.extension/*task-size* 32]
  (bench
   (o/full-orbit [#{1 2 3 4 5 6 7 8 9 10}]
                 subset-covers)
   :verbose))


(println "Parallel")

(binding [orbit.extension/*task-size* 32]
  (bench
   (o/pfull-orbit [#{1 2 3 4 5 6 7 8 9 10}]
                  subset-covers)
   :verbose))

(println)

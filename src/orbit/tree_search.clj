(ns orbit.tree-search
  "Finding all solutions when the graph is guaranteed to be circuit free."
  (:require [orbit.memory :as memory]
            [taoensso.timbre :as timbre]))

(defn tree-search
  "Searching for solutions by predicate solution?, where the search graph is
  guaranteed to be acyclic, thus no need for keeping the orbit.
  Solutions can be extended to further solutions (i.e. they are not assumed
  to be leaf nodes)."
  [seeds sa solution? stepf]
  (loop [waiting (seq seeds), solutions (set (filter solution? seeds))]
    (timbre/info "#solutions:" (count solutions)
                 "unprocessed:" (count waiting)
                 (memory/mem-info))
    (if (empty? waiting)
      solutions
      (let [[newelts unprocessed] (stepf waiting sa)]
        (recur (into unprocessed newelts)
               (into solutions (filter solution? newelts)))))))

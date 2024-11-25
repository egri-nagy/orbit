(ns orbit.tree-search
  "Finding all solutions when the graph is guaranteed to be circuit free.")

(defn tree-search
  "Searching for solutions by predicate solution?, where the search graph is
  guaranteed to be acyclic, thus no need for keeping the orbit.
  Solutions can be extended to further solutions (i.e. they are not assumed
  to be leaf nodes).
  Thus, search terminates when there are new candidates generated."
  [seeds sa solution? stepf]
  (loop [waiting (seq seeds), solutions (set (filter solution? seeds))]
    (if (empty? waiting)
      solutions
      (let [[newelts unprocessed] (stepf waiting sa)]
        (recur (into unprocessed newelts)
               (into solutions (filter solution? newelts)))))))

(defn terminating-tree-search
  "Same as [[tree-search]] but terminating at solutions."
  [seeds sa solution? stepf]
  (loop [waiting (seq seeds), solutions (set (filter solution? seeds))]
    (if (empty? waiting)
      solutions
      (let [[newelts unprocessed] (stepf waiting sa)
            {sols true nonsols false} (group-by solution? newelts)]
        (recur (into unprocessed nonsols)
               (into solutions sols))))))

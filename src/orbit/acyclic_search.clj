(ns orbit.acyclic-search
  "Finding all solutions when the graph is guaranteed to be circuit free.")

(defn acyclic-search
  "Searching for solutions by predicate solution?, where the search graph is
  guaranteed to be acyclic, thus no need for keeping the orbit."
  [seeds sa solution? stepf]
  (loop [waiting (seq seeds), solutions (set (filter solution? seeds))]
    (if (empty? waiting)
      solutions
      (let [[newelts unprocessed] (stepf waiting sa)]
        (recur (into unprocessed newelts)
               (into solutions (filter solution? newelts)))))))

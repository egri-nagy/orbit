(ns orbit.partial-orbit
  "Partial orbit stopping at first solution.")

(defn partial-orbit
  "Generic search with the ability to bail out early when
  a solution is found. It returns a solution or nil.
  candidate? returns true if the given element is a possible solution,
  so even if it's not a solution, it is worth investigating.
  solution? returns true when the given element is a solution.
  Bulk and parallel extension strategies (stepf) may be wasteful."
  [seed sa candidate? solution? stepf]
  (loop [waiting (set [seed]), orbit #{}]
    (let [candidates (filter candidate? waiting)
          solutions (filter solution? candidates)]
      (if (or (not-empty solutions) (empty? candidates))
        (first solutions)
        (let [[newelts unprocessed] (stepf candidates sa)
              norbit (into orbit candidates)
              diff (remove norbit newelts)]
          (recur (into unprocessed diff) norbit))))))

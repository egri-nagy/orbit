(ns orbit.full-orbit
  "Computing an orbit exhaustively.")

(defn full-orbit
  "Generic graph-search for producing the full orbit from seeds
  by applying set valued action sa. The order of the enumeration
  is determined by the step function."
  [seeds sa stepf]
  (loop [waiting (seq seeds) ;this seq call makes it a bit faster, why?
         orbit (set seeds)]
    (if (empty? waiting)
      orbit
      (let [[extensions unprocessed] (stepf waiting sa)
            newelts (remove orbit extensions)]
        (recur (into unprocessed newelts)
               (into orbit newelts))))))

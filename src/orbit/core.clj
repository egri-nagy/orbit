(ns orbit.core
  "Calculating orbits by graph search algorithms.
   Functions defined here form the complete API."
  (:require [orbit.extension :as ext]
            [orbit.full-orbit :as f]
            [orbit.partial-orbit :as p]
            [orbit.tree-search :as t])
  (:gen-class))

(defmacro get-version
  "A macro that  saves in compile time the property for version number, so it is
  available in runtime."
  []
  (System/getProperty "orbit.version"))

(defn -main
  "The first argument is a name of a file containing Clojure source code.
  This main method evaluates the forms contained."
  [& args]
  (println "Orbit"
           (get-version))
  (load-file (first args))
  (shutdown-agents))

; FULL ORBIT ALGORITHMS
;; seeds - elements to act on
;; sa - set action function
(defn full-orbit
  "Calculates full-orbit starting from the elements in seeds using a single
  set-valued action function producing new elements. Extension is done
  at a step for the whole front line."
  [seeds sa]
  (f/full-orbit seeds sa ext/bulk-step))

(defn pfull-orbit
  "Parallel version of full-orbit using reducers."
  [seeds sa]
  (f/full-orbit seeds sa ext/parallel-step))

(defn full-orbit-single-op
  "Calculates full-orbit starting from the elements in seeds using a single
  set-valued action function producing new elements. Extension is done
  at a step for the whole front line."
  [seeds sa]
  (f/full-orbit seeds sa ext/single-op-bulk-step))

; PARTIAL ORBIT, STOPPING AT FIRST SOLUTIONS
(defn partial-orbit
  "Returns a first solution when searching by extending one by one."
  [seed sa candidate? solution?]
  (p/partial-orbit seed sa candidate? solution? ext/single-step))

; SEARCHING ACYCLIC GRAPH FOR SOLUTIONS
(defn tree-search
  "Searching for solutions in a graph known to be acyclic. Solutions
  can be further extended to be other solutions. It is the responsibility
  of sa to stop if it is not the case."
  [seeds sa solution?]
  (t/tree-search seeds sa solution? ext/bulk-step))

(defn ptree-search
  "Parallel version of tree-search. Solutions extended too."
  [seeds sa solution?]
  (t/tree-search seeds sa solution? ext/parallel-step))

(defn terminating-tree-search
  "Searching for solutions in a graph known to be acyclic. Solutions are
   not extended further."
  [seeds sa solution?]
  (t/terminating-tree-search seeds sa solution? ext/bulk-step))
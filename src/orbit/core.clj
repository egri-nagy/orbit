(ns orbit.core
  "Calculating orbits by graph search algorithms."
  (:require [orbit.extension :as ext]
            [orbit.full-orbit :refer  [full-orbit]]
            [orbit.partial-orbit :refer [partial-orbit]]
            [orbit.tree-search :refer  [tree-search]]
            [taoensso.timbre :as timbre])
  (:gen-class))

(declare full-orbit-parallel
         full-orbit-single
         full-orbit-bulk
         partial-orbit-single
         partial-orbit-bulk
         tree-search-bulk
         tree-search-single)

;; to save compile time property into a runtime one
(defmacro get-version []
  (System/getProperty "orbit.version"))

;; setting default log level
(timbre/merge-config! {:level :warn})

(defn -main
  "The first argument is a name of a file containing Clojure source code.
  This main method evaluates the forms contained."
  [& args]
  (println "Orbit"
           (get-version))
  (load-file (first args))
  (shutdown-agents))

; FULL ORBIT ALGORITHMS

(defn full-orbit-parallel
  [seeds sa]
  (full-orbit seeds sa ext/parallel-step))

;; seeds - elements to act on
;; sa - set action function
(defn full-orbit-bulk
  "Bulk-extension search starting from the elements in seeds using a single
  set-valued action function producing new elements."
  [seeds sa]
  (full-orbit seeds sa ext/bulk-step))

(defn full-orbit-single
  "Single extension search starting from the elements in seeds using a single
  set-valued action function."
  [seeds sa]
  (full-orbit seeds sa ext/single-step))

; PARTIAL ORBITS, STOPPING AT FIRST SOLUTIONS

(defn partial-orbit-single
  "Returns a first solution when searching by breadth-first."
  [seed sa candidate? solution?]
  (partial-orbit seed sa candidate? solution? ext/single-step))

(defn partial-orbit-bulk
  "Returns a first solution when searching by depth-first."
  [seed sa candidate? solution?]
  (partial-orbit seed sa candidate? solution? ext/bulk-step))

; SEARCHING ACYCLIC GRAPH FOR SOLUTIONS

(defn tree-search-bulk
  [seeds sa solution?]
  (tree-search seeds sa solution? ext/bulk-step))

(defn tree-search-single
  [seeds sa solution?]
  (tree-search seeds sa solution? ext/single-step))

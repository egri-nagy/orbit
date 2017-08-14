(ns orbit.core
  "Calculating orbits by graph search algorithms."
  (:require [orbit.extension :as ext]
            [orbit.full-orbit :refer  [full-orbit]]
            [orbit.first-solution :refer [first-solution]]
            [orbit.acyclic-search :refer  [acyclic-search]]
            [taoensso.timbre :as timbre]
            [clojure.core.reducers :as r])
  (:gen-class))

(declare full-orbit-parallel
         full-orbit-single
         full-orbit-bulk
         first-solution-single
         first-solution-bulk
         acyclic-search-bulk
         acyclic-search-single)

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

(defn first-solution-single
  "Returns a first solution when searching by breadth-first."
  [seed sa candidate? solution?]
  (first-solution seed sa candidate? solution? ext/single-step))

(defn first-solution-bulk
  "Returns a first solution when searching by depth-first."
  [seed sa candidate? solution?]
  (first-solution seed sa candidate? solution? ext/bulk-step))

(defn acyclic-search-bulk
  [seeds sa solution?]
  (acyclic-search seeds sa solution? ext/bulk-step))

(defn acyclic-search-single
  [seeds sa solution?]
  (acyclic-search seeds sa solution? ext/single-step))

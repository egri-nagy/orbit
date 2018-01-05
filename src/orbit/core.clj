(ns orbit.core
  "Calculating orbits by graph search algorithms."
  (:require [orbit.extension :as ext]
            [orbit.full-orbit :as f]
            [orbit.partial-orbit :as p]
            [orbit.tree-search :as t]
            [taoensso.timbre :as timbre])
  (:gen-class))

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
;; seeds - elements to act on
;; sa - set action function
(defn full-orbit
  "Bulk-extension search starting from the elements in seeds using a single
  set-valued action function producing new elements."
  [seeds sa]
  (f/full-orbit seeds sa ext/bulk-step))

(defn pfull-orbit
  [seeds sa]
  (f/full-orbit seeds sa ext/parallel-step))

; PARTIAL ORBIT, STOPPING AT FIRST SOLUTIONS
(defn partial-orbit
  "Returns a first solution when searching by extending one by one."
  [seed sa candidate? solution?]
  (p/partial-orbit seed sa candidate? solution? ext/single-step))


; SEARCHING ACYCLIC GRAPH FOR SOLUTIONS
(defn tree-search
  [seeds sa solution?]
  (t/tree-search seeds sa solution? ext/bulk-step))

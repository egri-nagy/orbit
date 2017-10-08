(ns orbit.extension
  "Strategies for creating new elements (extensions) based on a set and
  an action. General form: taking a collection and an action, returning
  a new set of elements and the unprocessed, in a vector."
  (:require [clojure.core.reducers :as r]))

;; dynamic variable for the size of the task in parallel execution
(def ^:dynamic *task-size* 256)

;; EXTENSION STRATEGIES
(defn combine-function
  "Combines intermediate results into a set."
  ([] #{})
  ([coll x] (into coll x)))

(defn parallel-step
  "Applies action to all elements in parallel using reducers.
  It has to turn elts into a vector, otherwise fold does not  kick in."
  [elts action]
  [(r/fold *task-size* combine-function conj (r/mapcat action (vec elts))) #{}])

(defn bulk-step
  "Applies action to all elements in one go. Returns the empty set as
  unprocessed elements."
  [elts action]
  [(r/reduce conj #{} (r/mapcat action elts)) #{}])

(defn single-step
  "Produces elements by applying the set-valued action to a single element
  from the given collection of elements. Also returns unprocessed elements."
  [elts action]
  [(action (first elts)) (rest elts)])

(ns orbit.extension
  "Strategies for creating new elements (extensions) based on a set and
  an action. General form: taking a collection and an action, returning
  a new set of elements and the unprocessed, in a vector.
  In graph terminology, we find the neighbouring nodes of nodes."
  (:require [clojure.core.reducers :as r]))

;; dynamic variable for the size of the task in parallel execution
(def ^:dynamic *task-size* 256)

;; EXTENSION STRATEGIES - SET-VALUED ACTIONS
(defn combine-function
  "Combines intermediate results into a set.
  Required by fold in the reducers library."
  ([] #{})
  ([coll x] (into coll x)))

(defn parallel-step
  "Applies action to all elements in parallel using reducers.
  It has to turn elts into a vector, otherwise fold does not  kick in.
  Processes all elements, thus it returns the empty set unprocessed elements."
  [elts action]
  [(r/fold *task-size* combine-function into (r/map action (vec elts)))
   #{}])

(defn bulk-step
  "Applies action to all elements in one go. Returns the empty set as
  unprocessed elements."
  [elts action]
  [(r/reduce into #{} (r/map action elts))
   #{}])

(defn single-step
  "Produces elements by applying the set-valued action to a single element
  from the given collection of elements."
  [elts action]
  [(action (first elts))
   (rest elts)])

;; SINGLE VALUE OPERATORS
(defn single-op-bulk-step
  "Produces elements by applying the single-valued operation to all elements."
  [elts action]
  [(r/reduce conj #{} (r/map action elts))  #{}])

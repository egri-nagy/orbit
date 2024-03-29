(ns orbit.extension
  "When creating the orbit there are different ways for extending the orbit
  being built. The general form of extension is to take a collection and an
  action. Then based on applying the action we produce a new set of elements
  and also return the unprocessed ones in a vector. This gives control on the
  extension process. How to this exactly depends on the answers for the
  following guiding questions.
  1. Do we have an operator that produces a set of results, or just a single
  one. SET-VALUED vs SINGLE VALUE
  2. Do we need the extension happen one-by-one, or is it ok to extend the whole
  frontline at once? single-step vs. parallel
  3. Does the extensions happen in parallel or in a single thread?"
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
  It has to turn elts into a vector, otherwise fold does not kick in.
  Processes all elements, thus it returns the empty set unprocessed elements."
  [elts action]
  [(r/fold
    *task-size*
    combine-function
    into
    (r/map action (vec elts)))
   #{}])

(defn bulk-step
  "Applies action to all elements in one go. Returns the empty set as
  unprocessed elements."
  [elts action]
  [(r/reduce
    into
    #{}
    (r/map action elts))
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
  [(r/reduce
    conj
    #{}
    (r/map action elts))
   #{}])

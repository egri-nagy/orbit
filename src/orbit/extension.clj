(ns orbit.extension
  "How can we extend an orbit? (extending means adding new elements)
  The general form of extension is taking a collection (frontier) and an action.
  By applying the action, we produce a new set of elements.
  In addition, we also return the remaining unprocessed elements in a vector.
  This gives control on the extension process.
  How to extend depends on the answers for the  following guiding questions.
  1. Do we have an operator that produces a set of results, or just a single
  one? SET-VALUED vs SINGLE-VALUE
  2. Do we need the extension happen one-by-one, or is it ok to extend the whole
  frontline at once? single-step vs bulk
  3. Does the extensions happen in parallel or in a single thread?"
  (:require [clojure.core.reducers :as r]))

(def ^:dynamic *task-size*
  "Dynamic variable for the size of the task in parallel execution.
   It affects the parallel extensions. Somewhat arbitrarily the default is 256.
   To run with different values the preferred way is:
   `(binding [orbit.extension/*task-size* 32] ,,,)`
   It ensures that changed value can be seen close to the computation."
  256)

;;; EXTENSION STRATEGIES

;;SET-VALUED ACTIONS
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

;; SINGLE-VALUE OPERATORS
(defn single-op-bulk-step
  "Produces elements by applying the single-valued operation to all elements."
  [elts action]
  [(r/reduce
    conj
    #{}
    (r/map action elts))
   #{}])
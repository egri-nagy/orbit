[![Clojars Project](https://img.shields.io/clojars/v/orbit.svg)](https://clojars.org/orbit)
[![Build Status](https://travis-ci.org/egri-nagy/orbit.svg?branch=master)](https://travis-ci.org/egri-nagy/orbit)

# orbit
The library came out from the observation that most problems in computational abstract algebra require a search algorithm. Instead of writing them again and again, it is natural to abstract the search part out. It is used by [KIGEN](https://github.com/egri-nagy/kigen).

This library contains generic search algorithms. A set-valued operator and predicates for recognizing possible and actual solutions need to be given.

The abstraction overhead is counterbalanced by parallel execution (using the reducers library).

## Orbit computation example
For computing al subsets of a set, we define the following set-valued function.

```clj
(defn subset-covers
  "All covering (missing a single element only) subsets of a collection.
  The collection is assumed to be a set."
  [coll]
  (map (fn [x]
         (remove (partial = x) coll))
       coll))
```
Then we can call a full-orbit function by giving the seeds (a 4-element set in this case) to calculate all subsets.
```clj
(orbit.core/full-orbit [(range 4)] subset-covers)
#{(0 1 3) (2 3) (0 2 3) (0 1 2) () (3) (1 3) (0) (0 3) (1 2 3) (0 2) (2) (1 2) (1) (0 1 2 3) (0 1)}
```

[Attila Egri-Nagy](http://www.egri-nagy.hu)

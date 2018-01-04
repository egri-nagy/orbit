[![Clojars Project](https://img.shields.io/clojars/v/orbit.svg)](https://clojars.org/orbit)

# orbit
The library came out from the observation that most problems in computational abstract algebra require a search algorithm. Instead of writing them again and again, it is natural to abstract the search part out.

This library contains generic search algorithms. A set-valued operator and predicates for recognizing possible andactual solutions need to be given.

The abstraction overhead is counterbalanced by parallel execution (using the reducers library).


(defproject orbit "19.08.05"
  :description "Parallel orbit and search algorithms"
  :url "https://egri-nagy.github.io/orbit/"
  :license {:name "GNU GENERAL PUBLIC LICENSE"
            :url "http://www.gnu.org/copyleft/gpl.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [slamhound "1.5.5"]]
  :plugins [[lein-cloverage "1.1.1"]
            [lein-kibit "0.1.6"]
            [lein-ancient "0.6.15"]
            [lein-bikeshed "0.5.2"]
            [jonase/eastwood "0.3.6"]]
  :main ^:skip-aot orbit.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :aliases {"slamhound" ["run" "-m" "slam.hound"]})

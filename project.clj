(defproject orbit "17.12.12-SNAPSHOT"
  :description "Parallel orbit and search algorithms"
  :url "https://egri-nagy.github.io/orbit/"
  :license {:name "GNU GENERAL PUBLIC LICENSE"
            :url "http://www.gnu.org/copyleft/gpl.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [criterium "0.4.4"]
                 [com.taoensso/timbre "4.10.0"]
                 [slamhound "1.5.5"]]
  :plugins [[lein-cloverage "1.0.6"]
            [lein-kibit "0.1.2"]
            [lein-ancient "0.6.14"]
            [lein-bikeshed "0.3.0"]
            [jonase/eastwood "0.2.3"]]
  :main ^:skip-aot orbit.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :aliases {"slamhound" ["run" "-m" "slam.hound"]})

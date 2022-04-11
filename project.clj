(defproject orbit "19.08.05"
  :description "Parallel orbit and search algorithms"
  :url "https://egri-nagy.github.io/orbit/"
  :license {:name "GNU GENERAL PUBLIC LICENSE"
            :url "http://www.gnu.org/copyleft/gpl.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :plugins [[lein-cloverage "1.2.3"]
            [lein-kibit "0.1.8"]
            [lein-ancient "0.7.0"]
            [lein-bikeshed "0.5.2"]
            [jonase/eastwood "1.2.3"]]
  :main ^:skip-aot orbit.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

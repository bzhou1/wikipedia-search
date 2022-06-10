(defproject org.clojars.bzhou1/wikipedia-search "1.0.3"
  :description "Search Wikipedia using any keyword. Returns extract & URL as hash map"
  :url "https://github.com/bzhou1"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [clj-http "3.12.3"]
                 [cheshire "5.11.0"]
                 [org.clojure/data.json "2.4.0"]]
  :main ^:skip-aot wikipedia-search.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})

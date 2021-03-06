(defproject budget "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [yesql "0.5.2"]
                 [org.clojure/data.json "0.2.6"]
                 [tupelo "0.1.64"]
                 [org.xerial/sqlite-jdbc "3.7.2"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler budget.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})

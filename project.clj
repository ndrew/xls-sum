(defproject xls-sum "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [com.cemerick/shoreleave-remote-ring "0.0.2"]
                 [compojure             "1.1.3"]
                 [dk.ative/docjure      "1.6.0"]
                 [lib-noir              "0.7.0"]
                 [me.raynes/fs          "1.4.4"]
                 [org.clojure/clojure   "1.5.1"]
                 [ring-json-response    "0.2.0"]]
  :main xls-sum.core
  :ring         {:handler xls-sum.server/app-routing
                 :auto-reload? true
                 :auto-refresh true}
   :jvm-opts ["-Dfile.encoding=utf-8"] 
  )

(defproject xls-sum "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure   "1.5.1"]
                 [lib-noir              "0.7.0"]
                 [me.raynes/fs          "1.4.4"]
                 [compojure             "1.1.3"]
                 [dk.ative/docjure      "1.6.0"]
                 [com.cemerick/shoreleave-remote-ring "0.0.2" ]]
  :main xls-sum.core
  :ring         {:handler xls-sum.server/app-routing
                 :auto-reload? true
                 :auto-refresh true})

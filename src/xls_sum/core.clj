(ns xls-sum.core
  (:gen-class :main true)
  (:require
    [xls-sum.server :as server]
    [clojure.java.browse :as browser]))


(defn -main [& args]
  (let [port 1234
        url (str "http://localhost:" port)
        server_instance (future (server/start port))]
    (browser/browse-url url)))
(ns xls-sum.core
  (:gen-class :main true)
  (:require
    [clojure.java.browse :as browser]
    [me.raynes.fs :as fs]
    [xls-sum.server :as server]))



(defn get-data[]
  (let [path (System/getProperty "user.dir")
        files (map #(identity %) (fs/find-files path #".*xlsx$"))]
        files
    ))


(defn -main [& args]
  
  
  (let [port 1234
        url (str "http://localhost:" port)
        server_instance (future (server/start port))]
    (browser/browse-url url))
  
  )



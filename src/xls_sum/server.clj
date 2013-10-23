(ns xls-sum.server
  (:require [compojure.core :refer :all]
            [ring.adapter.jetty :refer [run-jetty]]
            [cemerick.shoreleave.rpc :as rpc]

            [compojure.route :as route]
            [compojure.handler :as handler]

            [hiccup.page :refer [include-css include-js html5]]
            [hiccup.element :refer [javascript-tag]]
            ))


(defn index-page-handler[]
 (html5
    [:head
     [:meta {:http-equiv "Content-Type" :content "text/html;charset=utf-8"}]
     [:meta {:name "description" :content "Excel sum utility"}]
     [:meta {:name "author"      :content "Andrij Sernyak"}]
     ;[:link {:rel "shortcut icon" :href "/css/favicon.ico" :type "image/x-icon"}]
     [:title "Excel sum"]

     ;(include-css "/css/monte.css")
     ;(include-js "/extern/jquery-1.7.2.min.js")
     ;(include-js "/extern/moment.min.js")
     ;(include-js "/extern/d3.js")


     ;(include-edn edn-storage-jsvar {:view (:view-id cfg :index-page)
     ;                                :update-url (:update-url cfg)
     ;                                :cfg data})
     ;(include-js "/js/js.js")
     ]

   [:body
    "FFFFUUUU"
     ;[:a {:id "logo" :href "/"}]
     ;[:div {:id "settings"}
     ; [:a {:href "/status/"} "status "]
     ; [:a {:href "/settings/"} "settings"]]
     ;[:div {:id "viewport"}]
     ;[:div {:id "debug"}]
     ]))


(defroutes app-routes
  ; page routes
  (GET "/"              []     (index-page-handler))

  (route/resources "/" {:root "public"})
  ;(route/not-found (not-found-page-handler))
  )


(def app-routing (-> #'app-routes
                  ;;rpc/wrap-rpc
                  handler/site))


(defn start [port]
   (run-jetty #'app-routing {:port 1234}))

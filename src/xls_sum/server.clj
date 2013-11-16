(ns xls-sum.server
  (:require [compojure.core :refer :all]
            [ring.adapter.jetty :refer [run-jetty]]
            [cemerick.shoreleave.rpc :as rpc]

            [compojure.route :as route]
            [compojure.handler :as handler]

            [hiccup.page :refer [include-css include-js html5]]
            [hiccup.element :refer [javascript-tag]]
            
            [cheshire.core :as json]
            [ring.util.response :as ring]
            
            [me.raynes.fs :as fs]

            [dk.ative.docjure.spreadsheet :as xls]
            [xls-sum.excel :refer :all]
            ))


 
(defn json-response
  "Turn the supplied data into a JSON-encoded Ring response."
  [data]
  (-> (ring/response (json/generate-string data {:escape-non-ascii true}))
      (ring/content-type "application/json")))

(defn index-page-handler[]
 (html5
    [:head
     [:meta {:http-equiv "Content-Type" :content "text/html;charset=utf-8"}]
     [:meta {:name "description" :content "Excel sum utility"}]
     [:meta {:name "author"      :content "Andrij Sernyak"}]
     ;[:link {:rel "shortcut icon" :href "/css/favicon.ico" :type "image/x-icon"}]
     [:title "Excel sum"]

     (include-css "/css/styles.css")
     (include-css "/css/jquery.handsontable.full.css")
     
     (include-js "/js/jquery.min.js")
     (include-js "/js/jquery.handsontable.full.js")
     ;(include-js "/extern/d3.js")


     ;(include-edn edn-storage-jsvar {:view (:view-id cfg :index-page)
     ;                                :update-url (:update-url cfg)
     ;                                :cfg data})
     ;(include-js "/js/js.js")
     ] ; head

   [:body
      [:nav {:id "header"}
       [:form
        [:h3 "Fooo"]
        [:input {:type "text" :name "path"}]
        ;[:input {:type "submit" :value "↩"}]
        ]]
      [:div {:id "main"}
        [:aside
           [:ul   [:li "1"] [:li "2"] [:li "3"] [:li "4"] [:li "5"]]]; aside

      [:div {:id "primary"}
       [:div {:id "grid"}]
       ;[:p "Pour-over pariatur brunch banjo before they sold out. Sartorial post-ironic literally semiotics, locavore fingerstache culpa ad food truck street art fixie authentic tote bag incididunt umami. Non fingerstache dreamcatcher, synth ugh yr post-ironic disrupt Banksy commodo. Master cleanse Blue Bottle sint kale chips mumblecore pug, before they sold out gentrify enim quinoa sapiente pop-up. Aute dolor put a bird on it, deep v locavore XOXO wayfarers twee kitsch pork belly ethnic quinoa PBR&B hella sartorial. Plaid gastropub in irony. Est narwhal nesciunt ex sunt photo booth bicycle rights 90's anim, scenester sapiente Wes Anderson."]
       ;[:p "Neutra Thundercats swag artisan PBR&B. Tempor do semiotics, nostrud nulla ad actually anim locavore bespoke Vice consectetur incididunt gluten-free Portland. Gastropub Portland polaroid sriracha readymade, 8-bit sint exercitation aliqua Helvetica consequat roof party Marfa ea adipisicing. Narwhal laborum deep v asymmetrical hella fashion axe. Irure dolore fugiat drinking vinegar, blog excepteur synth farm-to-table mixtape Helvetica chambray readymade esse. Laboris Brooklyn viral VHS pug, gastropub Austin eiusmod bespoke sustainable deserunt Thundercats. Odio consectetur qui deep v."]
       ;[:p "Freegan Tumblr tote bag pickled adipisicing. Umami gastropub lo-fi, nihil letterpress Tumblr Shoreditch Carles culpa you probably haven't heard of them Marfa delectus eiusmod. Lomo pug officia, twee consequat quis magna pork belly hoodie. 3 wolf moon wayfarers PBR aesthetic butcher. Ethnic Austin raw denim, sustainable keytar scenester hoodie freegan do aliquip distillery mustache adipisicing Carles. Raw denim fugiat craft beer wayfarers, cray actually nisi do mlkshk. Keffiyeh flexitarian hella authentic, Cosby sweater umami Etsy gastropub anim drinking vinegar kogi raw denim pour-over jean shorts."]
       ;[:p "Beard pour-over kitsch American Apparel. Art party dolor tattooed, literally swag bitters brunch messenger bag Truffaut slow-carb. Plaid umami synth, swag kogi eiusmod Bushwick veniam artisan XOXO lo-fi typewriter chia. Bicycle rights deserunt elit Tonx, vegan disrupt sunt McSweeney's squid kale chips Truffaut tofu incididunt distillery. Consequat tote bag church-key asymmetrical, beard typewriter duis. Nostrud Banksy drinking vinegar trust fund cray, placeat post-ironic. Sunt jean shorts before they sold out McSweeney's deep v veniam."]
       ] ; primary
    ] ; main
    [:script {:src "/js/grid.js"}]
    ]
   ))

(defn defaults [] 
  (let [path (System/getProperty "user.dir")]
      (json-response 
        {:path path
         :files 
            (map #(.getName %) (fs/find-files path #".*xlsx$"))
         })
    )
  )

(defn- normalize-path[path]
  (if (or (.endsWith path "/")
            (.endsWith path "\\"))
      path
      (str path "/")))

(defn get-data [path file]
    (let [excel (str (normalize-path path) file)
          workbook (xls/load-workbook excel)]
        
        (json-response 
            {:file excel
             :data (get-excel-data workbook)
             ;:data []
             })))


(defroutes app-routes
  ; page routes
  (GET "/"              []     (index-page-handler))

  (GET "/init"         []     (defaults))
  (GET "/data"         {{path :path
                         file :file} :params}  (get-data path file))
  
  (route/resources "/" )
  ;(route/not-found (not-found-page-handler))
  )


(def app-routing (-> #'app-routes
                  ;;rpc/wrap-rpc
                  handler/site))


(defn start [port]
   (run-jetty #'app-routing {:port port}))

(ns wikipedia-search.core
  (:require [clj-http.client :as client])
  (:require [cheshire.core :refer :all])
  (:require [clojure.data.json :as json])
  (:gen-class))


(defn get-extract-info
  "Pulls if Wikipedia page is real, extracts, pageid"
  [input]
  (let [api-url "https://en.wikipedia.org/w/api.php?"]
  (:body (client/get api-url
                     {:cookie-policy :none :query-params
                      {:action "query" :format "json" :prop "extracts"
                       :titles input :formatversion "2"
                       :exsentences "3" :exlimit "1"}}))
  ))

(defn get-page-info
  "Pulls URL"
  [input]
  (let [api-url "https://en.wikipedia.org/w/api.php?"]
  (:body (client/get api-url
                     {:cookie-policy :none :query-params
                      {:action "query" :titles input
                       :prop "info" :inprop "url|talkid"
                       :format "json"}}))
  )
  )

(defn wikipedia-search
  "Search wikipedia for keyword. If exists, returns URL & extract in hash map"
  [input]
  
  ;; First API call - sees if the Wikipedia page exists
  (let [extract-info (parse-string (get-extract-info input) true)
        missing (get-in extract-info [:query :pages 0 :missing])]
      (if missing
        {:success? false}

        ;; If the page does exist, print extract and pulls+prints URL to page
        (do
          (let [extract (get-in extract-info [:query :pages 0 :extract])
                page-info (parse-string (get-page-info input) true)
                pageid (get-in extract-info [:query :pages 0 :pageid])
                url (get-in page-info [:query :pages (keyword (str pageid)) :fullurl])]

            ; Return values in hash map
            {:success? true :extract extract :url url}
            )
          )
        )
      )
  )
   

;; (wikipedia-search "jazz")
;; (wikipedia-search "asdhfoausdf")

(ns wikipedia-search.core
  (:require [clj-http.client :as client])
  (:require [cheshire.core :refer :all])
  (:require [clojure.data.json :as json])
  (:require [clojure.string :as str])
  (:gen-class))


;;
;; Get page info via API call as JSON
;;
(defn get-page-summary
  [search-term]
  (let [api-url (str "https://en.wikipedia.org/api/rest_v1/page/summary/" search-term)]
    (:body (client/get api-url {:cookie-policy :none :throw-exceptions false :as :json}))
    )
  )

;;
;; Check if the Wikipedia page exists
;;
(defn is-page-exists
  [page-summary]
    (if (and (= (get page-summary :title) "Not found.")
             (= (get page-summary :type) "https://mediawiki.org/wiki/HyperSwitch/errors/not_found"))
      false
      true)
  )


;;
;; Check if the title of the Wikipedia page matches user input
;; Prevents redirected pages & subsection errors
;;
(defn is-title-input
  [page-summary input]
  (if (= (str/upper-case(get page-summary :title)) (str/upper-case input))
    true
    false)
  )

;;
;; Search Wikipedia for input. If exists, returns URL & extract in hash map.
;;
(defn wiki-search
  [input]

  ;; Check if the Wikipedia page exists
  (let [page-summary (get-page-summary input)]

    ;; If it passes preconditions, return extract + URL from page-summary 
    (if (and (is-page-exists [page-summary])
             (is-title-input page-summary input))
      (do (let [extract (get page-summary :extract)
                extract-html (get page-summary :extract_html)
                url (get-in page-summary [:content_urls :desktop :page])
                thumbnail-img (get-in page-summary [:thumbnail :source])
                original-img (get-in page-summary [:originalimage :source])]
        {:success? true :extract extract :extract-html extract-html :url url
         :thumbnail-img thumbnail-img :original-img original-img})
      )
      ;; If not, return false success
      {:success? false})
    )
  )

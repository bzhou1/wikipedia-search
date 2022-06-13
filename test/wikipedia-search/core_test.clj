(ns wikipedia-search.core-test
  (:require [clojure.test :refer :all]
            [wikipedia-search.core :refer :all]))

(deftest a-test
  (is (= (is-page-exists (get-page-summary "Apple")) true))
  (is (= (is-page-exists (get-page-summary "Juji")) true))
  (is (= (is-page-exists (get-page-summary "Stone")) true))
  (is (= (= (is-title-input (get-page-summary "apple") "apple")) true))
  (is (= (= (is-title-input (get-page-summary "apple") "Apple")) true))
  (is (= (= (is-title-input (get-page-summary "apple") "aPPle")) true))
  (is (= (is-title-input (get-page-summary "Juji") "Juji") false))
  (is (= (is-title-input (get-page-summary "stone") "stone") false))
  (is (= (get (wiki-search "juji") :success?) false))
  (is (= (get (wiki-search "stone") :success?) false))
  (is (= (get (wiki-search "apple") :success?) true))

  (is (= (is-page-exists (get-page-summary "President of India")) true))
  (is (= (= (is-title-input (get-page-summary "President of India") "President of India")) true))
  (is (= (get (wiki-search "President of India") :success?) true))
)

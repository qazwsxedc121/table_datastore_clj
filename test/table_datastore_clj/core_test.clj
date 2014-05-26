(ns table-datastore-clj.core-test
  (:require [clojure.test :refer :all]
            [table-datastore-clj.core :refer :all]))

(deftest create-test
  (testing "build table"
    (let [t (table [:a :b] [[1 2] [3 4]])]
      (is (= (:schema t) [:a :b])))))

(def t (table ["name" "sex" "age"] [["john" "M" 13] ["marry" "F" 13]]))

(deftest find-test
  (testing "find one"
    (is (= (find-one t {"sex" "F"}) ["marry" "F" 13])))

  (testing "find one with key and value"
    (is (= (find-one-k-v t "sex" "F") ["marry" "F" 13])))

  (testing "find all as map"
    (is (= (find-all-as-map t {"age" 13})
            (list {"name" "john", "sex" "M", "age" 13} {"name" "marry", "sex" "F", "age" 13}))))

  (testing "searching"
    (is (empty? (table-search t "r")))
    (is (= (first (table-search t "marry")) ["marry" "F" 13]))))

(deftest convert-test
  (testing "convert zipmap"
    (is (= (convert-map-k-k t "name" "sex")
           {"john" "M" "marry" "F"}))))



(ns table-datastore-clj.core-test
  (:require [clojure.test :refer :all]
            [table-datastore-clj.core :refer :all]))

(deftest a-test
  (testing "build table"
    (let [t (table [:a :b] [[1 2] [3 4]])]
      (is (= (:schema t) [:a :b])))))

(deftest b-test
  (let [t (table  ["name" "sex" "age"] [["john" "M" 13] ["marry" "F" 13]])]
    (testing "find one"
      (is (= (get t {"sex" "F"}) ["marry" "F" 13])))
    (testing "convert zipmap"
      (is (= (convert-map-k-k t "name" "sex")
             {"john" "M" "marry" "F"})))))



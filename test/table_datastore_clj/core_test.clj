(ns table-datastore-clj.core-test
  (:require [clojure.test :refer :all]
            [table-datastore-clj.core :refer :all]))

(deftest create-test
  (testing "build table"
    (let [t (table [:a :b] [[1 2] [3 4]])]
      (is (= (:schema t) [:a :b])))))

(def test-john-v ["john" "M" 13])

(def test-marry-v ["marry" "F" 13])

(def test-john {"name" "john" "sex" "M" "age" 13})

(def test-marry {"name" "marry" "sex" "F" "age" 13})

(def test-table (table ["name" "sex" "age"] [test-john-v test-marry-v]))

(defn set= [a b]
  (= (set a) (set b)))

(deftest find-test
  (testing "find one"
    (is (= (find-one test-table {"sex" "F"}) test-marry-v))
    (is (= (find-one-as-map test-table {"sex" "F"}) test-marry)))

  (testing "find one with key and value"
    (is (= (find-one-k-v test-table "sex" "F") test-marry-v))
    (is (= (find-one-k-v-as-map test-table "sex" "F") test-marry)))

  (testing "find all"
    (is (set= (find-all test-table {"age" 13})
              (list test-john-v test-marry-v)))
    (is (set= (find-all-as-map test-table {"age" 13})
              (list test-john test-marry))))

  (testing "find all with key and value"
    (is (set= (find-all-k-v test-table "age" 13)
              (list test-john-v test-marry-v)))
    (is (set= (find-all-k-v-as-map test-table "age" 13)
              (list test-john test-marry))))

  (testing "searching"
    (is (empty? (table-search test-table "r")))
    (is (= (first (table-search test-table "marry")) test-marry-v))))

(deftest convert-test
  (testing "convert to a map easy for use"
    (is (= (convert-map-k-k test-table "name" "sex")
           {"john" "M" "marry" "F"}))))

(deftest columns-test
  (testing "columns"
    (is (= (columns test-table "name")
           (list"john" "marry")))
    (is (= (columns test-table "age")
           (list 13 13)))))



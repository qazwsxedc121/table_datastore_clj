(ns table-datastore-clj.serialize-test
  (:require [clojure.test :refer :all]
            [table-datastore-clj.core :refer :all]
            [table-datastore-clj.serialize :refer :all]))

(def test-john-v ["john" "M" "13"])

(def test-marry-v ["marry" "F" "13"])

(def test-table (table ["name" "sex" "age"] [test-john-v test-marry-v]))

(deftest csv-test
  (testing "reading"
    (is (= (from-csv "resources/test/test_table.csv")
           test-table))))

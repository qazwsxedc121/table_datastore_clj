(ns table-datastore-clj.core-test
  (:require [clojure.test :refer :all]
            [table-datastore-clj.core :refer :all]))

(deftest a-test
  (testing "build table"
    (let [t (table [:a :b] [[1 2] [3 4]])]
      (is (= (:schema t) [:a :b]))))
  (testing "find one"
    (let [t (table [:a :b] [[1 2] [3 4]])]
      (is (= (fo t {:b 2}) [1 2])))))



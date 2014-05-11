(ns table-datastore-clj.serialize
  (:require [table-datastore-clj.core :as core]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(defn from-csv [filepath]
  "generate a table from csv-file, all fields should be String"
  (with-open [infile (io/reader filepath)]
    (let [data-with-head (doall (csv/read-csv infile))
          head (first data-with-head)
          data (rest data-with-head)]
      (core/table head data))))


(defn to-csv [filepath table]
  "read csv file and get a table, all fields should be String"
  (with-open [outfile (io/writer filepath)]
    (csv/write-csv outfile [(:schema table)])
    (csv/write-csv outfile (:data table))))


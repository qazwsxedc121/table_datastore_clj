(ns table-datastore-clj.core)


(defprotocol IDataTable
  (fo [t c] "find one"))

(declare find-one)

(defrecord DataTable [schema data]
  IDataTable
  (fo [t c] (find-one t c)))

(defn table
  ([schema] (table schema []))
  ([schema initdata]
   (DataTable. schema initdata)))

;;(def table-example (table ["name" "sex" "age"] [["john" "M" 13] ["marry" "F" 13]]))

(defn to-map [t i]
;;(to-map table-example ["john" "M" 13])
;;=> {"name" "john" "sex" "M" "age" 13}
  (let [schema (:schema t)]
    (zipmap schema i)))

(defn to-maps [t]
;;(to-maps table-example)
;;=> ({"name" "john" "sex" "M" "age" 13} {"name" "marry" "sex" "F" "age" 13})
  (let [schema (:schema t)
        data (:data t)]
    (map #(zipmap schema %) data)))

(defn find-one-k-v [t k v]
  "find one entry in table , params(t=table k=key v=value), return a array"
;;(find-one-k-v table-example "age" 13)
;;=> ["john" "M" 13]
  (let [key-index (.indexOf (:schema t) k)]
    (loop [r (:data t)]
      (cond
       (empty? r) nil
       (= (nth (first r) key-index) v) (first r)
       :else (recur (rest r))))))

(defn find-one-k-v-as-map [t k v]
  "find one entry as a map in table"
;;(find-one-k-v-as-map table-example "age" 13)
;;=> {"name" "john", "sex" "M", "age" 13}
  (to-map t (find-one-k-v t k v)))

(defn find-all-k-v [t k v]
  "find all entry in table , params(t=table k=key v=value), return a list of array"
;;(find-all-k-v table-example "age" 13)
;;=> (["john" "M" 13] ["marry" "F" 13])
  (let [key-index (.indexOf (:schema t) k)]
    (filter #(= (nth % key-index) v) (:data t))))

(defn find-all-k-v-as-map [t k v]
  "find all entry as map in table"
;;(find-all-k-v-as-map table-example "age" 13)
;;=> ({"name" "john", "sex" "M", "age" 13} {"name" "marry", "sex" "F", "age" 13})
  (map #(to-map t %)
       (find-all-k-v t k v)))

(defn entry-fits-condition [s e c]
  "if the entry suits condition, params(s=schema c=condition e=entry)"
;;(entry-fits-condition (:schema table-example) ["marry" "F" 13] {"sex" "F"})
;;=> true
;;(entry-fits-condition (:schema table-example) ["marry" "F" 13] {"age" 12})
;;=> false
  (let [c-key-index (map #(.indexOf s %) (keys c))
        c-vals (vals c)
        e-vals (map #(nth e %) c-key-index)]
    (= c-vals e-vals)))

(defn find-one [t c]
  "find one entry in table , params(t=table c=condition), return a array"
;;(find-one table-example {"age" 13})
;;=> ["john" "M" 13]
  (loop [r (:data t)]
    (cond
     (empty? r) nil
     (entry-fits-condition (:schema t) (first r) c) (first r)
     :else (recur (rest r)))))

(defn find-all [t c]
  "find all entry in table with condition, params(t=table c=condition), return arrays"
;;(find-all table-example {"age" 13})
;;=> ({"name" "john", "sex" "M", "age" 13} {"name" "marry", "sex" "F", "age" 13})
  (let [schema (:schema t)
        data (:data t)]
    (filter #(entry-fits-condition schema % c) data)))

(defn table-search [t w]
  "search in the table for keyword, params(t=table w=word)"
  (let [schema (:schema t)
        data (:data t)]
    (filter #((set %) w) data)))

(defn table-sort [t c]
  "sort data in table by column, params(t=table c=column)"
  (let [schema (:schema t)
        column-index (.indexOf schema c)
        data (:data t)]
    (table schema
           (sort-by #(% column-index) data))))

(defn columns [t k]
  "all values in one columns as a list(not a set)"
;;(columns table-example "name")
;;=> ("john" "marry")
  (let [key-index (.indexOf (:schema t) k)]
    (map #(nth % key-index) (:data t))))

(defn insert [t e]
  "insert one entry to table, params(t=table e=entry)"
;;(insert table-example ["alex" "M" 14])
;;=> {:schema ["name" "sex" "age"], :data [["john" "M" 13] ["marry" "F" 13] ["alex" "M" 14]]}
  (table (:schema t)
         (conj (:data t) e)))

(defn vec-delete [v i]
  (vec (concat (subvec v 0 i) (subvec v (inc i) (count v)))))

(defn delete-one-index [t i]
  "delete one entry by index, params(t=table i=index)"
;;(delete-one-index table-example 0)
;;=> {:schema ["name" "sex" "age"], :data [["marry" "F" 13]]}
  (table (:schema t)
         (vec-delete (:data t) i)))

(defn delete [t c]
  "find all entry in table with condition, params(t=table c=condition)"
;;(delete table-example {"sex" "M"})
;;=> {:schema ["name" "sex" "age"], :data [["marry" "F" 13]]}
  (let [schema (:schema t)
        data (:data t)]
    (table schema
           (filter #(not (entry-fits-condition schema % c)) data))))

(defn pprint
  "print table to string beautifuly"
;;(pprint table-example 10)
;;=> "name sex  age  \njohn M    13   \nmarryF    13   "
  ([t] (pprint t 20))
  ([t max-length]
   (let [format-word (fn [w] (format (str "%-" max-length "s") w))]
     (str (apply str (map format-word (t :schema)))
          (apply str (map #(apply str "\n" (map format-word %)) (t :data)))))))

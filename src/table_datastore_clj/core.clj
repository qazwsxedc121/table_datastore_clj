(ns table-datastore-clj.core)

(defn table
  ([schema] (table schema []))
  ([schema initdata]
   {:schema schema :data initdata}))

;;(def table-example ["name" "sex" "age"] [["john" "M" 13] ["marry" "F" 13]])

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

(defn columns [t k]
  (let [key-index (.indexOf (:schema t) k)]
    (map #(nth % key-index) (:data t))))

(defn to-map [t i]
  (let [schema (:schema t)]
    (zipmap schema i)))

(defn to-maps [t]
  (let [schema (:schema t)
        data (:data t)]
    (map #(zipmap schema %) data)))



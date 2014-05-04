(ns table-datastore-clj.core)

(defn table
  ([schema] (table schema []))
  ([schema initdata]
   {:schema schema :data initdata}))

(defn find-one-k-v [t k v]
  (let [key-index (.indexOf (:schema t) k)]
    (loop [r (:data t)]
      (cond
       (empty? r) nil
       (= (nth (first r) key-index) v) (first r)
       :else (recur (rest r))))))

(defn find-all-k-v [t k v]
  (let [key-index (.indexOf (:schema t) k)]
    (filter #(= (nth % key-index) v) (:data t))))

(defn columns [t k]
  (let [key-index (.indexOf (:schema t) k)]
    (map #(nth % key-index) (:data t))))

(defn to-maps [t]
  (let [schema (:schema t)
        data (:data t)]
    (map #(zipmap schema %) data)))



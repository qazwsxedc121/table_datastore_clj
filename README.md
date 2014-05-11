# table_datastore_clj

A little library to keep simple data table in memory.

A "Table" is a vector of items with same sechema.

## Usage

### new table

```
(def table-example (table ["name" "sex" "age"] [["john" "M" 13] ["marry" "F" 13]]))
```

### find using key and value

```
(find-one-k-v table-example "age" 13)
=> ["john" "M" 13]

(find-one-k-v-as-map table-example "age" 13)
=> {"name" "john", "sex" "M", "age" 13}

(find-all-k-v table-example "age" 13)
=> (["john" "M" 13] ["marry" "F" 13])

(find-all-k-v-as-map table-example "age" 13)
=> ({"name" "john", "sex" "M", "age" 13} {"name" "marry", "sex" "F", "age" 13})
```

### pretty print to string

```
(pprint table-example 10)
=> "name sex  age  \njohn M    13   \nmarryF    13   "
```

## License

Copyright © 2014 qazwsxedc121@gmail.com

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

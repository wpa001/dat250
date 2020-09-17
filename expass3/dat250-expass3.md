• No errors or problems wile downloading MongoDB. Everything not only went fast but also smoothly :)

## Validation of the installation package
![validation](png/package-validation.PNG)

## Experiment 1 (CRUD Operantions)
##### • Insert Documents:
![insert](png/insertMany.PNG)

##### • Query Documents:
![query](png/and-or-query.PNG)

##### • Update Documents:
![update](png/update-documents.PNG)

##### • Remove Documents:
![remove](png/deleteMany.PNG)

##### • Bulk Write Operation:
![remove](png/bulkWrite.PNG)


## Experiment 2 (Aggregation)

##### • Example given to test:
![wokring-example](png/mapReduce.PNG)

##### • Own created mapReduce:
![remove](png/mapReduceOwn.PNG)    
  
```java
var mapFunction2 = function() {
    for (var i = 0; i < this.items.length; i++) {
        var key = this.items[i].sku;
        var value = { count: 1, qty: this.items[i].qty };
        
        emit(key, value);
    }
};

var reduceFunction2 = function(keySKU, countObjVals) {
    reducedVal= 0;

    for (var i = 0; i < countObjVals.length; i++) {
        reducedVal += countObjVals[i].qty;
    }

    return reducedVal;
};

db.orders.mapReduce(
    mapFunction2,
    reduceFunction2,
    { out: { merge: "map_reduce_example2" } }
);
```


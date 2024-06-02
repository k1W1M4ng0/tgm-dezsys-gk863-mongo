# SYT Dezentrale Systeme GK863 Document Oriented Middleware using MongoDB

## Task description

The task description: [Task description](./TASK.md)

## Questions

### Nennen Sie 4 Vorteile eines NoSQL Repository im Gegensatz zu einem relationalen DBMS

- Flexibilität: NoSQL DB sind nicht auf Tabellen begrenzt, und können unterschiedliche Schemas speichern, wie zb Objekte.
- Erweiterbarkeit: Wenn ein Schema geändert werden muss, kann das leichter gemacht werden.
- Volumen: Kann große Mengen an Daten schnell speichern.
- Einfache Skalierung: Man kann einfach neue Knoten hinzufügen, um zu skalieren.

### Nennen Sie 4 Nachteile eines NoSQL Repository im Gegensatz zu einem relationalen DBMS

- Kein Standard: Viele DBMS haben ihr eigenes System, es gibt daher sehr viele unterschiedliche DBMS, welche alle zuerst erlernt werden müssen.
- Kein SQL: SQL ist eine etablierte, gut funktionale Sprache (aber nur für Tabellen), und viele Nosql DBMS haben ihre eigene Sprache.
- Schwächere Abfragen: Komplexere Abfragen sind schwieriger als mit relationalen DBs + SQL.
- Kein ACID: Viele NoSQL DBs bieten kein ACID -> Daten könnten inkonsistent sein.

### Welche Schwierigkeiten ergeben sich bei der Zusammenführung der Daten?

Wenn Daten in einer unterschiedlichen Struktur sind, dann kann die Zusammenführung schwierig sein, weil man sie zb transformieren muss.

### Welche Arten von NoSQL Datenbanken gibt es?

Beliebte Arten sind:  
- Document-oriented
- Key-value
- Wide-column
- Graph-based

Es gibt auch noch zahlreiche Sub-Arten usw., da es kein Standard gibt und Unternehmen immer wieder neue erfinden, mit einem neuen Namen.

### Nennen Sie einen Vertreter für jede Art?

- Document-oriented: MongoDB
- Key-value: Redis
- Wide-column: Cassandra
- Graph-based: Neo4j

### Beschreiben Sie die Abkürzungen CA, CP und AP in Bezug auf das CAP Theorem

In verteilten Systemen ist es unmöglich, Consistency, Availability und Partition Tolerance gleichzeitig zu garantieren, 2 schließen immer das dritte aus.

- C: Die Daten sind auf allen Systemen gleich. Wenn also Daten geschrieben werden, müssen sie gleich an alle Systeme weitergeleitet werden.
- A: Jeder Client muss eine Antwort bekommen, auch wenn Nodes down sind
- P: Ein System muss weiterarbeiten, wenn die Kommunikation zwischen Nodes entfällt.

- CA: Kein P = Keine Ausfallssicherheit. Es bietet C und A, wenn garantiert kein Kommunikationsausfall eintritt. (In der Praxis bei verteilten Systemen nicht möglich)
- CP: Kein A = Wenn die Kommunikation zwischen Nodes entfällt (Partition), dann muss ein Node ausgeschalten werden.
- AP: Kein C = Daten werden neu synchronisiert nach einem Kommunikationsausfall.

### Mit welchem Befehl koennen Sie den Lagerstand eines Produktes aller Lagerstandorte anzeigen.



### Mit welchem Befehl koennen Sie den Lagerstand eines Produktes eines bestimmten Lagerstandortes anzeigen.




## Implementation

### Docker container

```bash
docker pull mongo
docker run -d -p 27017:27017 --name mongo mongo
docker exec -it mongo bash

# in docker exec bash
mongosh

show dbs
```

### WarehouseData

The entity class for this object is pretty basic, i just added an @Id annotation:

```java

    private String warehouseApplicationID;

    @Id
	private String warehouseID;
	private String warehouseName;
	private String warehouseAddress;
	private String warehousePostalCode;
	private String warehouseCity;
	private String warehouseCountry;
	private String timestamp;

    // @DocumentReference
	private ArrayList<Product> productData;
```

There is also an empty constructor, setters and getters.

I had @DocumentReference there to separate Product and WarehouseData into separate tables,
however, i chose to comment out this because i wanted to have the products in the warehouses due to 
the product having quantity as attribute.

### Product

For the entity class for Product, there was also just an @Id annotation added, with a constructor, setters and getters.

```java
    @Id
    private Integer productID;
    private String productName;
    private String productCategory;
    private int productQuantity;
    private String productUnit;
```

### WarehouseRepository

The repository extends MongoRepository. One can add abstract methods that will automatically get implemented
with their method signatures, such as in the following example, but i did not use them as the task was to
just create mongosh queries.

```java

public interface WarehouseRepository extends MongoRepository<WarehouseData, String> {

    public WarehouseData findByWarehouseID(String warehouseID);

}
```

### Application

The main application has an autowired WarehouseRepository: 

```java

	@Autowired
	private WarehouseRepository repository;
```

Every 5 seconds, 4 products are saved:

```java
        while(true) {
            for(int i = 1; i < 5; ++i) {

                
                String url = String.format("http://localhost:%d/warehouse/%d/data", port, (port == 8081) ? i : port+i);

                logger.info("get data from url " + url.toString());

                WarehouseData whData = new RestTemplate().getForObject(url, WarehouseData.class);
                whData.addProduct(myProduct);
                logger.info("got data " + whData.toString());

                repository.save(whData);

                logger.info("saved in repo");

            }

            Thread.sleep(5000);
        }
```

They are saved with `repository.save(whData)`. This updates entries if they were already in the db.

## Query tasks 

Possible tasks and queries could be:

### Which warehouses have less than 20 of any single product in stock?

```mongo
db.warehouseData.find({
    'productData.productQuantity': {$lt: 20}
})
```

### How many products does each warehouse have?

```mongo
db.warehouseData.aggregate({
    $project: {
      _id: 1,
      'productData._id': 1,
      'productData.productQuantity': 1
    }
})
```

### Which 3 warehouses have the oldest data?

```mongo
db.warehouseData.aggregate([
    {
        $sort: {
          timestamp: -1
        }
    },
    {
        $limit: 3
    }
])
```


## Sources

- [Spring blog on MongoDB relation modeling](https://spring.io/blog/2021/11/29/spring-data-mongodb-relation-modelling)

- [dataversity - nosql-databases-advantages-and-disadvantages](https://www.dataversity.net/nosql-databases-advantages-and-disadvantages/)
- [Wikipedia - NoSQL](https://de.wikipedia.org/wiki/NoSQL)
- [Wikipedia - CAP Theorem](https://de.wikipedia.org/wiki/CAP-Theorem)
- [IBM - CAP Theorem](https://www.ibm.com/topics/cap-theorem)
- [Mongo Query Reference - Project](https://www.mongodb.com/docs/manual/reference/operator/aggregation/project/)

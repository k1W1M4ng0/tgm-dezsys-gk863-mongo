package warehouse.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import warehouse.model.Product;
import warehouse.model.WarehouseData;

public interface ProductRepository extends MongoRepository<Product, String> {

    public Product findByProductID(String productID);
    // public List<ProductData> findByWarehouseID(String warehouseID);

 }

package warehouse;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import warehouse.model.Product;
import warehouse.model.WarehouseData;
import warehouse.repository.WarehouseRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private WarehouseRepository repository;

	public static void main(String[] args) {
        
        int port = Integer.parseInt(args[0]);

        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(
           Collections.singletonMap("server.port", port)
        );
        app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {

        if(args.length < 2) {
            logger.error("no port for the warehouse rest producer entered!");
            return;
        }

        Integer port = 8081;
        port = Integer.parseInt(args[1]);


		// repository.deleteAll();

        // my custom product that each warehouse has 100 of (for a query)
        Product myProduct = new Product();
        myProduct.setProductID(12345); 
        myProduct.setProductName("Gourmet Coffee"); 
        myProduct.setProductCategory("Food & Beverages"); 
        myProduct.setProductQuantity(100); 
        myProduct.setProductUnit("kg"); 

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




		// Initialize product data repository
		// repository.deleteAll();

		// save a couple of product data
		// repository.save(new ProductData("1","00-443175","Bio Orangensaft Sonne","Getraenk", 2500));
		// repository.save(new ProductData("1","00-871895","Bio Apfelsaft Gold","Getraenk", 3420));
		// repository.save(new ProductData("1","01-926885","Ariel Waschmittel Color","Waschmittel", 478));
		// repository.save(new ProductData("1","02-234811","Mampfi Katzenfutter Rind","Tierfutter", 1324));
		// repository.save(new ProductData("2","03-893173","Saugstauberbeutel Ingres","Reinigung", 7390));
        // repository.save(new WarehouseData());

		// fetch all products
		// System.out.println("ProductData found with findAll():");
		// System.out.println("-------------------------------");
		// for (var productdata : repository.findAll()) {
		// 	System.out.println(productdata);
		// }
		// System.out.println();
		//
		// // Fetch single product
		// System.out.println("Record(s) found with ProductID(\"1\"):");
		// System.out.println("--------------------------------");
		// System.out.println(repository.findByProductID("00-871895"));
		// System.out.println();
		//
		// // Fetch all products of Warehouse 1
		// System.out.println("Record(s) found with findByWarehouseID(\"1\"):");
		// System.out.println("--------------------------------");
		// for (ProductData productdata : repository.findByWarehouseID("1")) {
		// 	System.out.println(productdata);
		// }
		// System.out.println();
		//
		// // Fetch all products of Warehouse 2
		// System.out.println("Record(s) found with findByWarehouseID(\"2\"):");
		// System.out.println("--------------------------------");
		// for (ProductData productdata : repository.findByWarehouseID("2")) {
		// 	System.out.println(productdata);
		// }

	}

}

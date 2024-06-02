package warehouse.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


public class WarehouseData {
	
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

	/**
	 * Constructor
	 */
	public WarehouseData() {
		this.productData = new ArrayList<>();
		this.timestamp = LocalDateTime.now().toString();
	}
	
	/**
	 * Setter and Getter Methods
	 */
	public String getWarehouseID() {
		return warehouseID;
	}

	public String getWarehouseApplicationID() {
        return warehouseApplicationID;
    }

    public void setWarehouseApplicationID(String warehouseApplicationID) {
        this.warehouseApplicationID = warehouseApplicationID;
    }

    public void setProductData(ArrayList<Product> productData) {
        this.productData = productData;
    }

    public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
	/**
	 * @return the warehouseAddress
	 */
	public String getWarehouseAddress() {
		return warehouseAddress;
	}

	/**
	 * @param warehouseAddress the warehouseAddress to set
	 */
	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}
	
	/**
	 * @return the warehousePostalCode
	 */
	public String getWarehousePostalCode() {
		return warehousePostalCode;
	}

	/**
	 * @param warehousePostalCode the warehousePostalCode to set
	 */
	public void setWarehousePostalCode(String warehousePostalCode) {
		this.warehousePostalCode = warehousePostalCode;
	}

	/**
	 * @return the warehouseCity
	 */
	public String getWarehouseCity() {
		return warehouseCity;
	}
	
	/**
	 * @param warehouseCity the warehouseCity to set
	 */
	public void setWarehouseCity(String warehouseCity) {
		this.warehouseCity = warehouseCity;
	}
	
	/**
	 * @return the warehouseCountry
	 */
	public String getWarehouseCountry() {
		return warehouseCountry;
	}

	/**
	 * @param warehouseCountry the warehouseCountry to set
	 */
	public void setWarehouseCountry(String warehouseCountry) {
		this.warehouseCountry = warehouseCountry;
	}

	public void addProduct(Product p) {
		this.productData.add(p);
	}
	
	/**
	 * @return the productData
	 */
	public ArrayList<Product> getProductData() {
		return productData;
	}
	
	/**
	 * Methods
	 */
	@Override
	public String toString() {
		String info = String.format("Warehouse Info: ID = %s, timestamp = %s", warehouseID, timestamp.toString() );
		return info;
	}

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

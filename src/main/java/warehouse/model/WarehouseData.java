package warehouse.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


public class WarehouseData {
	
    private String warehouseApplicationID;

    @Id
	private Integer warehouseID;
	private String warehouseName;
	private String warehouseAddress;
	private String warehousePostalCode;
	private String warehouseCity;
	private String warehouseCountry;
	private String timestamp;

    @DocumentReference
	private ArrayList<Product> productData;

	/**
	 * Constructor
	 */
	public WarehouseData() {
		this.productData = new ArrayList<>();
		this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	}
	
	/**
	 * Setter and Getter Methods
	 */
	public Integer getWarehouseID() {
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

    public void setWarehouseID(Integer warehouseID) {
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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
		String info = String.format("Warehouse Info: ID = %s, timestamp = %s", warehouseID, timestamp );
		return info;
	}
}

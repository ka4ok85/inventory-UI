package restmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restatementjob {

    @JsonProperty
    private String id;

    @JsonProperty
    private String productId;

    @JsonProperty
    private String productName;

    @JsonProperty
    private String storeName;

    @JsonProperty
    private String storeId;

    @JsonProperty
    private String storelocationId;
    
    @JsonProperty
    private String storelocationShelf;
    
    @JsonProperty
    private String storelocationSlot;

    @JsonProperty
    private String expectedQuantity;

    @JsonProperty
    private String status;

    @JsonProperty
    private String userLogin;

    @JsonProperty
    private String userId;

    @JsonProperty
    private String dateAdded;

    @JsonProperty
    private String dateProcessed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setStorelocationId(String storelocationId) {
		this.storelocationId = storelocationId;
	}

	public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStorelocationId() {
        return storelocationId;
    }

    public void setStorelocation(String storelocationId) {
        this.storelocationId = storelocationId;
    }

    public String getStorelocationShelf() {
        return storelocationShelf;
    }

    public void setStorelocationShelf(String storelocationShelf) {
        this.storelocationShelf = storelocationShelf;
    }

    public String getStorelocationSlot() {
        return storelocationSlot;
    }

    public void setStorelocationSlot(String storelocationSlot) {
        this.storelocationSlot = storelocationSlot;
    }

    public String getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(String expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(String dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

	@Override
	public String toString() {
		return "Restatementjob [id=" + id + ", productId=" + productId + ", productName=" + productName + ", storeName="
				+ storeName + ", storeId=" + storeId + ", storelocationId=" + storelocationId + ", storelocationShelf="
				+ storelocationShelf + ", storelocationSlot=" + storelocationSlot + ", expectedQuantity="
				+ expectedQuantity + ", status=" + status + ", userLogin=" + userLogin + ", userId=" + userId
				+ ", dateAdded=" + dateAdded + ", dateProcessed=" + dateProcessed + "]";
	}



}

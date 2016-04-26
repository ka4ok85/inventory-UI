package restmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restatementjob {


	@JsonProperty
	private String id;

	@JsonProperty
	private String product;

	@JsonProperty
	private String storeName;

	@JsonProperty
	private String storeId;

	@JsonProperty
	private String storelocation;

	@JsonProperty
	private String expectedQuantity;

	@JsonProperty
	private String status;

	@JsonProperty
	private String username;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStorelocation() {
		return storelocation;
	}

	public void setStorelocation(String storelocation) {
		this.storelocation = storelocation;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Restatementjob [id=" + id + ", product=" + product + ", storeId=" + storeId + ", storeName=" + storeName + ", storelocation="
				+ storelocation + ", expectedQuantity=" + expectedQuantity + ", status=" + status + ", username="
				+ username + "]";
	}


}
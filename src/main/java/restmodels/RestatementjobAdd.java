package restmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestatementjobAdd {

    @JsonView(restmodels.RestatementjobAdd.class)
    private Long id;

    @JsonView(restmodels.RestatementjobAdd.class)
    private Long productId;

    @JsonView(restmodels.RestatementjobAdd.class)
    private Long storeId;

    @JsonView(restmodels.RestatementjobAdd.class)
    private Long userId;
    
    @JsonView(restmodels.RestatementjobAdd.class)
    private Long expectedQuantity;

    @JsonView(restmodels.RestatementjobAdd.class)
    private Long storelocationId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getExpectedQuantity() {
		return expectedQuantity;
	}

	public void setExpectedQuantity(Long expectedQuantity) {
		this.expectedQuantity = expectedQuantity;
	}

	public Long getStorelocationId() {
		return storelocationId;
	}

	public void setStorelocationId(Long storelocationId) {
		this.storelocationId = storelocationId;
	}

	@Override
	public String toString() {
		return "RestatementjobAdd [id=" + id + ", productId=" + productId + ", storeId=" + storeId + ", userId="
				+ userId + ", expectedQuantity=" + expectedQuantity + ", storelocationId=" + storelocationId + "]";
	}
    
    
}

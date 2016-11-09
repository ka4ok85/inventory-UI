package restmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductLocationChange {

    @JsonView(restmodels.ProductLocationChange.class)
    private Long productId;

    @JsonView(restmodels.ProductLocationChange.class)
    private Long locationId;

    @JsonView(restmodels.ProductLocationChange.class)
    private Long quantity;
    
    @JsonView(restmodels.ProductLocationChange.class)
    private String action;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "ProductLocationChange [productId=" + productId + ", locationId=" + locationId + ", quantity=" + quantity
				+ ", action=" + action + "]";
	}

}

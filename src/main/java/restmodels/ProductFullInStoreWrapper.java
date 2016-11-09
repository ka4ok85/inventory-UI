package restmodels;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductFullInStoreWrapper {

	@JsonProperty
    private Product product;

    @JsonProperty
    private Long totalQuantity;

    @JsonProperty
    private ArrayList<StorelocationQuantityWrapper> storelocationQuantityWrappers;
    
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	
	

	public ArrayList<StorelocationQuantityWrapper> getStorelocationQuantityWrappers() {
		return storelocationQuantityWrappers;
	}

	public void setStorelocationQuantityWrappers(ArrayList<StorelocationQuantityWrapper> storelocationQuantityWrappers) {
		this.storelocationQuantityWrappers = storelocationQuantityWrappers;
	}

	@Override
	public String toString() {
		return "ProductFullInStoreWrapper [product=" + product + ", totalQuantity=" + totalQuantity
				+ ", storelocationQuantityWrappers=" + storelocationQuantityWrappers + "]";
	}



}

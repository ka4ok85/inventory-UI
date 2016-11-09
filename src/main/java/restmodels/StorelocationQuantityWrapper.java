package restmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StorelocationQuantityWrapper {

    @JsonProperty
    private Storelocation storelocation;
    
    @JsonProperty
    private Long quantity;

	public Storelocation getStorelocation() {
		return storelocation;
	}

	public void setStorelocation(Storelocation storelocation) {
		this.storelocation = storelocation;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "StorelocationQuantityWrapper [storelocation=" + storelocation + ", quantity=" + quantity + "]";
	}
	
    
}

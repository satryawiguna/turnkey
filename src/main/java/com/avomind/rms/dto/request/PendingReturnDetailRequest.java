package com.avomind.rms.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class PendingReturnDetailRequest {

	@NotEmpty(message = "SKU is required")
	private String sku;
	
	@Min(value = 1, message = "Minimum quantity should be greater than 0")
	private int quantity;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	} 
	
}

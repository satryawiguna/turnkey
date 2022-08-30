package com.avomind.rms.dto.response;

public class UpdateStatusOrderReturnResponse {

	private String token;
	
	private String sku;
	
	private int returnQuantity;
	
	private Double returnPrice;
	
	private String status;
	
	
	
	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public String getSku() {
		return sku;
	}



	public void setSku(String sku) {
		this.sku = sku;
	}



	public int getReturnQuantity() {
		return returnQuantity;
	}



	public void setReturnQuantity(int returnQuantity) {
		this.returnQuantity = returnQuantity;
	}



	public Double getReturnPrice() {
		return returnPrice;
	}



	public void setReturnPrice(Double returnPrice) {
		this.returnPrice = returnPrice;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}
}

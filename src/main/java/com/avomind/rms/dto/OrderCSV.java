package com.avomind.rms.dto;

public class OrderCSV {

	private String orderId;
	
	private String emailAddress;
	
	private String sku;
	
	private int quantity;
	
	private Double price;
	
	private String itemName;
	
	public OrderCSV(String orderId, String emailAddress, String sku, int quantity, Double price, String itemName) {
		this.orderId = orderId;
		this.emailAddress = emailAddress;
		this.sku = sku;
		this.quantity = quantity;
		this.price = price;
		this.itemName = itemName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}

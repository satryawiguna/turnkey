package com.avomind.rms.dto;

import java.util.List;

public class OrderCSVWithMessage extends Messages {
	
	private List<OrderCSV> orderCSVs;
																																																															
	public OrderCSVWithMessage(List<OrderCSV> orderCSVs, List<String> messages) {
		this.orderCSVs = orderCSVs;
		this.messages = messages;
	}

	public List<OrderCSV> getOrderCSVs() {
		return orderCSVs;
	}

	public void setOrderCSVs(List<OrderCSV> orderCSVs) {
		this.orderCSVs = orderCSVs;
	}
}

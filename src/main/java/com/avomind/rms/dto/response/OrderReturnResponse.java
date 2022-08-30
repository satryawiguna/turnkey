package com.avomind.rms.dto.response;

import java.util.List;

public class OrderReturnResponse {

	private Long id;
	
	private String token;
	
	private List<OrderReturnDetailResponse> orderReturnDetailResponse;
	
	private Double totalRefund;

	public OrderReturnResponse() {
	}
	
	public OrderReturnResponse(Long id, String token, List<OrderReturnDetailResponse> orderReturnDetailResponse) {
		this.id = id;
		this.token = token;
		this.orderReturnDetailResponse = orderReturnDetailResponse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<OrderReturnDetailResponse> getOrderReturnDetailResponse() {
		return orderReturnDetailResponse;
	}

	public void setOrderReturnDetailResponse(List<OrderReturnDetailResponse> orderReturnDetailResponse) {
		this.orderReturnDetailResponse = orderReturnDetailResponse;
	}

	public Double getTotalRefund() {
		Double totalRefund = 0.00;
		
		for (OrderReturnDetailResponse orderReturnDetailResponse : this.getOrderReturnDetailResponse()) {
			if (!orderReturnDetailResponse.getStatus().equals("REJECTED"))
				totalRefund += orderReturnDetailResponse.getReturnPrice();
		}
		
		return totalRefund;
	}

}

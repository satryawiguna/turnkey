package com.avomind.rms.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class PendingReturnRequest {

	@NotEmpty(message = "Order id is required")
	private String orderId;
	
	@NotEmpty(message = "Email address is required")
	@Email(message = "Invalid format email address")
	private String emailAddress;
	
	@NotEmpty(message = "Order detail is required")
	@Valid
	private List<PendingReturnDetailRequest> orderReturnDetail;
	
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

	public List<PendingReturnDetailRequest> getOrderReturnDetail() {
		return orderReturnDetail;
	}

	public void setOrderReturnDetaill(List<PendingReturnDetailRequest> orderReturnDetail) {
		this.orderReturnDetail = orderReturnDetail;
	}
}

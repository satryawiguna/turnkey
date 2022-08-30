package com.avomind.rms.dto.response;

import java.util.List;

import com.avomind.rms.dto.Messages;

public class OrderReturnWithMessageResponse extends Messages {
	
	private OrderReturnResponse orderReturn;
	
	public OrderReturnWithMessageResponse(OrderReturnResponse orderReturn, List<String> messages) {
		this.orderReturn = orderReturn;
		this.messages = messages;
	}

	public OrderReturnResponse getOrderReturn() {
		return orderReturn;
	}

	public void setOrderReturn(OrderReturnResponse orderReturn) {
		this.orderReturn = orderReturn;
	}
	
}

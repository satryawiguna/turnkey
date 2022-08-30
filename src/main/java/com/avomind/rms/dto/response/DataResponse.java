package com.avomind.rms.dto.response;

import java.util.ArrayList;
import java.util.List;

public class DataResponse<T> {

	private boolean status;
	
	protected List<String> messages = new ArrayList<>();
	
	private T payload;
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}
	
}

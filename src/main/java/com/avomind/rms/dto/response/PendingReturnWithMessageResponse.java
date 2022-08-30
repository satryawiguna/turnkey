package com.avomind.rms.dto.response;

import java.util.List;

import com.avomind.rms.dto.Messages;

public class PendingReturnWithMessageResponse extends Messages {
	
	private List<PendingReturnResponse> pendingReturns;
	
	public PendingReturnWithMessageResponse(List<PendingReturnResponse> pendingReturns, List<String> messages) {
		this.pendingReturns = pendingReturns;
		this.messages = messages;
	}

	public List<PendingReturnResponse> getPendingReturns() {
		return pendingReturns;
	}

	public void setPendingReturn(List<PendingReturnResponse> pendingReturns) {
		this.pendingReturns = pendingReturns;
	}
	
}

package com.avomind.rms.dto.response;

import java.util.List;

import com.avomind.rms.dto.Messages;

public class AwaitingApprovalReturnWithMessageResponse extends Messages {
	private List<AwaitingApprovalReturnResponse> awaitingApprovalReturns;
	
	public AwaitingApprovalReturnWithMessageResponse(List<AwaitingApprovalReturnResponse> awaitingApprovalReturns, List<String> messages) {
		this.awaitingApprovalReturns = awaitingApprovalReturns;
		this.messages = messages;
	}

	public List<AwaitingApprovalReturnResponse> getAwaitingApprovalReturns() {
		return awaitingApprovalReturns;
	}

	public void setAwaitingApprovalReturn(List<AwaitingApprovalReturnResponse> awaitingApprovalReturns) {
		this.awaitingApprovalReturns = awaitingApprovalReturns;
	}
}

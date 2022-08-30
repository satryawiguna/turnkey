package com.avomind.rms.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AwaitingApprovalReturnRequest {
	@NotEmpty(message = "Token is required")
	@NotNull(message = "Token could not be nullable")
	String[] tokens;

	public String[] getTokens() {
		return tokens;
	}

	public void setTokens(String[] tokens) {
		this.tokens = tokens;
	}
}

package com.avomind.rms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avomind.rms.dto.request.AwaitingApprovalReturnRequest;
import com.avomind.rms.dto.request.OrderReturnStatusRequest;
import com.avomind.rms.dto.request.PendingReturnRequest;
import com.avomind.rms.dto.response.AwaitingApprovalReturnWithMessageResponse;
import com.avomind.rms.dto.response.DataResponse;
import com.avomind.rms.dto.response.OrderReturnWithMessageResponse;
import com.avomind.rms.dto.response.PendingReturnWithMessageResponse;
import com.avomind.rms.dto.response.UpdateStatusOrderReturnResponse;
import com.avomind.rms.service.OrderReturnService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderReturnController {

	@Autowired
	private OrderReturnService orderReturnService;
	
	@PostMapping("/pending/returns")
	public ResponseEntity<DataResponse<PendingReturnWithMessageResponse>> pendingReturn(@Valid @RequestBody PendingReturnRequest pendingReturn, Errors errors) {
		
		DataResponse<PendingReturnWithMessageResponse> pendingReturnWithMessageResponse =  new DataResponse<>();
		
		if (errors.hasErrors()) {
			for (ObjectError error : errors.getAllErrors()) {
				pendingReturnWithMessageResponse.getMessages().add(error.getDefaultMessage());
			}
			
			pendingReturnWithMessageResponse.setStatus(false);
			pendingReturnWithMessageResponse.setPayload(null);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pendingReturnWithMessageResponse);
		}
		
		PendingReturnWithMessageResponse createPendingReturnResponse = orderReturnService.createPendingReturn(pendingReturn);
		
		List<String> messages = createPendingReturnResponse.getMessages();
		
		if (createPendingReturnResponse.getPendingReturns().size() < 1) {
			for (String error : messages) {
				pendingReturnWithMessageResponse.getMessages().add(error);
			}
			
			pendingReturnWithMessageResponse.setStatus(false);
			pendingReturnWithMessageResponse.setPayload(null);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pendingReturnWithMessageResponse);
		}
		
		pendingReturnWithMessageResponse.setStatus(true);
		pendingReturnWithMessageResponse.setPayload(createPendingReturnResponse);
		
		return ResponseEntity.ok(pendingReturnWithMessageResponse);
	}
	
	@PostMapping("/returns")
	public ResponseEntity<DataResponse<AwaitingApprovalReturnWithMessageResponse>> returns(@Valid @RequestBody AwaitingApprovalReturnRequest awaitingApprovalReturn, Errors errors) {
	
		DataResponse<AwaitingApprovalReturnWithMessageResponse> awaitingApprovalReturnWithMessageResponse =  new DataResponse<>();
		
		if (errors.hasErrors()) {
			for (ObjectError error : errors.getAllErrors()) {
				awaitingApprovalReturnWithMessageResponse.getMessages().add(error.getDefaultMessage());
			}
			
			awaitingApprovalReturnWithMessageResponse.setStatus(false);
			awaitingApprovalReturnWithMessageResponse.setPayload(null);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(awaitingApprovalReturnWithMessageResponse);
		}
		
		AwaitingApprovalReturnWithMessageResponse createAwaitingApprovalReturnResponse = orderReturnService.createAwaitingApprovalReturn(awaitingApprovalReturn);
		
		List<String> messages = createAwaitingApprovalReturnResponse.getMessages();
		
		if (createAwaitingApprovalReturnResponse.getAwaitingApprovalReturns().size() < 1) {
			for (String error : messages) {
				awaitingApprovalReturnWithMessageResponse.getMessages().add(error);
			}
			
			awaitingApprovalReturnWithMessageResponse.setStatus(false);
			awaitingApprovalReturnWithMessageResponse.setPayload(null);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(awaitingApprovalReturnWithMessageResponse);
		}
		
		awaitingApprovalReturnWithMessageResponse.setStatus(true);
		awaitingApprovalReturnWithMessageResponse.setPayload(createAwaitingApprovalReturnResponse);
		
		return ResponseEntity.ok(awaitingApprovalReturnWithMessageResponse);
	}
	
	@GetMapping("/returns/{id}")	
	public ResponseEntity<DataResponse<OrderReturnWithMessageResponse>> returns(@PathVariable("id") Long id) {
		
		DataResponse<OrderReturnWithMessageResponse> orderReturnWithMessageResponse =  new DataResponse<>();
		
		OrderReturnWithMessageResponse getOrderReturnResponses = orderReturnService.findOrderReturnsById(id);
		
		List<String> messages = getOrderReturnResponses.getMessages();
		
		if (getOrderReturnResponses.getMessages().size() > 0) {
			for (String error : messages) {
				getOrderReturnResponses.getMessages().add(error);
			}
			
			orderReturnWithMessageResponse.setStatus(false);
			orderReturnWithMessageResponse.setPayload(null);
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderReturnWithMessageResponse);
		}
		
		orderReturnWithMessageResponse.setStatus(true);
		orderReturnWithMessageResponse.setPayload(getOrderReturnResponses);
		
		return ResponseEntity.ok(orderReturnWithMessageResponse);
	}
	
	@PutMapping("/returns/{id}/items/{itemId}/qc/status")
	public ResponseEntity<DataResponse<UpdateStatusOrderReturnResponse>> returns(@PathVariable("id") Long id, @PathVariable("itemId") String itemId, @RequestBody OrderReturnStatusRequest orderReturnStatus) {
		
		DataResponse<UpdateStatusOrderReturnResponse> updateStatusOrderRResponse =  new DataResponse<>();
		
		UpdateStatusOrderReturnResponse updateStatusOrderReturn = orderReturnService.updateStatusOrderReturn(id, itemId, orderReturnStatus);
		
		updateStatusOrderRResponse.setStatus(true);
		updateStatusOrderRResponse.setPayload(updateStatusOrderReturn);
		
		return ResponseEntity.ok(updateStatusOrderRResponse);
	}
}

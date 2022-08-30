package com.avomind.rms.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avomind.rms.dto.OrderCSV;
import com.avomind.rms.dto.OrderCSVWithMessage;
import com.avomind.rms.dto.request.AwaitingApprovalReturnRequest;
import com.avomind.rms.dto.request.OrderReturnStatusRequest;
import com.avomind.rms.dto.request.PendingReturnDetailRequest;
import com.avomind.rms.dto.request.PendingReturnRequest;
import com.avomind.rms.dto.response.AwaitingApprovalReturnWithMessageResponse;
import com.avomind.rms.dto.response.OrderReturnDetailResponse;
import com.avomind.rms.dto.response.OrderReturnResponse;
import com.avomind.rms.dto.response.OrderReturnWithMessageResponse;
import com.avomind.rms.dto.response.AwaitingApprovalReturnResponse;
import com.avomind.rms.dto.response.PendingReturnResponse;
import com.avomind.rms.dto.response.PendingReturnWithMessageResponse;
import com.avomind.rms.dto.response.UpdateStatusOrderReturnResponse;
import com.avomind.rms.helper.CSVHelper;
import com.avomind.rms.model.OrderReturn;
import com.avomind.rms.repository.OrderReturnRepository;

@Service
@Transactional
public class OrderReturnService {

	@Autowired
	private OrderReturnRepository orderReturnRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public PendingReturnWithMessageResponse createPendingReturn(PendingReturnRequest pendingReturn)
	{	
		// Read order CSV
		OrderCSVWithMessage readOrderCSV = CSVHelper.readOrderCSV(pendingReturn);
		
		List<OrderCSV> orderCSVs = readOrderCSV.getOrderCSVs();
		List<String> messages = readOrderCSV.getMessages();
		List<OrderReturn> orderReturns = new ArrayList<OrderReturn>();
		List<PendingReturnResponse> pendingReturnResponses = new ArrayList<PendingReturnResponse>();
		
		if (orderCSVs.size() > 0) {
			
			for (OrderCSV orderCSV : orderCSVs) {
				String token = Base64.getEncoder().encodeToString((orderCSV.getOrderId() + orderCSV.getEmailAddress()).getBytes());
				
				List<OrderReturn> orderReturnFindByTokenAndSKU = orderReturnRepository.findByTokenAndSKU(token, orderCSV.getSku());
				
				if (orderReturnFindByTokenAndSKU.size() < 1) {
					OrderReturn orderReturn = new OrderReturn();
					
					orderReturn.setToken(token);
					orderReturn.setSku(orderCSV.getSku());
					
					for (PendingReturnDetailRequest orderReturnDetailRequest : pendingReturn.getOrderReturnDetail()) {
						if (orderReturnDetailRequest.getSku().equals(orderCSV.getSku())) {
							int returnQuantity = orderReturnDetailRequest.getQuantity();
							Double returnPrice = (orderCSV.getPrice() / orderCSV.getQuantity()) * returnQuantity;
							
							orderReturn.setReturnQuantity(returnQuantity);
							orderReturn.setReturnPrice(returnPrice);
						}
					}
					
					orderReturn.setStatus("PENDING");
					
					orderReturns.add(orderReturn);
					
				} else {
					messages.add(String.format("SKU %s is already exists", orderCSV.getSku()));
				}
			}
			
			List<OrderReturn> orderReturnResponses = orderReturnRepository.saveAll(orderReturns);
			
			for (OrderReturn orderReturn : orderReturnResponses) {
				PendingReturnResponse pendingReturnResponse = modelMapper.map(orderReturn, PendingReturnResponse.class);
				pendingReturnResponses.add(pendingReturnResponse);
			}
		}

		return new PendingReturnWithMessageResponse(pendingReturnResponses, messages);
	}

	public AwaitingApprovalReturnWithMessageResponse createAwaitingApprovalReturn(AwaitingApprovalReturnRequest awaitingApprovalsReturn) {
		
		String[] tokens = awaitingApprovalsReturn.getTokens();
		
		List<OrderReturn> orderReturns = orderReturnRepository.findByTokensAndStatus(tokens, "PENDING");
		List<AwaitingApprovalReturnResponse> awaitingApprovalReturnResponses = new ArrayList<AwaitingApprovalReturnResponse>();
		
		ArrayList<String> messages = new ArrayList<String>();
		
		if (orderReturns.size() > 0) {
			for(int i = 0; i < orderReturns.size(); i++) {
				orderReturns.get(i).setStatus("AWAITING_APPROVAL");
			}
			
			List<OrderReturn> updateStatusResponse = orderReturnRepository.saveAll(orderReturns);
			
			for (OrderReturn orderReturn : updateStatusResponse) {
				AwaitingApprovalReturnResponse awaitingApprovalReturnResponse = modelMapper.map(orderReturn, AwaitingApprovalReturnResponse.class);
				awaitingApprovalReturnResponses.add(awaitingApprovalReturnResponse);
			}
			
		} else {
			messages.add("No order return founded");
		}
		
		return new AwaitingApprovalReturnWithMessageResponse(awaitingApprovalReturnResponses, messages);
	}
	
	public OrderReturnWithMessageResponse findOrderReturnsById(Long id) {
		List<OrderReturn> orderReturns = orderReturnRepository.findGroupById(id);
		List<OrderReturnDetailResponse> orderReturnDetailResponses = new ArrayList<OrderReturnDetailResponse>();
		
		ArrayList<String> messages = new ArrayList<String>();
		
		OrderReturnResponse orderReturnResponse = new OrderReturnResponse();
				
		if (orderReturns.size() > 0) {
			for (OrderReturn orderReturn : orderReturns) {
				OrderReturnDetailResponse orderReturnDetailResponse = modelMapper.map(orderReturn, OrderReturnDetailResponse.class);
				orderReturnDetailResponses.add(orderReturnDetailResponse);
			}
			
			orderReturnResponse.setId(orderReturns.get(0).getId());
			orderReturnResponse.setToken(orderReturns.get(0).getToken());
			orderReturnResponse.setOrderReturnDetailResponse(orderReturnDetailResponses);
		} else {
			messages.add("No order return founded");
		}
		
		return new OrderReturnWithMessageResponse(orderReturnResponse, messages);
	}
	
	public UpdateStatusOrderReturnResponse updateStatusOrderReturn(Long id, String itemId, OrderReturnStatusRequest orderReturnStatusRequest)
	{
		
		Optional<OrderReturn> orderReturn = orderReturnRepository.findByIdAndItemId(id, itemId);
		
		String status = orderReturnStatusRequest.getStatus();
		
		if (orderReturn != null) {
			orderReturn.get().setStatus(status.toString());
		}
		
		OrderReturn updateOrderReturn = orderReturnRepository.save(orderReturn.get());
		UpdateStatusOrderReturnResponse updateStatusOrderReturn = modelMapper.map(updateOrderReturn, UpdateStatusOrderReturnResponse.class);
		
		return updateStatusOrderReturn;
	}
}

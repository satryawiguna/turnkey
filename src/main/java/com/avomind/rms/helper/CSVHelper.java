package com.avomind.rms.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.avomind.rms.dto.OrderCSV;
import com.avomind.rms.dto.OrderCSVWithMessage;
import com.avomind.rms.dto.request.PendingReturnDetailRequest;
import com.avomind.rms.dto.request.PendingReturnRequest;

public class CSVHelper {

	static String[] HEADERs = { "orderId", "emailAddress", "sku", "quantity", "price", "itemName" };

	public static OrderCSVWithMessage readOrderCSV(PendingReturnRequest orderReturn) {
		
		Resource resource = new ClassPathResource("orders.csv");
		
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
	    		CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<OrderCSV> orders = new ArrayList<OrderCSV>();	      
	    	ArrayList<String> messages = new ArrayList<String>();
	    	
	    	Iterable<CSVRecord> csvRecords = csvParser.getRecords();	
		      
		    for (CSVRecord csvRecord : csvRecords) {
		    	orders.add(new OrderCSV(
		    			csvRecord.get(0), 
		  	        	csvRecord.get(1),
		  	        	csvRecord.get(2),
		  	        	Integer.parseInt(csvRecord.get(3)), 
		  	        	Double.parseDouble(csvRecord.get(4)), 
		  	        	csvRecord.get(5)));
	    	}
		    
		    // Filter by order id
		    List<OrderCSV> filterByOrderIdAndEmailAddress = orders.stream()
	    		  .filter(order -> order.getOrderId().equals(orderReturn.getOrderId()))
	    		  .filter(order -> order.getEmailAddress().equals(orderReturn.getEmailAddress()))
	    		  .collect(Collectors.toList());
	      
		    orders = filterByOrderIdAndEmailAddress;
		    
		    if (orders.size() < 1) {
		    	messages.add("No order id and email address found");
		    }
		    	
		    
		    // Filter by sku && quantity
		    if (orderReturn.getOrderReturnDetail().size() > 0) {
		    	for (PendingReturnDetailRequest orderDetailReturn : orderReturn.getOrderReturnDetail()) {
		    		List<OrderCSV> filterBySkuAndQuantity = orders.stream()
			    			.filter(order -> order.getSku().equals(orderDetailReturn.getSku()))
			    			.filter(order -> order.getQuantity() >= orderDetailReturn.getQuantity())
			    			.collect(Collectors.toList());
		    		
		    		orders = filterBySkuAndQuantity;
		    		
			    	if (orders.size() < 1) {
			    		messages.add(String.format("SKU %s not found / Outnumber of quantity return", orderDetailReturn.getSku()));
			    	}
				    	
		    	}
		    }
		    
		    return new OrderCSVWithMessage(orders, messages);
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
	    }
	}
}

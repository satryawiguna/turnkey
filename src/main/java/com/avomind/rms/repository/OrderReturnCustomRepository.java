package com.avomind.rms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.avomind.rms.model.OrderReturn;

@Repository
public interface OrderReturnCustomRepository {

	List<OrderReturn> findByTokenAndSKU(String token, String sku);
	
	List<OrderReturn> findByTokensAndStatus(String[] tokens, String status);
	
	List<OrderReturn> findGroupById(Long id);
	
	Optional<OrderReturn> findByIdAndItemId(Long id, String itemId);
	
}

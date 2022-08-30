package com.avomind.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avomind.rms.model.OrderReturn;

@Repository
public interface OrderReturnRepository extends JpaRepository<OrderReturn, Long>, OrderReturnCustomRepository {
	
}

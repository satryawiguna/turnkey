package com.avomind.rms.repository;

import com.avomind.rms.model.OrderReturn;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderReturnCustomRepositoryImpl implements OrderReturnCustomRepository {
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<OrderReturn> findByTokenAndSKU(String token, String sku) {
		String sql = "SELECT o FROM order_returns o WHERE token=:token AND sku=:sku";
		
		final TypedQuery<OrderReturn> query = entityManager.createQuery(sql, OrderReturn.class);
		
        query.setParameter("token", token);
        query.setParameter("sku", sku);
        
        return query.getResultList();
	}
	
	@Override
	public List<OrderReturn> findByTokensAndStatus(String[] tokens, String status) {
		String sql = "SELECT o FROM order_returns o WHERE o.token IN (:tokens) AND o.status=:status";
		
		final TypedQuery<OrderReturn> query = entityManager.createQuery(sql, OrderReturn.class);
		
        query.setParameter("tokens", String.join(", ", tokens));
        query.setParameter("status", status);
        
        return query.getResultList();
	}
	
	@Override
	public List<OrderReturn> findGroupById(Long id) {
		String sql = "SELECT o FROM order_returns o WHERE o.token IN (SELECT c.token FROM order_returns c WHERE c.id=:id)";
		
		final TypedQuery<OrderReturn> query = entityManager.createQuery(sql, OrderReturn.class);
		
        query.setParameter("id", id);
        
        return query.getResultList();
	}
	
	@Override
	public Optional<OrderReturn> findByIdAndItemId(Long id, String itemId) {
		String sql = "SELECT o FROM order_returns o WHERE o.id=:id AND o.sku=:itemId";
		
		final TypedQuery<OrderReturn> query = entityManager.createQuery(sql, OrderReturn.class);
		
        query.setParameter("id", id);
        query.setParameter("itemId", itemId);
        
        Optional<OrderReturn> orderReturn = Optional.ofNullable(query.getSingleResult());
        
        return orderReturn;
	}
}

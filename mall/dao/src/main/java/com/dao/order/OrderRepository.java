package com.dao.order;

import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.order.Order;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface OrderRepository extends BaseRepository<Order> {
	
}
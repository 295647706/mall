package com.dao.order;

import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.order.OrderProduct;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface OrderProductRepository extends BaseRepository<OrderProduct> {
	
}
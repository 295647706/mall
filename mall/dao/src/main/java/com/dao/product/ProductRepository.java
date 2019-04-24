package com.dao.product;

import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.product.Product;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface ProductRepository extends BaseRepository<Product> {
	
}
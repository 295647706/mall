package com.dao.user;

import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.user.ShoppingCart;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface ShoppingCartRepository extends BaseRepository<ShoppingCart> {
	
}
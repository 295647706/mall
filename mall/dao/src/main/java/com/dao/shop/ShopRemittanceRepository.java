package com.dao.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.shop.ShopRemittance;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface ShopRemittanceRepository extends BaseRepository<ShopRemittance> {
	
	/**
	 * 根据店铺ID查询分页
	 */
	@Query(value = "SELECT t FROM ShopRemittance t WHERE t.shopId=?1", countQuery = "SELECT count(*) FROM ShopRemittance t WHERE t.shopId=?1")
	public Page<ShopRemittance> getPagesByShopId(long shopId, Pageable pageable);
	
}
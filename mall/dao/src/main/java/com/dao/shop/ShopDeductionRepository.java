package com.dao.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.shop.ShopDeduction;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface ShopDeductionRepository extends BaseRepository<ShopDeduction> {
	
	/**
	 * 根据店铺ID查询分页
	 */
	@Query(value = "SELECT t FROM ShopDeduction t WHERE t.shopId=?1", countQuery = "SELECT count(*) FROM ShopDeduction t WHERE t.shopId=?1")
	public Page<ShopDeduction> getPagesByShopId(long shopId, Pageable pageable);
	
}
package com.dao.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.shop.ShopWithdrawCash;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface ShopWithdrawCashRepository extends BaseRepository<ShopWithdrawCash> {
	
	@Query("select sum(amount) from ShopWithdrawCash where shopId=?1 AND state=?2")
	public Integer sumAmountByShopIdAndState(long shopId, int state);
	
	/**
	 * 根据店铺ID查询分页
	 */
	@Query(value = "SELECT t FROM ShopWithdrawCash t WHERE t.shopId=?1", countQuery = "SELECT count(*) FROM ShopWithdrawCash t WHERE t.shopId=?1")
	public Page<ShopWithdrawCash> getPagesByShopId(long shopId, Pageable pageable);
	
}
package com.dao.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.shop.ArrearsBill;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface ArrearsBillRepository extends BaseRepository<ArrearsBill> {
	
	/**
	 * 根据店铺ID查询分页
	 */
	@Query(value = "SELECT t FROM ArrearsBill t WHERE t.shopId=?1 AND t.state=?2", countQuery = "SELECT count(*) FROM ArrearsBill t WHERE t.shopId=?1 AND t.state=?2")
	public Page<ArrearsBill> getPagesByShopIdAndState(long shopId, int state, Pageable pageable);
	
}
package com.dao.shop;

import org.apache.ibatis.annotations.Select;

import com.dao.BaseMapper;
import com.model.shop.Shop;
import com.model.shop.ShopSub;

/**
 * 
 * @Author Ruan
 * 
 */

public interface ShopMapper extends BaseMapper<Shop, ShopSub> {
	
	/**
	 * 店铺信息
	 */
	@Select("SELECT t1.id,t1.name,t1.logo,t1.synopsis,t1.type,t1.category_id_first,t1.category_id_second,t1.category_id_third,"
			+ "t2.name AS categoryNameFirst,t3.name AS categoryNameSecond,t4.name AS categoryNameThird "
			+ "FROM tbl_shop AS t1 "
			+ "LEFT JOIN tbl_category AS t2 ON t1.category_id_first=t2.id "
			+ "LEFT JOIN tbl_category AS t3 ON t1.category_id_second=t3.id "
			+ "LEFT JOIN tbl_category AS t4 ON t1.category_id_third=t4.id "
			+ "WHERE t1.id=#{id}")
	public ShopSub getShopSubById(long id);
	
}
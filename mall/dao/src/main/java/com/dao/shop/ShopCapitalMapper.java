package com.dao.shop;

import org.apache.ibatis.annotations.Select;

import com.dao.BaseMapper;
import com.model.shop.ShopCapital;
import com.model.shop.ShopCapitalSub;

/**
 * 
 * @Author Ruan
 * 
 */

public interface ShopCapitalMapper extends BaseMapper<ShopCapital, ShopCapitalSub> {
	
	/**
	 * 商家账户资金
	 */
	@Select("SELECT t1.id,t1.product_payment AS productPayment,t1.arrears,t2.bond,t2.bond_activity AS bondActivity,t2.bond_marketing AS bondMarketing "
			+ "FROM tbl_shop_capital AS t1 LEFT JOIN tbl_shop_bond AS t2 ON t1.shop_id=t2.shop_id WHERE t1.shop_id=#{shopId}")
	public ShopCapitalSub getShopCapitalSubByShopId(long shopId);
	
}
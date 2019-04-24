package com.dao.user;

import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.dao.BaseMapper;
import com.model.user.User;
import com.model.user.UserSub;

/**
 * 
 * @Author Ruan
 * 
 * 注意：启动类中使用了@MapperScan自动扫描，所以这里不是手动添加@Mapper
 * 
 */

//@Mapper
public interface UserMapper extends BaseMapper<User, UserSub> {
	
	@Select("SELECT u.id,u.phone,p.usable AS profitUsable,p.total AS profitTotal FROM tbl_user AS u LEFT JOIN tbl_user_profit AS p ON u.id=p.user_id WHERE u.id=#{id}")
	public UserSub getProfitById(long id);
	
	@Select("SELECT u.id,u.phone,p.usable AS profitUsable,p.total AS profitTotal FROM tbl_user AS u LEFT JOIN tbl_user_profit AS p ON u.id=p.user_id WHERE u.id=#{id}")
	public Map<String, Object> getProfitMapById(long id);
	
	/**
	 * 商家用户信息
	 */
	@Select("SELECT u.id,u.nick_name AS nickName,u.logo,s.id AS shopId,s.type AS shopType,s.state AS shopState FROM tbl_user AS u LEFT JOIN tbl_shop AS s ON u.shop_id=s.id WHERE u.id=#{id}")
	public UserSub getUserAndShopMessById(long id);

	@Select("select id,nick_name as nickName, logo, gender, birthday, personalized_signature as personalizedSignature, province, city, area, address from tbl_user where id=#{id} and deleted=0")
    Map<String,Object> getUserDetail(Long id);

	@Select("select id,phone, nick_name as nickName, logo, type, experience from tbl_user where id=#{id} and deleted=0")
	Map<String,Object> getUserInfo(Long id);
}
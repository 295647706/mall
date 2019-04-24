package com.dao.user;

import com.dao.BaseMapper;
import com.model.user.UserCoupon;
import com.model.user.UserCouponSub;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Author Ruan
 * 
 */

public interface UserCouponMapper extends BaseMapper<UserCoupon, UserCouponSub> {
    List<Map<String,Object>> userCouponList(@Param("useType") String useType, @Param("userId") long userId, @Param("currTime") long currTime);

    List<Map<String,Object>> myCouponPage(@Param("selectType") String selectType, @Param("userId")long userId);
}
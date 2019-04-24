package com.dao.user;

import com.dao.BaseMapper;
import com.model.user.UserGold;
import com.model.user.UserGoldSub;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Author Ruan
 * 
 */

public interface UserGoldMapper extends BaseMapper<UserGold, UserGoldSub> {
    List<Map<String,Object>> userGoldList(@Param("useType") String useType, @Param("userId") long userId, @Param("currTime") long currTime);
	
}
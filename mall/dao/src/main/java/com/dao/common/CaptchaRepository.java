package com.dao.common;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.common.Captcha;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface CaptchaRepository extends BaseRepository<Captcha> {
	
	public Captcha getByUserIdAndPhoneAndType(long userId, String phone, int type);
	
	@Modifying
	@Query("update Captcha set code=?1,endTime=?2,updateTime=?3 where id=?4")
	public int updateById(String code, long endTime, long updateTime, long id);
	
}
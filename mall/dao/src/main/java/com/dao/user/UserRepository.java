package com.dao.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.user.User;
import com.model.user.UserSub;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface UserRepository extends BaseRepository<User> {
	
	@Query(UserSub.SELECT_BEAN_SUB + UserSub.COLUMN_IRNL + UserSub.TABLE + UserSub.BY_ID)
	public UserSub getUserSubById(long id);
	
	@Query(UserSub.SELECT_BEAN_SUB + UserSub.COLUMN_IRNLPS + UserSub.TABLE + UserSub.BY_ACCOUNT)
	public UserSub getUserSubByAccount(String account);
	
}
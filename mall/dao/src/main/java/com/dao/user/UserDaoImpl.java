package com.dao.user;

import org.springframework.stereotype.Repository;

import com.dao.BaseDaoImpl;
import com.model.user.User;
import com.model.user.UserSub;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, UserSub> implements UserDao {

}
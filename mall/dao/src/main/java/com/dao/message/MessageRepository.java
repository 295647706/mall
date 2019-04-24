package com.dao.message;

import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.message.Message;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface MessageRepository extends BaseRepository<Message> {
	
}
package com.dao.message;

import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.message.ChatMessage;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface ChatMessageRepository extends BaseRepository<ChatMessage> {
	
}
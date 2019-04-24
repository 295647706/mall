package com.dao.user;

import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.user.Feedback;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface FeedbackRepository extends BaseRepository<Feedback> {
	
}
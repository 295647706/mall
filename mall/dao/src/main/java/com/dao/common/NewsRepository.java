package com.dao.common;

import org.springframework.stereotype.Repository;

import com.dao.BaseRepository;
import com.model.common.News;

/**
 * 
 * @Author Ruan
 * 
 */

@Repository
public interface NewsRepository extends BaseRepository<News> {
	
}
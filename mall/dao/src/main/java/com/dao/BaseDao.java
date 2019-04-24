package com.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 
 * @Author Ruan
 * 
 */

public interface BaseDao<T, TSub> {
	
	/**
	 * 查询列表
	 * @return List<T>
	 */
	public List<T> getList();
	
	/**
	 * 执行SQL语句分页查询
	 * @param String sql：SQL语句
	 * @param Integer page：当前页(页码)
	 * @param Integer size：每页数据条数
	 * @param Pageable pageable：排序
	 * @return Page<TSub>
	 */
	public Page<TSub> getPagesBySql(String sql, Integer page, Integer size, Pageable pageable);
	
}
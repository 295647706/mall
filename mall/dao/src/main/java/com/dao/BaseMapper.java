package com.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.common.MyBatisConstants;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 
 * @Author Ruan
 * 
 * 注意：启动类的@MapperScan不能扫描通用Mapper接口包，否则报java.lang.ClassCastException: sun.reflect.generics.reflectiveObjects.TypeVariableImpl cannot be cast to java.lang.Class异常
 * 
 */

public interface BaseMapper<T, TSub> extends tk.mybatis.mapper.common.BaseMapper<T>, MySqlMapper<T>, IdsMapper<T>, ConditionMapper<T>, ExampleMapper<T> {
	
	@SelectProvider(type = BaseProvider.class, method = "selectById")
	public T selectById(@Param(MyBatisConstants.ID)long id);
	public T selectById(@Param(MyBatisConstants.ID)long id, @Param(MyBatisConstants.SELECT)String columns);
	
	@SelectProvider(type = BaseProvider.class, method = "selectById")
	public TSub selectSubById(@Param(MyBatisConstants.ID)long id);
	public TSub selectSubById(@Param(MyBatisConstants.ID)long id, @Param(MyBatisConstants.SELECT)String columns);
	
	@SelectProvider(type = BaseProvider.class, method = "select")
	public T selectTop(Map<String, Object> params);
	
	@SelectProvider(type = BaseProvider.class, method = "select")
	public TSub selectTopSub(Map<String, Object> params);
	
	@SelectProvider(type = BaseProvider.class, method = "selectList")
	public List<T> selectList(Map<String, Object> params);
	
	@SelectProvider(type = BaseProvider.class, method = "selectList")
	public List<TSub> selectListSub(Map<String, Object> params);
	
	@SelectProvider(type = BaseProvider.class, method = "selectBySql")
	public T selectBySql(@Param(MyBatisConstants.SQL)String sql);
	@SelectProvider(type = BaseProvider.class, method = "selectBySql")
	public TSub selectSubBySql(@Param(MyBatisConstants.SQL)String sql);
	@SelectProvider(type = BaseProvider.class, method = "selectBySql")
	public List<T> selectListBySql(@Param(MyBatisConstants.SQL)String sql);
	@SelectProvider(type = BaseProvider.class, method = "selectBySql")
	public List<TSub> selectListSubBySql(@Param(MyBatisConstants.SQL)String sql);
	
}
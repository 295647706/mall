package com.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import javax.persistence.Table;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import com.common.MyBatisConstants;

/**
 * 
 * @Author Ruan
 * 
 */

public class BaseProvider {
	
	/**
	 * 获取泛型的实体对象对应的表名
	 */
	private String getTableName(ProviderContext context){
		Class<?> clazz = context.getMapperType();																//获取当前具体mapper的class，如UserMapper
		Type[] types = clazz.getGenericInterfaces();															//获取通用接口(types[0]=BaseMapper)
//		Type type = ((ParameterizedType) types[0]).getActualTypeArguments()[0];
		Class<?> c = (Class<?>) ((ParameterizedType) types[0]).getActualTypeArguments()[0];						//泛型对应的具体对象
		Table table = c.getAnnotation(Table.class);
		return table.name();
	}
	
	/**
	 * 配置SQL语句
	 */
	private String getSelectSql(ProviderContext context, Map<String, Object> params) {
		SQL sql = new SQL();
		
		if(null!=params && 0<params.size()){
			if(params.containsKey(MyBatisConstants.SELECT)){
				Object obj = params.get(MyBatisConstants.SELECT);
				if(obj instanceof String[]){
					sql.SELECT((String[]) obj);
				}else if(obj instanceof String){
					sql.SELECT((String) obj);
				}else{
					sql.SELECT("*");
				}
			}else{
				sql.SELECT("*");
			}
			
			if(params.containsKey(MyBatisConstants.TABLE)){
				sql.FROM((String) params.get(MyBatisConstants.TABLE));
			}else{
				sql.FROM(getTableName(context));
			}
			
			for (String param : params.keySet()) {
				if(!param.equals(MyBatisConstants.SELECT) && !param.equals(MyBatisConstants.TABLE) && !param.equals(MyBatisConstants.ORDER_BY)
						&& !param.equals(MyBatisConstants.GROUP_BY) && !param.equals(MyBatisConstants.LIMIT)){
					sql.WHERE(param+"=#{"+param+"}");
				}
			}
			
			if(params.containsKey(MyBatisConstants.ORDER_BY)){
				Object obj = params.get(MyBatisConstants.ORDER_BY);
				if(obj instanceof String[]){
					sql.ORDER_BY((String[]) obj);
				}else if(obj instanceof String){
					sql.ORDER_BY((String) obj);
				}
			}
			
			if(params.containsKey(MyBatisConstants.GROUP_BY)){
				Object obj = params.get(MyBatisConstants.GROUP_BY);
				if(obj instanceof String[]){
					sql.GROUP_BY((String[]) obj);
				}else if(obj instanceof String){
					sql.GROUP_BY((String) obj);
				}
			}
		}else{
			sql.SELECT("*");
			sql.FROM(getTableName(context));
		}
		
		return sql.toString();
    }
	
	public String select(ProviderContext context, Map<String, Object> params) {
		return getSelectSql(context, params) + " limit 1";
    }
	
	public String selectList(ProviderContext context, Map<String, Object> params) {
		String limit = "";
		if(null!=params && 0<params.size() && params.containsKey(MyBatisConstants.LIMIT)){
			limit = " limit #{"+MyBatisConstants.LIMIT+"}";
		}
		return getSelectSql(context, params) + limit;
    }
	
//	public String selectById(ProviderContext context, @Param("id")long id){
	public String selectById(ProviderContext context, Map<String, Object> params){
		SQL sql = new SQL();
		if(null!=params && params.containsKey(MyBatisConstants.SELECT)){
			sql.SELECT((String) params.get(MyBatisConstants.SELECT));
		}else{
			sql.SELECT("*");
		}
		sql.FROM(getTableName(context));
		sql.WHERE("id="+params.get(MyBatisConstants.ID));
		return sql.toString();
	}
	
	public String selectBySql(Map<String, Object> params) {
		return (String) params.get(MyBatisConstants.SQL);
	}
	
}
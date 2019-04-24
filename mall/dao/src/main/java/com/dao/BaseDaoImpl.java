package com.dao;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.common.Constants;

/**
 * 
 * @Author Ruan
 * 
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseDaoImpl<T, TSub> implements BaseDao<T, TSub> {

	private Class<T> entityClass;
	private Class<TSub> entityClassSub;

	public BaseDaoImpl() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
		entityClassSub = (Class) params[1];
	}

	@PersistenceContext
	private EntityManager entityManager;
	
	
	
	/**
	 * 查询列表
	 * @return List<T>
	 */
	public List<T> getList() {
		return entityManager.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
	}
	
	/**
	 * 执行SQL语句分页查询
	 * @param String sql：SQL语句
	 * @param Integer page：当前页(页码)
	 * @param Integer size：每页数据条数
	 * @param @param Pageable pageable：排序
	 * @return Page<TSub>
	 */
	@Override
	public Page<TSub> getPagesBySql(String sql, Integer page, Integer size, Pageable pageable) {
		page = null==page ? Constants.DefaultPage.PAGE : page;
		size = null==size ? Constants.DefaultPage.SIZE : size;

//		Pageable pageable = new PageRequest(page, size);
////		两种多属性排序方法：按property1(key)降序asc(value)，再property2(key)降序desc(value)
////		Sort sort = new Sort(Sort.Direction.ASE, "property1").and(new Sort(Sort.Direction.DESC, "property2"));
//		if (null != sorts && 0 < sorts.size()) {
//			sql += " ORDER BY ";
//			List<Order> orders = new ArrayList<Order>();
//			for (String key : sorts.keySet()) {
//				orders.add(new Order(Direction.fromString(sorts.get(key)), key));
//				sql += key + " " + sorts.get(key) + ",";
//			}
//			sql = sql.substring(0, sql.lastIndexOf(","));
//			pageable = new PageRequest(page, size, new Sort(orders));
//		}

		Session session = entityManager.unwrap(org.hibernate.Session.class);
		
//		NativeQuery query = session.createSQLQuery(sql);
//		//query.setResultTransformer(Transformers.aliasToBean(entityClass));	//转化为指定的实体，但是实体属性类型必须和数据库表的字段类型一致，否则会报类型转换错误
//		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);			//返回一个map，KEY和数据库表的字段名称一致(大小写一致)
		
		NativeQuery query = session.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		long total = query.list().size();
		query.setFirstResult(null==page||0>=page?0:(page*size));				//设置从哪条数据开始查询(分页的索引是从0开始)
		query.setMaxResults(size);												//每页查询数量
		List<TSub> list = convert(entityClassSub, query.list());
		return new PageImpl<TSub>(list, pageable, total);
	}
	
	/**
	 * 转换成实体类 Introspector是一个专门处理bean的工具类，用来获取Bean体系里的propertiesDescriptor,methodDescriptor
	 */
	private List<TSub> convert(Class clazz, List<Map<String, Object>> list) {
		if (null == list || 0 >= list.size()) {
			return new ArrayList<TSub>();
		}
		List<TSub> result = new ArrayList<TSub>();
		try {
			PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for (Map<String, Object> map : list) {
				TSub obj = (TSub) clazz.newInstance();
				for (String key : map.keySet()) {
					String attrName = key;
					for (PropertyDescriptor prop : props) {
						attrName = removeUnderLine(attrName);
						if (!attrName.equalsIgnoreCase(prop.getName())) {
							continue;
						}
						Method method = prop.getWriteMethod();
						Object value = map.get(key);
						if (value != null) {
							value = ConvertUtils.convert(value, prop.getPropertyType());
						}
						method.invoke(obj, value);
					}
				}
				result.add(obj);
			}
		} catch (Exception e) {
			throw new RuntimeException("convert clazz error!");
		}
		return result;
	}
	/**
	 * 去掉数据库字段的下划线(Java属性是驼峰格式)
	 * @param String attrName：数据库字段名
	 * @return String
	 */
	private String removeUnderLine(String attrName) {
		if (attrName.contains("_")) {
			String[] names = attrName.split("_");
			String firstPart = names[0];
			String otherPart = "";
			for (int i = 1; i < names.length; i++) {
				String word = names[i].replaceFirst(names[i].substring(0, 1), names[i].substring(0, 1).toUpperCase());
				otherPart += word;
			}
			attrName = firstPart + otherPart;
		}
		return attrName;
	}

}
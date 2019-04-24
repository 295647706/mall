package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 
 * @Author Ruan
 * 
 * 利用SpEL表达式，把实体类写成动态，目的是把通用的查询放在公用的方法上
 * 
 * JPA自定义的简单查询是根据方法名来自动生成SQL，主要的语法是findXXBy,readXXBy,queryXXBy,countXXBy,getXXBy,deleteXXBy后面跟属性名称，再组合关键字And、 Or、LIKE、 IgnoreCase、 OrderBy等
 * 
 * 具体的关键字，使用方法和生产成SQL如下表所示

 * Keyword					Sample										JPQL snippet
 * And						findByLastnameAndFirstname					… where x.lastname = ?1 and x.firstname = ?2
 * Or						findByLastnameOrFirstname					… where x.lastname = ?1 or x.firstname = ?2
 * Is,Equals				findByFirstnameIs,findByFirstnameEquals		… where x.firstname = ?1
 * Between					findByStartDateBetween						… where x.startDate between ?1 and ?2
 * LessThan					findByAgeLessThan							… where x.age < ?1
 * LessThanEqual			findByAgeLessThanEqual						… where x.age =< ?1
 * GreaterThan				findByAgeGreaterThan						… where x.age > ?1
 * GreaterThanEqual			findByAgeGreaterThanEqual					… where x.age >= ?1
 * After					findByStartDateAfter						… where x.startDate > ?1
 * Before					findByStartDateBefore						… where x.startDate < ?1
 * IsNull					findByAgeIsNull								… where x.age is null
 * IsNotNull,NotNull		findByAge(Is)NotNull						… where x.age not null
 * Like						findByFirstnameLike							… where x.firstname like ?1
 * NotLike					findByFirstnameNotLike						… where x.firstname not like ?1
 * StartingWith				findByFirstnameStartingWith					… where x.firstname like ?1 (parameter bound with appended %)
 * EndingWith				findByFirstnameEndingWith					… where x.firstname like ?1 (parameter bound with prepended %)
 * Containing				findByFirstnameContaining					… where x.firstname like ?1 (parameter bound wrapped in %)
 * OrderBy					findByAgeOrderByLastnameDesc				… where x.age = ?1 order by x.lastname desc
 * Not						findByLastnameNot							… where x.lastname <> ?1
 * In						findByAgeIn(Collection ages)				… where x.age in ?1
 * NotIn					findByAgeNotIn(Collection age)				… where x.age not in ?1
 * TRUE						findByActiveTrue()							… where x.active = true
 * FALSE					findByActiveFalse()							… where x.active = false
 * IgnoreCase				findByFirstnameIgnoreCase					… where UPPER(x.firstame) = UPPER(?1)
 * 
 * 
 * 
 * 分页Page对象：
 * 		content：查询对象集合(List Map等)
 * 		first：当前是否第一页
 * 		last：当前是否最后一页
 * 		number：当前页码(从0开始)
 * 		numberOfElements：当前页共有多少条数据
 * 		size：每页数量
 *		totalPages：总页数
 *		totalElements：总数量
 *		sort：
 *			direction：排序(升序ASE|倒序DESC)
 *			property：排序字段(属性)
 *			ignoreCase：忽略大小写
 *			nullHandling：
 *			ascending：
 *			descending：
 */

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
	
//	@Query("from #{#entityName} t where t.id=?1")
	@Query("SELECT t from #{#entityName} t WHERE t.id=?1")
	public T getById(Long id);

}
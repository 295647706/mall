package com.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * 
 * @Author Ruan
 * 
 * 自定义MyBatis分页插件返回结果(与org.springframework.data.domain.Page的属性一致)
 * 注意：MyBatis分页插件页码从1开始，而且org.springframework.data.domain.Page的页码从0开始
 * 
 */

public class PageResult<T> implements Serializable {

	private static final long serialVersionUID = 151301323853683585L;
	
	private boolean first;//当前是否第一页
	private boolean last;//当前是否最后一页
	private Long totalElements;//总记录数
	private Integer number;//当前页码(MyBatis从1开始)
	private Integer numberOfElements;//当前页共有多少条数据
	private Integer size;//每页记录数
	private Integer totalPages;//总页数
	private List<T> content;//结果集

	public PageResult(List<T> content) {
		init(content);
	}

	private void init(List<T> content) {
		if (content instanceof Page) {
			Page<T> page = (Page<T>) content;
			this.totalElements = page.getTotal();
			this.number = page.getPageNum();
			this.numberOfElements = page.getResult().size();
			this.size = page.getPageSize();
			this.totalPages = page.getPages();
			this.content = page.getResult();
			
			this.first = false;
			if(1>=this.number){
				this.first = true;
			}
			
			this.last = false;
			if(this.totalPages<=this.number){
				this.last = true;
			}
		} else if (content instanceof Collection) {
			this.totalElements = (long) content.size();
			this.number = 1;
			this.numberOfElements = content.size();
			this.size = content.size();
			this.totalPages = this.size > 0 ? 1 : 0;
			this.content = content;
			
			this.first = false;
			if(1>=this.number){
				this.first = true;
			}
			
			this.last = false;
			if(this.totalPages<=this.number){
				this.last = true;
			}
		}
		
		//为了与org.springframework.data.domain.Page的页码一致(从0开始)
		this.number = this.number - 1;
	}

	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public Long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getNumberOfElements() {
		return numberOfElements;
	}
	public void setNumberOfElements(Integer numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public List<T> getContent() {
		return content;
	}
	public void setContent(List<T> content) {
		this.content = content;
	}

}
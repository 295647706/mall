package com.api.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.util.SecureCheck;

/**
 * 
 * @Author Ruan
 * 
 * 全局拦截器，单例，注意线程安全
 * 
 */

public class GlobalInterceptor implements HandlerInterceptor {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalInterceptor.class);
	
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("stopWatch-startTime");
	
	/* 
	 * 完成页面渲染后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//计算页面处理消耗时间
		long consumeTime = System.currentTimeMillis() - startTimeThreadLocal.get();
		
		//超过1秒的，记录日志以便调优
		if(consumeTime > 1000){
			log.warn(String.format("%s consume %d milli seconds", request.getRequestURI(), consumeTime));
		}
	}

	/* 
	 * 完成具体方法后调用
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

	/* 
	 * 调用具体方法之前调用(抛出的异常，被GlobalExceptionHandler捕捉)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = getUri(request);
		
		//拦截非法请求
		if(!(handler instanceof HandlerMethod)){
			throw new Exception("[400 BAD REQUEST:requestMethod="+request.getMethod()+",URI="+requestUri+"]");
		}
		
//		//Error Page错误(这里抛出的异常，被GlobalExceptionHandler捕捉)
//		if(HttpStatus.NOT_FOUND.value() == response.getStatus()){
//			throw new Exception("[404 ERROR PAGE:requestMethod="+request.getMethod()+",URI="+requestUri+"]");
//		}
//		if(HttpStatus.INTERNAL_SERVER_ERROR.value() == response.getStatus()){
//			throw new Exception("[500 ERROR PAGE:requestMethod="+request.getMethod()+",URI="+requestUri+"]");
//		}
		
		//拦截Sql注入和Xss攻击
		if(checkXssSql(request)){
			throw new Exception("[XSS ATTACK or SQL INJECT:"+requestUri+"]");
		}
		
		//记录请求开始时间
		startTimeThreadLocal.set(System.currentTimeMillis());
		return true;
	}
	
	/**
	 * 检查Xss攻击和Sql注入
	 */
	private boolean checkXssSql(HttpServletRequest request){
		Map<String, String[]> paramMap = request.getParameterMap();
		for (String key : paramMap.keySet()) {
			String[] values = null;
			Object object = paramMap.get(key);
			if (object instanceof String[]) {
				values = (String[]) object;
			} else {
				values = new String[] { String.valueOf(object) };
			}

			for (String value : values) {
				if(SecureCheck.xssAttack(value) || SecureCheck.sqlInject(value)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取uri(相对路径，不包含域名)
	 */
	private String getUri(final HttpServletRequest request){
		String uri = request.getRequestURI();
		log.warn("URI="+uri);
		
//		log.warn("header:"+Constants.Jwt.HEADER+"="+request.getHeader(Constants.Jwt.HEADER));
//		Enumeration<String> headerNames = request.getHeaderNames();												//获取所有的消息头名称
//		while (headerNames.hasMoreElements()) {
//			String nextElement = headerNames.nextElement();
//			log.warn(nextElement+"="+request.getHeader(nextElement));
//		}
		
		String params = "";
		Map<String, String[]> paramMap = request.getParameterMap();
		for (String key : paramMap.keySet()) {
			String[] values = null;
			Object object = paramMap.get(key);
			if (object instanceof String[]) {
				values = (String[]) object;
			} else {
				values = new String[] { String.valueOf(object) };
			}

			for (String value : values) {
				log.warn(key+"="+value);
				params += key+"="+value+"&";
			}
		}
		params = null==params||params.isEmpty()?"":("?"+params.substring(0,params.lastIndexOf("&")));
		uri += params;
		
		return uri;
	}
	
}
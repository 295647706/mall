package com.api.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.api.interceptor.GlobalInterceptor;

/**
 * 
 * @Author Ruan
 * 
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Bean
	GlobalInterceptor globalInterceptor() {
		return new GlobalInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		InterceptorRegistration ir = registry.addInterceptor(new GlobalInterceptor());							//注册拦截器(无法注入Bean、Service、变量等)
		InterceptorRegistration ir = registry.addInterceptor(globalInterceptor());								//注册拦截器
		ir.addPathPatterns("/**");																				//配置拦截的路径
//		ir.excludePathPatterns("/**.html");																		//配置不拦截的路径
//		ir.excludePathPatterns("/login/**", "/logout/**", "/error/**");											//配置不拦截的路径

//		registry.addInterceptor(loginInterceptor()).addPathPatterns("/**")
//		.excludePathPatterns("/login/**", "/logout/**", "/error/**", "/weixin/**")
////		.excludePathPatterns("/swagger/**").excludePathPatterns("/swagger-resources/**").excludePathPatterns("/v2/api-docs")
//		.addPathPatterns("/weixin/pay/unify", "/weixin/paymess");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//解决swagger问题
//		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
	
}
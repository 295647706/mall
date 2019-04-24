package com.api.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.api.filter.JWTAuthenticationFilter;
import com.api.filter.JWTLoginFilter;
import com.api.filter.MyAuthenticationProvider;

/**
 * 
 * @Author Ruan
 * 
 * Spring Security配置，将JWTLoginFilter，JWTAuthenticationFilter组合在一起
 * 
 */

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyAuthenticationProvider provider;																	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()																								//跨域
			.and()
			.csrf().disable()																					//禁用csrf
			.authorizeRequests()																				//配置安全策略
//			.antMatchers(HttpMethod.POST, "/user/register").permitAll()											//允许所有用户访问/user/register的POST请求
			.antMatchers("/weixin/**").permitAll()
			.antMatchers("/captcha/**").permitAll()																//验证码
			.antMatchers("/favicon.ico").permitAll()
			.antMatchers("/test/**").permitAll()
			.antMatchers("/register/**").permitAll()//注册
			.antMatchers("/user/login/**").permitAll()//登录
			
			//TODO 获取用户信息，测试阶段使用
			.antMatchers("/user/info").permitAll()//获取用户信息，测试阶段使用

			.antMatchers("/user/**").permitAll()
			.antMatchers("/search/**").permitAll()

//			.antMatchers(HttpMethod.GET, "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()//允许对于网站静态资源的无授权访问
			.anyRequest().authenticated()																		//其余的所有请求都需要验证
//			.and()
//			.logout().permitAll()																				//定义logout不需要验证
			.and()
			.addFilter(new JWTLoginFilter(authenticationManager()))												//添加自定义Filter
			.addFilter(new JWTAuthenticationFilter(authenticationManager()));
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);																	//自定义验证
	}

}
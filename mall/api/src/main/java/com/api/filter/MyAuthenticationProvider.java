package com.api.filter;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.common.ErrorConstants;
import com.model.Login;
import com.model.user.UserSub;
import com.service.user.UserService;
import com.util.Crypt;

/**
 * 
 * @Author Ruan
 * 
 * AuthenticationProvider提供用户UserDetails的具体验证方式
 * 
 */

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Login login = (Login) authentication.getPrincipal();													//参数值(从JWTLoginFilter传过来)
//		String username = authentication.getName();																//登录账号
//		String password = (String) authentication.getCredentials();												//登录密码
		String username = login.getAccount();
		String password = login.getPassword();
		String captcha = login.getCaptcha();
		String captchaInSession = login.getCaptchaInSession();
		
		if(null==captcha || null==captchaInSession || !captcha.equalsIgnoreCase(captchaInSession)){
			throw new JWTAuthenticationException(ErrorConstants.CAPTCHA_INCORRECT);
		}
		
		UserSub userSub = userService.getByAccount(username);													//数据库值
		if (null == userSub) {
			throw new JWTAuthenticationException(ErrorConstants.ACCOUNT_NOEXIST);
		}
		if (!Crypt.validate(password, userSub.getPassword())) {
			throw new JWTAuthenticationException(ErrorConstants.LOGIN_PASSWORD_INCORRECT);
		}
		userSub.setPassword(null);																				//不保存密码
		
		//第二步
		//验证通过回传数据(被JWTLoginFilter使用)
		//Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		//return new UsernamePasswordAuthenticationToken(user, password, authorities);
		return new UsernamePasswordAuthenticationToken(userSub, null, new ArrayList<>());						//保存用户对象
//		return new UsernamePasswordAuthenticationToken(userSub.getId(), null, new ArrayList<>());				//保存用户ID
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
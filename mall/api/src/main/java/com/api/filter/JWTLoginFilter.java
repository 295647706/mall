package com.api.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.common.Constants;
import com.common.ErrorConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Login;
import com.model.user.UserSub;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.json.JSONObject;

/**
 * 
 * @Author Ruan
 * 
 * 处理登录：验证用户名密码正确后，生成一个token，并将token返回给客户端(退出：直接在客户端清除header[Authorization]即可)
 * 
 * 该类继承自UsernamePasswordAuthenticationFilter，重写2个方法
 *  attemptAuthentication：接收并解析用户凭证(账号 密码...)
 *  successfulAuthentication：用户成功登录后，这个方法会被调用，在这个方法里生成token
 * 
 */

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;

	public JWTLoginFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	/**
	 * 接收并解析用户凭证  
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			String captchaInSession = (String) request.getSession().getAttribute(Constants.CAPTCHA_SESSION_KEY);//验证码
			
			//第一步
			//将参数转成对象
			Login login = new ObjectMapper().readValue(request.getInputStream(), Login.class);
			login.setCaptchaInSession(captchaInSession);
			//通过UsernamePasswordAuthenticationToken将参数传递去验证(自定义验证MyAuthenticationProvider)
//			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getAccount(), login.getPassword(), new ArrayList<>()));
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, login.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 用户成功登录后，这个方法会被调用，在这个方法里生成token
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		long expMillis = nowMillis + Constants.Jwt.EXPIRATION * 1000;
		Date exp = new Date(expMillis);
		
		UserSub userSub = (UserSub) authResult.getPrincipal();
		JSONObject userJson = new JSONObject();
		userJson.put(Constants.Jwt.ID, userSub.getId());
		userJson.put(Constants.Jwt.REALNAME, userSub.getRealName());
		userJson.put(Constants.Jwt.NICKNAME, userSub.getNickName());
		userJson.put(Constants.Jwt.LOGO, userSub.getLogo());
//		Long userId = (Long) authResult.getPrincipal();
		
		//第三步
		String token = Jwts.builder()
				.setIssuedAt(now)																				//创建时间
				.setSubject(userJson.toString())																//主题(用户的信息)
//				.setSubject(userId.toString())																	//主题(用户ID)
				.setExpiration(exp).setNotBefore(now)															//过期时间
				.signWith(SignatureAlgorithm.HS512, Constants.Jwt.SIGNING_KEY)									//签名
				.compact();																						//生成JWT
		response.addHeader(Constants.Jwt.HEADER, Constants.Jwt.TOKEN_PREFIX + token);
		
		JSONObject json = new JSONObject();
		json.put(Constants.DefaultKeyValue.CODE_KEY, Constants.DefaultKeyValue.CODE_VALUE_SUCCESS);
		json.put(Constants.DefaultKeyValue.MSG_KEY, Constants.DefaultKeyValue.MSG_VALUE);
		json.put(Constants.DefaultKeyValue.DATA_KEY, Constants.DefaultKeyValue.DATA_VALUE);
		response.getWriter().print(json.toString());
		response.getWriter().flush();
//		super.successfulAuthentication(request, response, chain, authResult);
	}

	/**
	 * 登录失败(自定义验证MyAuthenticationProvider中验证不通过或抛出的异常在这处理)
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		
		JSONObject json = new JSONObject();
		json.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.ACCOUNT_PASSWORD_ERROR);
		json.put(Constants.DefaultKeyValue.MSG_KEY, ErrorConstants.ERROR_MAP.get(ErrorConstants.ACCOUNT_PASSWORD_ERROR));
		json.put(Constants.DefaultKeyValue.DATA_KEY, Constants.DefaultKeyValue.DATA_VALUE);
		
		if (failed instanceof JWTAuthenticationException) {
			JWTAuthenticationException jwtAE = (JWTAuthenticationException) failed;
			json.put(Constants.DefaultKeyValue.CODE_KEY, jwtAE.getCode());
			json.put(Constants.DefaultKeyValue.MSG_KEY, jwtAE.getMessage());
		}
		
		response.getWriter().print(json.toString());
//		super.unsuccessfulAuthentication(request, response, failed);
	}

}
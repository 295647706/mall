package com.api.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.common.Constants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * 
 * @Author Ruan
 * 
 * 校验token
 *  token由三部分组成(header头、payload负载和signature签名)，每部分用.分隔，每段都是用Base64编码(可用在线解码器https://www.base64decode.org查看)，如：
 *  eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1MjExODY5MDUsInN1YiI6IjE4MDI4NTY2NzAwIiwiZXhwIjoxNTIzNzc4OTA1LCJuYmYiOjE1MjExODY5MDV9.hRrpCb7VcsLtaYz4D2nBa27titqyE1w_p-zLqPagTtwi3D4GaEpQ0if9mYktHf7axUTuQxIgedI8JI7xw-Wjhw
 * 
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，从http头的Authorization项读取token数据，
 * 然后用Jwts包提供的方法校验token的合法性，如果校验通过，就认为这是一个取得授权的合法请求
 * 
 */

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
	
	private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String header = "";
		try{
			header = request.getHeader(Constants.Jwt.HEADER);
			if (null == header || !header.startsWith(Constants.Jwt.TOKEN_PREFIX)) {
				SecurityContextHolder.clearContext();
				chain.doFilter(request, response);
				log.error("uri="+request.getRequestURI()+",header="+header);
				return;
			}

			UsernamePasswordAuthenticationToken authentication = getAuthentication(header);
			if (null == authentication) {
				SecurityContextHolder.clearContext();
				chain.doFilter(request, response);
				log.error("uri="+request.getRequestURI()+",header="+header);
				return;
			}
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		}catch(IOException e){
			log.error("uri="+request.getRequestURI()+",header="+header);
			log.error(e.getMessage(), e);
			throw e;
		}catch(ServletException e){
			log.error("uri="+request.getRequestURI()+",header="+header);
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String header) {
		try{
			String subject = Jwts.parser()
					.setSigningKey(Constants.Jwt.SIGNING_KEY)
					.parseClaimsJws(header.replace(Constants.Jwt.TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			if (null != subject && !subject.isEmpty()) {
				return new UsernamePasswordAuthenticationToken(subject, null, new ArrayList<>());
			}
		} catch (ExpiredJwtException e){
			log.error(e.getMessage(), e);
		} catch (UnsupportedJwtException e){
			log.error(e.getMessage(), e);
		} catch (MalformedJwtException e){
			log.error(e.getMessage(), e);
		} catch (SignatureException e){
			log.error(e.getMessage(), e);
		} catch (IllegalArgumentException e){
			log.error(e.getMessage(), e);
		} catch (AuthenticationException e){
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
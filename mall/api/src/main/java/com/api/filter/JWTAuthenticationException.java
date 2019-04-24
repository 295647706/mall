package com.api.filter;

import org.springframework.security.core.AuthenticationException;

import com.common.ErrorConstants;

/**
 * 
 * @Author Ruan
 * 
 */

public class JWTAuthenticationException extends AuthenticationException {
	
	private static final long serialVersionUID = 5673828596971315017L;
	
	private int code;																							//错误码，参照ErrorConstants

	public JWTAuthenticationException(String message){
		super(message);
	}
	
	public JWTAuthenticationException(int code){
		super(ErrorConstants.ERROR_MAP.get(code));
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

}
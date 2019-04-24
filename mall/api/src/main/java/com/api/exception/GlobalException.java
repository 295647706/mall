package com.api.exception;

import com.common.ErrorConstants;

/**
 * 
 * @Author Ruan
 * 
 * 自定义全局异常
 * 
 */

public class GlobalException extends Exception {
	
	private static final long serialVersionUID = 5351448876944655072L;
	
	private int code;																							//错误码，参照ErrorConstants

	public GlobalException(String message){
		super(message);
	}
	
	public GlobalException(int code){
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
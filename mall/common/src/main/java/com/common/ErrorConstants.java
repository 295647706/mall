package com.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Author Ruan
 * 
 * 常量-错误码
 * 
 */

public final class ErrorConstants {
	
	public static final int BAD_REQUEST = 400;																	//400 BAD REQUEST！
	public static final int UNAUTHORIZED = 401;																	//401 UNAUTHORIZED！
	public static final int NOT_FOUND = 404;																	//404 NOT FOUND！
	public static final int INTERNAL_SERVER_ERROR = 500;														//500 INTERNAL SERVER ERROR！

	public static final int SYSTEM_ABNORMAL = 4001;																//系统异常！
	public static final int ERROR_PAGE = 4002;																	//页面错误！
	public static final int XSS_ATTACK_OR_SQL_INJECT = 4003;													//Xss攻击或Sql注入！
	
	public static final int DATA_ABNORMAL = 4010;																//数据异常！
	public static final int DATA_FORMAT_INCORRECT = 4011;														//数据格式不正确！
	public static final int DATA_DATE_FORMAT_INCORRECT = 4012;													//日期格式不正确！
	public static final int DATA_NO_TRANSACTION = 4013;															//事务异常！
	public static final int DATA_ID_INCORRECT = 4014;															//ID不正确！
	
	public static final int REDIS_ABNORMAL = 4050;																//Redis异常！
	
	public static final int SIGN_INCORRECT = 4201;																//签名不正确！
	public static final int PARAMETER_INCORRECT = 4202;															//参数不正确！
	public static final int PARAMETER_NOEXIST = 4203;															//参数不存在！
	public static final int PARAMETER_TYPE_MISMATCH = 4204;														//参数类型不匹配！
	public static final int PARAMETER_VALIDATION_TYPE_INCORRECT = 4205;											//参数校验注解类型不正确！
	
	public static final int LOGIN_FIRST = 4301;																	//请先登录！
	public static final int LOGIN_TIMEOUT = 4302;																//登录超时，请重新登录！
	public static final int ACCOUNT_PASSWORD_ERROR = 4303;														//账号或密码错误！
	public static final int ACCOUNT_NOEXIST = 4304;																//账号不存在！
	public static final int LOGIN_PASSWORD_INCORRECT = 4305;													//密码不正确！
	public static final int PHONE_INCORRECT = 4306;																//手机号码不正确！
	public static final int SMSCODE_INCORRECT = 4307;															//短信验证码不正确！
	public static final int SMSCODE_SEND_FAILURE = 4308;														//发送短信验证码失败！
	public static final int CAPTCHA_INCORRECT = 4309;															//验证码不正确！
	
	public static final int USER_EXIST = 4401;																	//用户已存在！
	public static final int USER_NOEXIST = 4402;																//用户不存在！
	public static final int USER_ABNORMAL = 4403;																//用户异常！
	
	public static final Map<Integer, String> ERROR_MAP;
	static {
		ERROR_MAP = new HashMap<Integer, String>();
		
		ERROR_MAP.put(BAD_REQUEST, "400 BAD REQUEST！");
		ERROR_MAP.put(UNAUTHORIZED, "401 UNAUTHORIZED！");
		ERROR_MAP.put(NOT_FOUND, "404 NOT FOUND！");
		ERROR_MAP.put(INTERNAL_SERVER_ERROR, "500 INTERNAL SERVER ERROR！");
		
		ERROR_MAP.put(SYSTEM_ABNORMAL, "系统异常！");
		ERROR_MAP.put(ERROR_PAGE, "页面错误！");
		ERROR_MAP.put(XSS_ATTACK_OR_SQL_INJECT, "Xss攻击或Sql注入！");
		
		ERROR_MAP.put(DATA_ABNORMAL, "数据异常！");
		ERROR_MAP.put(DATA_FORMAT_INCORRECT, "数据格式不正确！");
		ERROR_MAP.put(DATA_DATE_FORMAT_INCORRECT, "日期格式不正确！");
		ERROR_MAP.put(DATA_NO_TRANSACTION, "事务异常！");
		ERROR_MAP.put(DATA_ID_INCORRECT, "ID不正确！");
		
		ERROR_MAP.put(REDIS_ABNORMAL, "Redis异常！");
		
		ERROR_MAP.put(SIGN_INCORRECT, "签名不正确！");
		ERROR_MAP.put(PARAMETER_INCORRECT, "参数不正确！");
		ERROR_MAP.put(PARAMETER_NOEXIST, "参数不存在！");
		ERROR_MAP.put(PARAMETER_TYPE_MISMATCH, "参数类型不匹配！");
		ERROR_MAP.put(PARAMETER_VALIDATION_TYPE_INCORRECT, "参数校验注解类型不正确！");

		ERROR_MAP.put(LOGIN_FIRST, "请先登录！");
		ERROR_MAP.put(LOGIN_TIMEOUT, "登录超时，请重新登录！");
		ERROR_MAP.put(ACCOUNT_PASSWORD_ERROR, "账号或密码错误！");
		ERROR_MAP.put(ACCOUNT_NOEXIST, "账号或密码错误！");																//账号不存在！(不明确提示错误，以防被枚举出所有账号)
		ERROR_MAP.put(LOGIN_PASSWORD_INCORRECT, "账号或密码错误！");														//密码不正确！
		ERROR_MAP.put(PHONE_INCORRECT, "手机号码不正确！");
		ERROR_MAP.put(SMSCODE_INCORRECT, "短信验证码不正确！");
		ERROR_MAP.put(SMSCODE_SEND_FAILURE, "发送短信验证码失败！");
		ERROR_MAP.put(CAPTCHA_INCORRECT, "验证码不正确！");
		
		ERROR_MAP.put(USER_EXIST, "用户已存在！");
		ERROR_MAP.put(USER_NOEXIST, "用户不存在！");
		ERROR_MAP.put(USER_ABNORMAL, "用户异常！");
	}

}
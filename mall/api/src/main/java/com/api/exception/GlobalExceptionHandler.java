package com.api.exception;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.TransactionRequiredException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;

import org.hibernate.id.IdentifierGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.common.Constants;
import com.common.ErrorConstants;

/**
 * 
 * @Author Ruan
 * 
 * 自定义统一异常处理
 * 
 */

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Object defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
		boolean showLog = true;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.DefaultKeyValue.CODE_KEY, Constants.DefaultKeyValue.CODE_VALUE_FAILURE);
		map.put(Constants.DefaultKeyValue.MSG_KEY, e.getMessage());
		
		String userId = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(null!=authentication){
			userId = authentication.getPrincipal().toString();													//当前登录的用户ID
		}
		
		if (HttpStatus.UNAUTHORIZED.value() == response.getStatus()) {											//401未授权
			map.put(Constants.DefaultKeyValue.CODE_KEY, HttpStatus.UNAUTHORIZED.value());
			map.put(Constants.DefaultKeyValue.MSG_KEY, "UNAUTHORIZED!");
			showLog = false;
		} else if (HttpStatus.FORBIDDEN.value() == response.getStatus()) {										//403不允许
			map.put(Constants.DefaultKeyValue.CODE_KEY, HttpStatus.FORBIDDEN.value());
			map.put(Constants.DefaultKeyValue.MSG_KEY, "FORBIDDEN!");
			showLog = false;
		} else if (e instanceof NoHandlerFoundException || HttpStatus.NOT_FOUND.value()==response.getStatus()) {//404异常
			map.put(Constants.DefaultKeyValue.CODE_KEY, HttpStatus.NOT_FOUND.value());
			map.put(Constants.DefaultKeyValue.MSG_KEY, "NOT FOUND!");
			if(request.getRequestURI().equals("/error")){
				showLog = false;
			}
		} else if (HttpStatus.INTERNAL_SERVER_ERROR.value() == response.getStatus()) {							//500异常
			map.put(Constants.DefaultKeyValue.CODE_KEY, HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put(Constants.DefaultKeyValue.MSG_KEY, "INTERNAL SERVER ERROR!");
			showLog = false;
		} else if (e instanceof PoolException) {																//Redis缓存异常
			map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.REDIS_ABNORMAL);
			map.put(Constants.DefaultKeyValue.MSG_KEY, ErrorConstants.ERROR_MAP.get(ErrorConstants.REDIS_ABNORMAL));
		} else if(e instanceof MissingServletRequestParameterException) {										//参数不存在(Required double parameter 'xxx' is not present)
			String parameterName = e.getMessage().split("parameter")[1].split("is")[0].trim();
			map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.PARAMETER_NOEXIST);
			map.put(Constants.DefaultKeyValue.MSG_KEY, parameterName+ErrorConstants.ERROR_MAP.get(ErrorConstants.PARAMETER_NOEXIST));
			showLog = false;
		} else if (e instanceof MethodArgumentTypeMismatchException) {											//参数类型不匹配
			map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.PARAMETER_TYPE_MISMATCH);
			map.put(Constants.DefaultKeyValue.MSG_KEY, ErrorConstants.ERROR_MAP.get(ErrorConstants.PARAMETER_TYPE_MISMATCH));
		} else if (e instanceof UnexpectedTypeException) {														//参数校验注解类型不正确
			map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.PARAMETER_VALIDATION_TYPE_INCORRECT);
			map.put(Constants.DefaultKeyValue.MSG_KEY, ErrorConstants.ERROR_MAP.get(ErrorConstants.PARAMETER_VALIDATION_TYPE_INCORRECT));
		} else if (e instanceof ConstraintViolationException) {													//参数校验失败(单个)
			map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.PARAMETER_INCORRECT);
			map.put(Constants.DefaultKeyValue.MSG_KEY, e.getMessage().substring(e.getMessage().lastIndexOf(":")));
		} else if (e instanceof BindException) {																//参数校验失败(对象)
			map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.PARAMETER_INCORRECT);
			map.put(Constants.DefaultKeyValue.MSG_KEY, "");
			BindException be = (BindException) e;
			for(ObjectError oe:be.getAllErrors()){
				map.put(Constants.DefaultKeyValue.MSG_KEY, map.get(Constants.DefaultKeyValue.MSG_KEY)+"["+oe.getDefaultMessage()+"]");
			}
		} else if (e instanceof DataAccessException) {															//数据异常
			map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.DATA_ABNORMAL);
			map.put(Constants.DefaultKeyValue.MSG_KEY, ErrorConstants.ERROR_MAP.get(ErrorConstants.DATA_ABNORMAL));
			
			Throwable t = e.getCause();
			if (null != t) {
				if (t instanceof TransactionRequiredException) {												//事务异常
					map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.DATA_NO_TRANSACTION);
					map.put(Constants.DefaultKeyValue.MSG_KEY, ErrorConstants.ERROR_MAP.get(ErrorConstants.DATA_NO_TRANSACTION));
				} else if (t instanceof IdentifierGenerationException) {										//ID不正确
					map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.DATA_ID_INCORRECT);
					map.put(Constants.DefaultKeyValue.MSG_KEY, ErrorConstants.ERROR_MAP.get(ErrorConstants.DATA_ID_INCORRECT));
				} else {
					t = t.getCause();
					if (null != t) {
//						if (t instanceof MySQLIntegrityConstraintViolationException) {
//							map.put(Constants.DefaultKeyValue.MSG_KEY, t.getMessage());
//						}
					}
				}
			}
		} else if (e instanceof TransactionSystemException) {													//事务异常
			map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.DATA_NO_TRANSACTION);
			map.put(Constants.DefaultKeyValue.MSG_KEY, ErrorConstants.ERROR_MAP.get(ErrorConstants.DATA_NO_TRANSACTION));
		} else if (e instanceof BindException) {																//数据格式不正确
			map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.DATA_FORMAT_INCORRECT);
			map.put(Constants.DefaultKeyValue.MSG_KEY, ErrorConstants.ERROR_MAP.get(ErrorConstants.DATA_FORMAT_INCORRECT));
			if (e.getMessage().contains("Could not parse date")) {												//无法解析日期
				map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.DATA_DATE_FORMAT_INCORRECT);
				map.put(Constants.DefaultKeyValue.MSG_KEY, ErrorConstants.ERROR_MAP.get(ErrorConstants.DATA_DATE_FORMAT_INCORRECT));
			}
		} else if (e instanceof GlobalException) {
			GlobalException ge = (GlobalException) e;
			map.put(Constants.DefaultKeyValue.CODE_KEY, ge.getCode());
			map.put(Constants.DefaultKeyValue.MSG_KEY, ge.getMessage());
			showLog = ErrorConstants.LOGIN_FIRST!=ge.getCode();
		} else {
			map.put(Constants.DefaultKeyValue.CODE_KEY, ErrorConstants.SYSTEM_ABNORMAL);
			map.put(Constants.DefaultKeyValue.MSG_KEY, "未知异常！");
		}
		
		if(showLog) {
			log.error("=====> userId="+userId);
			showRequestMess(request);
			log.error(e.getMessage(), e);
		}
		
		return map;
	}
	
	/**
	 * 打印请求信息
	 */
	private void showRequestMess(HttpServletRequest request){
		log.error("URI="+request.getRequestURI());
		
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
				log.error(key+"="+value);
			}
		}
	}
	
}
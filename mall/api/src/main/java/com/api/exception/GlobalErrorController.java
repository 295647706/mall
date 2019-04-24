package com.api.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @Author Ruan
 * 
 * BasicErrorController：捕获 /error的所有错误
 * BasicErrorController extends AbstractErrorController implements ErrorController
 * 
 */

@Controller
public class GlobalErrorController implements ErrorController {
	
	private final static String PATH = "/error";

	@Override
	public String getErrorPath() {
		return PATH;
	}
	
	@RequestMapping(value = PATH, produces = { MediaType.TEXT_HTML_VALUE }, method = { RequestMethod.GET, RequestMethod.POST })
	public String errorHtml(HttpServletRequest request, HttpServletResponse response) {
//		return "返回自定义错误页面";
		throw new RuntimeException("http status=" + response.getStatus());										//抛出异常，被GlobalExceptionHandler捕捉
	}

	@RequestMapping(value = PATH, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object error(HttpServletRequest request, HttpServletResponse response) {
//		return "返回自定义错误数据，JSON或MAP等";
		throw new RuntimeException("http status=" + response.getStatus());										//抛出异常，被GlobalExceptionHandler捕捉
	}

}
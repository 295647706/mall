package com.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.Constants;

/**
 * 
 * @Author Ruan
 * 
 */

@RestController("index")
public class IndexController extends BaseController {
	
//	@Autowired
//	private HttpServletRequest request;																			//自动注入
	
//	@Autowired
//	private UserService userService;
	
	/**
	 * 首页
	 * @ModelAttribute：接收multipart/form-data参数，并封装成对象，如：@ModelAttribute(value="user")User user
	 * @RequestBody：接收application/json参数，并封装成对象，如：@RequestBody User user
	 */
	@RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
	public Object index(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.DefaultKeyValue.CODE_KEY, Constants.DefaultKeyValue.CODE_VALUE_SUCCESS);
		map.put(Constants.DefaultKeyValue.MSG_KEY, Constants.DefaultKeyValue.MSG_VALUE);
		return map;
	}
	
	/**
	 * 重置JwtToken
	 */
//	@GetMapping("/resetjwttoken")
//	public Object resetJwttoken(){
//		return userService.resetJwttoken();
//	}
	
}
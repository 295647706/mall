package com.api.controller;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.user.User;
import com.service.user.UserService;

/**
 * 
 * @Author Ruan
 * 
 * 测试
 * 
 */

@Validated
@RestController("test")
@RequestMapping("/test")
public class TestController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public Object test(){
		return userService.test();
	}
	
	/**
	 * 单个参数校验(需要在类上添加@Validated注解来启动校验，校验不通过会抛出异常，在统一异常里处理)
	 */
	@GetMapping("/validate/params/single")
	public Object testValidateParamsSingle(@NotNull(message = "ID不能为空！") @Min(value = 1, message = "ID必须大于或等于{value}") @Max(value = 10) Long id, 
			@Length(min = 2, max = 6, message="名称长度必须在{min}-{max}之间！") String name){
		return "测试验证单个参数！";
	}
	
	/**
	 * 对象参数校验(需要在类上添加@Validated注解来启动校验，校验不通过会抛出异常，在统一异常里处理)
	 */
	@GetMapping("/validate/params/obj")
	public Object testValidateParamsObj(@Valid User user){
		return "测试验证对象参数！";
	}
	
//	/**
//	 * 生成JWTToken
//	 * @param Long userId：用户ID
//	 */
//	@PostMapping("/jwttoken")
//	public Object getJwtToken(Long userId) {
//		UserSub userSub = userService.getUserSubById(userId, true);
//		JSONObject userJson = new JSONObject();
//		userJson.put(Constants.Jwt.ID, userSub.getId());
//		userJson.put(Constants.Jwt.REALNAME, userSub.getRealName());
//		userJson.put(Constants.Jwt.NICKNAME, userSub.getNickName());
//		userJson.put(Constants.Jwt.LOGO, userSub.getLogo());
//		String jwtToken = JwtToken.create(userJson.toString());													//相当于security jwt的登录成功
//		
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("user", userSub);
//		data.put("jwtToken", jwtToken);
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put(Constants.DefaultKeyValue.CODE_KEY, Constants.DefaultKeyValue.CODE_VALUE_SUCCESS);
//		map.put(Constants.DefaultKeyValue.MSG_KEY, Constants.DefaultKeyValue.MSG_VALUE_SUCCESS);
//		map.put(Constants.DefaultKeyValue.DATA_KEY, data);
//		
//		return map;
//	}
	
}
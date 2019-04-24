package com.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.common.Constants;
import com.dao.BaseMapper;
import com.dao.user.UserDao;
import com.model.user.User;
import com.model.user.UserSub;
import com.service.user.UserService;
import com.util.Crypt;
import com.util.jwt.JwtToken;

import net.sf.json.JSONObject;
import tk.mybatis.mapper.entity.Example;

/**
 * 客户端用户 登录、注册 
 * @author wuyahan
 *
 */
@RestController("LoginRegistController")
public class LoginRegistController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BaseMapper<User, UserSub> baseMapper;
	
	
	/**
	 * 获取用户信息
	 * @return
	 */
	@GetMapping("/user/info")
	public Object userInfo(){
		JSONObject json = new JSONObject();
		Integer code = 0;	
		Object data = "";
		String msg ="获取用户信息成功";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		JSONObject userJson = JSONObject.fromObject(authentication.getPrincipal());
		//return userJson.getLong(Constants.Jwt.ID);
		
		json.put("code", code);
		json.put("data", authentication.getPrincipal());
		json.put("msg", msg);
		return json;
		
	}
	
	@PostMapping("/user/login")
	public Object login(HttpServletRequest request, HttpServletResponse response, String account, String password){
		
		JSONObject json = new JSONObject();
		Integer code = 0;	
		Object data = "";
		String msg ="";
		
		System.out.println("登录账号:----"+account);
		
		
	    Map<String, Object> mapDate = new HashMap<String, Object>();
		
		if(StringUtils.isNotEmpty(account)) {
			//手机号码检测
			Example example = new Example(User.class);
			Example.Criteria criteria1 = example.createCriteria();
			criteria1.andEqualTo("phone", account);
			//data.put("userExample2", mapper.selectOneByExample(example2));
			User user = baseMapper.selectOneByExample(example);
			


			if(null!=user) {
				
				if(Crypt.validate(password,user.getPassword())) {
					
					JSONObject userJson = new JSONObject();
					userJson.put(Constants.Jwt.ID, user.getId());
					String jwtToken = JwtToken.create(userJson.toString());
				    mapDate.put("jwtToken", jwtToken);
				    
				  //  code = -1;	
					msg = "登录成功";
				}else {
					code = -1;	
					msg = "密码错误";
				}
				
				
			}else {
				criteria1 = example.createCriteria();
				criteria1.andEqualTo("account", account);
				//data.put("userExample2", mapper.selectOneByExample(example2));
				user = baseMapper.selectOneByExample(example);
				
				if(null!=user) {
					 if(Crypt.validate(password,user.getPassword())) {
							JSONObject userJson = new JSONObject();
							userJson.put(Constants.Jwt.ID, user.getId());
							String jwtToken = JwtToken.create(userJson.toString());
						    mapDate.put("jwtToken", jwtToken);
						}else {
							code = -1;	
							msg = "登录成功";
						}
				}else {
					code = -1;	
					msg = "密码错误";
				}
				
				
               
				 
			}
			
		}else {
			
			code = -1;	
			msg = "输入账号为空";
		}

		
		json.put("code", code);
		json.put("data", mapDate);
		json.put("msg", msg);

		return json;
	}
	
//	
//	phone	string	是	手机	
//	password	string	是	密码	
//	smsCode	string	是	验证码	
//	inviteCode	string	否	邀请码
//	
//	
	/**
	 * 
	 * @param request
	 * @param response 
	 * @param phone 手机	
	 * @param password 密码	
	 * @param smsCode 验证码
	 * @param inviteCode 邀请码
	 * @return
	 */
	@PostMapping("/register")
	public Object register(HttpServletRequest request, HttpServletResponse response, String phone, String password,String smsCode,String inviteCode){
		
		JSONObject json = new JSONObject();
		Integer code = 0;	
		Object data = "";
		String msg ="";

		
		//手机号码检测
		Example example = new Example(User.class);
		Example.Criteria criteria1 = example.createCriteria();
		criteria1.andEqualTo("phone", phone);
		//data.put("userExample2", mapper.selectOneByExample(example2));
		User user = baseMapper.selectOneByExample(example);
	
		
		
		//获取验证码
		String captcha = (String) request.getSession().getAttribute(Constants.CAPTCHA_SESSION_KEY);//, captcha);		

		if(null!=captcha&&captcha.equals(smsCode)) {
		
		
		//手机号码已经存在
		if(user!=null) {
			 code = -1;	
			 msg = "手机号"+phone+"已经存在";
		}
		//手机号码没有操作
		else {
			
			password = Crypt.encrypt(password);
			
			user = new User();
			user.setDefault();
			user.setPhone(phone);
			user.setPassword(password);
			baseMapper.insertSelective(user);
			
			//UserSub userSub = new UserSub(user.getId(), "", "", "");
			
			JSONObject userJson = new JSONObject();
			userJson.put(Constants.Jwt.ID, user.getId());
			String jwtToken = JwtToken.create(userJson.toString());
			  
		    Map<String, Object> mapDate = new HashMap<String, Object>();
		    mapDate.put("jwtToken", jwtToken);
			//data = JwtToken.create(user.getId()+"");
		    data = mapDate;
			//code = -1;	
			msg = "注册成功";
			
		}
		
		}else {
			code = -1;	
			msg = "验证码错误";
			
		}
		
		
		
		json.put("code", code);
		json.put("data", data);
		json.put("msg", msg);

		return json;
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
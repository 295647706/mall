package com.api.controller.user;

import com.api.controller.BaseController;
import com.model.user.User;
import com.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @Author Ruan
 * 
 */

@RestController("user")
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;


	/**
	 * 用户信息
	 * @return
	 */
	@GetMapping("")
	public Object user(){
		return userService.getUserInfo();
	}

	/**
	 * 修改密码
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @param confirmPassword 确认密码
	 * @return
	 */
	@PostMapping("/edit/password")
	public Object editPassword(@RequestParam String oldPassword,
							   @RequestParam String newPassword,
							   @RequestParam String confirmPassword){
		return userService.editPassword(oldPassword, newPassword, confirmPassword);
	}

	/**
	 * 修改资料
	 * @param user
	 * @return
	 */
	@PostMapping("/edit")
	public Object edit(@RequestBody User user){
		return userService.edit(user);
	}

	/**
	 * 修改电话号码 （未完成，验证码校验）
	 * @param request
	 * @param phone
	 * @param smsCode
	 * @return
	 */
	@PostMapping("/edit/phone")
	public Object editPhone(HttpServletRequest request, @RequestParam String phone,
							@RequestParam String smsCode){
		return userService.editPhone(phone, smsCode);
	}

	/**
	 * 获取用户信息
	 * @return
	 */
	@GetMapping("/detail")
	public Object userDetail(){
		return userService.getUserDetail();
	}
	
}
package com.api.controller;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.api.sms.model.request.SmsSendRequest;
import com.api.sms.model.response.SmsSendResponse;
import com.api.sms.util.ChuangLanSmsUtil;
import com.common.Constants;
import com.util.Captcha;

import net.sf.json.JSONObject;

/**
 * 
 * @Author Ruan
 * 
 */

@RestController("captcha")
@RequestMapping(value="/captcha")
public class CaptchaController {
	
	
	
	public static final String charset = "utf-8";
	 //用户平台API账号(非登录账号,示例:N1234567)
    public static String account = "N0315423";
    // 用户平台API密码(非登录密码)
    public static String password = "and123AND123";
    //请求地址
    public static String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";
	
	private static final Logger log = LoggerFactory.getLogger(CaptchaController.class);
	
	/**
	 * 创建图片验证码
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 */
	@RequestMapping(value="/createimagecode", method = RequestMethod.GET)
	public void createImageCode(HttpServletRequest request, HttpServletResponse response) {
		try{
			Object[] vcObject = Captcha.create(30, 4, 80, null);												//创建验证码和图片
			if("-1" == vcObject[0].toString()){
				
			}else{
				String captcha = vcObject[0].toString();
				
				request.getSession().setAttribute(Constants.CAPTCHA_SESSION_KEY, captcha);						//保存验证码到session
				
				//设置响应头通知浏览器以图片的形式打开，等同于response.setHeader("Content-Type", "image/jpeg");
				response.setContentType("image/jpeg");
				//设置响应头控制浏览器不要缓存
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", -1);
				
				ImageIO.write((BufferedImage)vcObject[1], "png", response.getOutputStream());					//将图片写给浏览器
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
//	/**
//	 * 发送短信验证码
//	 * @param HttpServletRequest request
//	 * @param HttpServletResponse response
//	 * @param String phone：手机号码
//	 * @param Integer flag：null|0=需要验证图片验证码 1=不需要验证图片验证码
//	 * @param String validateCodeImage：图片验证码
//	 * @return Object：{"code":0=失败|1=成功,"data":data}
//	 */
	@ResponseBody
	@RequestMapping(value="/sendsmscode", method = {RequestMethod.GET, RequestMethod.POST})
	public Object sendSmsValidateCode(HttpServletRequest request, HttpServletResponse response, String phone, String userId) {
		JSONObject json = new JSONObject();
		Integer code = 0;																						//code：0=失败 1=成功
		String data = "";
		try{
			
			//创建验证码
			String captcha = Captcha.create(4, 1);
			
		//	sendSmsCode(phone,captcha,request);
//			
//			//发送验证码
			 String msg = "【三信916商城】你好,你的验证码是"+captcha;
//		     //手机号码（群发手机号码之间使用英文逗号隔开）
//		     //状态报告
		     String report= "true";
//
		    SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone,report);
//
		    String requestJson = JSON.toJSONString(smsSingleRequest);
//		    
//
		    log.info("短信接口请求前: " + requestJson);
//
	        String responseJson = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
//
	        log.info("短信接口请求后:" + responseJson);
//
	        SmsSendResponse smsSingleResponse = JSON.parseObject(responseJson, SmsSendResponse.class);
//
//	       // log.info("response  toString is :" + smsSingleResponse);
//			
//		    
//	        //发送成功
	        if(smsSingleResponse.getCode().equals("0")) {
//	        	//保存验证码
				request.getSession().setAttribute(Constants.CAPTCHA_SESSION_KEY, captcha);		
			    data = "验证码发送成功";

//				//保存验证码到session
	        }else {
//	        	
	        	code = -1;
	        	data = smsSingleResponse.getErrorMsg();
//
	        }
	        
			
			

		}catch(Exception e){
			data = "接口异常,请稍后再试";
			log.error(e.getMessage(), e);
		}
		json.put("code", code);
		json.put("data", data);
		return json;
	}
	
	
	public  void sendSmsCode(String phone ,String captcha, HttpServletRequest request) throws UnsupportedEncodingException {
        //请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
      //  String smsSingleRequestServerUrl = "https://xxx/msg/send/json";
        // 短信内容
        String msg = "【916三信】你好,你的验证码是"+captcha;
        //手机号码
       // String phone = "15014175798";
        //状态报告
        String report= "true";

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone,report);

        String requestJson = JSON.toJSONString(smsSingleRequest);

        System.out.println("before request string is: " + requestJson);

        String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);

        System.out.println("response after request result is :" + response);

        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);

        System.out.println("response  toString is :" + smsSingleResponse);
        
       // 发送成功
        if(smsSingleResponse.getCode().equals("0")) {
        	//保存验证码
			request.getSession().setAttribute(Constants.CAPTCHA_SESSION_KEY, captcha);		
			
			//保存验证码到session
        }else {
        	
        	throw new RuntimeException(smsSingleResponse.getErrorMsg());
        	//code = -1;
    		//data = smsSingleResponse.getErrorMsg();

        }
    }
	
	public static void main(String[] args) throws UnsupportedEncodingException {
        //请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
      //  String smsSingleRequestServerUrl = "https://xxx/msg/send/json";
        // 短信内容
        String msg = "【三信916商城】你好,你的验证码是123456";
        //手机号码
        String phone = "15014175798";
        //状态报告
        String report= "true";

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone,report);

        String requestJson = JSON.toJSONString(smsSingleRequest);

       // System.out.println("before request string is: " + requestJson);

        String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);

       // System.out.println("response after request result is :" + response);

        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);

       // System.out.println("response  toString is :" + smsSingleResponse);
    }
	
}
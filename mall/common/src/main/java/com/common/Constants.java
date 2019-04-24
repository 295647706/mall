package com.common;

/**
 * 
 * @Author Ruan
 * 
 * 常量
 * 
 */

public final class Constants {
	
	public static final String CAPTCHA_SESSION_KEY = "R_CAPTCHA_KEY";											//验证码在session中的key
	
	public interface DefaultPage{																				//Page默认值
		public static final int PAGE = 0;																		//当前页码(从0开始)
		public static final int SIZE = 10;																		//每页数量
		public static final String DIRECTION_ASC = "ASC";														//排序，ASC=顺序，DESC=倒序
		public static final String DIRECTION_DESC = "DESC";
		public static final String PROPERTY_ID = "id";															//排序的列名(字段)
		public static final String PROPERTY_CREATETIME = "createTime";
		public static final String PROPERTY_UPDATETIME = "updateTime";
	}
	
	public interface DefaultBeanValue{																			//Bean默认值
		public static final int IS_DELETED_NO = 0;																//是否删除(0=否 1=是)
		public static final int IS_DELETED_YES = 1;
		
		public static final String OPERATER = "admin";															//操作者
		public static final String OPERATER_ID = "0";															//操作者ID
	}
	
	public interface DefaultKeyValue{																			//默认key-value
		public static final String CODE_KEY = "code";															//key
		public static final int CODE_VALUE_SUCCESS = 0;															//value:成功
		public static final int CODE_VALUE_FAILURE = 1;															//value:失败(!0)
		
		public static final String MSG_KEY = "msg";
		public static final String MSG_VALUE = "";
		public static final String MSG_VALUE_SUCCESS = "成功！";
		public static final String MSG_VALUE_FAILURE = "失败！";
		public static final String MSG_VALUE_ADD_SUCCESS = "添加成功！";
		public static final String MSG_VALUE_ADD_FAILURE = "添加失败！";
		public static final String MSG_VALUE_UPDATE_SUCCESS = "更新成功！";
		public static final String MSG_VALUE_UPDATE_FAILURE = "更新失败！";
		public static final String MSG_VALUE_DELETE_SUCCESS = "删除成功！";
		public static final String MSG_VALUE_DELETE_FAILURE = "删除失败！";
		
		public static final String DATA_KEY = "data";
		public static final String DATA_VALUE = "";
		
		public static final String GOTO_KEY = "goTo";
		public static final String GOTO_VALUE = "";
		public static final String GOTO_ERROR = "error/error";
		
		public static final String LIST_KEY = "list";
		public static final String PAGES_KEY = "pages";
	}
	
	public interface Jwt{																						//JWT(json web token)
		public static final String SIGNING_KEY = "r18028566713";												//JWT签名key
		public static final long EXPIRATION = 30 * 24 * 60 * 60L;												//JWT有效时间，单位：秒
		public static final String TOKEN_PREFIX = "Bearer ";													//JWT生成token的前辍(最后有个空格：Bearer token)
		public static final String HEADER = "Authorization";													//JWT返回header
		
		public static final String ID = "id";
		public static final String REALNAME = "realName";
		public static final String NICKNAME = "nickName";
		public static final String LOGO = "logo";
		public static final String SHOPID = "shopId";
	}
	
}
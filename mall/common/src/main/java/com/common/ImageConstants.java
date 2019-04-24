package com.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Author Ruan
 * 
 * 常量
 * 
 */

public final class ImageConstants {
	
	public enum Type{																							//类型：0=用户LOGO 10=商品主图 11=商品详情图 20=店铺LOGO
		USER_LOGO, PRODUCT_MASTER, PRODUCT_DETAIL, SHOP_LOGO;
	}
	
	public static final String SPEC_THUMBNAIL = "t";															//缩略图
	
	public static final Integer[][] SIZE = {{120,120},{400,400},{800,800}};										//尺寸[120][120] [400][400] [800][800]
//	public static final Integer[][] SIZE_USER_LOGO = {{120,120},{400,400},{800,800}};							//用户LOGO
//	public static final Integer[][] SIZE_SHOP_LOGO = {{120,120},{400,400},{800,800}};							//店铺LOGO
//	public static final Integer[][] SIZE_PRODUCT_MASTER = {{120,120},{400,400},{800,800}};						//商品主图
//	public static final Integer[][] SIZE_PRODUCT_DETAIL = {{120,120},{400,400},{800,800}};						//商品详情图
	
	public static final String[] SPEC = {"m","s","l"};															//规格，对应120 400 800
//	public static final String[] SUFFIX_USER_LOGO = {"m","s","l"};
//	public static final String[] SUFFIX_SHOP_LOGO = {"m","s","l"};
//	public static final String[] SUFFIX_PRODUCT_MASTER = {"m","s","l"};
//	public static final String[] SUFFIX_PRODUCT_DETAIL = {"m","s","l"};
	
	public static final Map<String, Integer[]> SPEC_SIZE;
	static {
		SPEC_SIZE = new HashMap<String, Integer[]>();
		for(int i=0; i<SPEC.length; i++){
			SPEC_SIZE.put(SPEC[i], SIZE[i]);
		}
	}
	
}
package com.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 
 * @Author Ruan
 * 
 * 图片变量
 * 
 */

@Component
@ConfigurationProperties
@PropertySource("classpath:image/${spring.profiles.active}.properties")
public final class ImageProperties {
	
	private String imgServer = "";																			//图片服务器(默认)
	
	private String parentPath;																				//父目录，可空(不用以/开头和结尾)，如：demo
	private String node;																					//节点，可空(不用以/开头和结尾)，区分文件服务器，如：i1
	private String folder;																					//文件夹(不用以/开头和结尾)，区别不同的工程项目，如：demos
	
	private String userFolderLogo;																			//用户
	
	private String shopFolderLogo;																			//店铺
	
	private String productFolderMaster;																		//商品
	private String productFolderDetail;
	
	public String getImgServer() {
		return imgServer;
	}
	public void setImgServer(String imgServer) {
		this.imgServer = imgServer;
	}
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getUserFolderLogo() {
		return userFolderLogo;
	}
	public void setUserFolderLogo(String userFolderLogo) {
		this.userFolderLogo = userFolderLogo;
	}
	public String getShopFolderLogo() {
		return shopFolderLogo;
	}
	public void setShopFolderLogo(String shopFolderLogo) {
		this.shopFolderLogo = shopFolderLogo;
	}
	public String getProductFolderMaster() {
		return productFolderMaster;
	}
	public void setProductFolderMaster(String productFolderMaster) {
		this.productFolderMaster = productFolderMaster;
	}
	public String getProductFolderDetail() {
		return productFolderDetail;
	}
	public void setProductFolderDetail(String productFolderDetail) {
		this.productFolderDetail = productFolderDetail;
	}
	
}
package com.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 
 * @Author Ruan
 * 
 * 默认变量
 * 
 */

@Component
@ConfigurationProperties
//@PropertySource("classpath:config/default.properties")//在resources下创建config文件夹
//@PropertySource("classpath:default.properties")
@PropertySource("classpath:default-${spring.profiles.active}.properties")
public final class DefaultProperties {
	
	private String domain;																							//域名
	private String contextUrl;																						//上下文
	private String staticServerManage;																				//静态文件服务器
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getContextUrl() {
		return contextUrl;
	}
	public void setContextUrl(String contextUrl) {
		this.contextUrl = contextUrl;
	}
	public String getStaticServerManage() {
		return staticServerManage;
	}
	public void setStaticServerManage(String staticServerManage) {
		this.staticServerManage = staticServerManage;
	}
	
}
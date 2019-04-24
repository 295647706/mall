package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.common.DefaultProperties;
import com.common.ImageProperties;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 
 * @Author Ruan
 * 
 */

@SpringBootApplication
@EntityScan(basePackages = {"com.model"})
@EnableJpaRepositories(basePackages = {"com.dao"})
@MapperScan(basePackages = {"com.dao.*"})
@ComponentScan(basePackages = {"com.dao", "com.service", "com.api"})
@EnableConfigurationProperties({DefaultProperties.class, ImageProperties.class})
@EnableScheduling
public class InitApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(InitApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InitApplication.class);
	}

}
package com.xoftix.xdms.workflow.activiti.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivitiDataBaseConfig {

	@Value("${activiti.database.url}")
	private String url;

	@Value("${activiti.database.user}")
	private String user;

	@Value("${activiti.database.password}")
	private String password;

	@Value("${activiti.database.driver}")
	private String driver;

	@Bean
	public DataSource database() {
		// TODO change the connection url & details for different DB
		return DataSourceBuilder.create().url(url).username(user).password(password).driverClassName(driver).build();
	}

}

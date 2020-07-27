package com.xoftix.xdms.workflow.activiti.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Value("${webservices.mailservice.sendemail}")
	private String urlSendEmail;

	@Value("${webservices.mailservice.status}")
	private String urlStatusMail;

	@Value("${webservices.mailservice.email}")
	private String email;

	public String getUrlSendEmail() {
		return urlSendEmail;
	}

	public String getUrlStatusMail() {
		return urlStatusMail;
	}

	public String getEmail() {
		return email;
	}

}

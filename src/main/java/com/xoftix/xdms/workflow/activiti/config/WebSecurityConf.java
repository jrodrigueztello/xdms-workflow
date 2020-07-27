package com.xoftix.xdms.workflow.activiti.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConf extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().anonymous().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll();
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration authConfig = new CorsConfiguration();
		authConfig.setAllowCredentials(true);
		authConfig.addAllowedOrigin("*");
		authConfig.addAllowedHeader("Authorization");
		authConfig.addAllowedHeader("Content-Type");
		authConfig.addAllowedHeader("Accept");
		authConfig.addAllowedMethod("POST");
		authConfig.addAllowedMethod("GET");
		authConfig.addAllowedMethod("DELETE");
		authConfig.addAllowedMethod("PUT");
		authConfig.addAllowedMethod("OPTIONS");
		authConfig.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", authConfig);

		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

}

package com.xoftix.xdms.workflow.activiti.config;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import springfox.documentation.service.Tag;

@Component
public class SwaggerDocsConfig {

	List<Tag> tags = new LinkedList<>();

	@Bean("tags")
	public List<Tag> getTags() {
		return tags;
	}
}

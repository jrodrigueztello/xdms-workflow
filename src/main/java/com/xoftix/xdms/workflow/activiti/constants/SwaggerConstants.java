package com.xoftix.xdms.workflow.activiti.constants;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.service.Tag;

@DependsOn("tags")
@Configuration
@PropertySource("classpath:lang/lang-es.properties")
public class SwaggerConstants {

	// INFO DEL MS
	public static final String VERSION = "1.0.0";
	public static final String TERMS_URL = "http://xoftix.com";
	public static final String LICENCE_URL = "";
	public static final String LICENCE = "";
	public static final String CONTACT_NAME = "";
	public static final String CONTACT_URL = "";
	public static final String CONTACT_EMAIL = "";
	public static final String PACKAGE_CONTROLLER = "com.xoftix";

	// COMMON
	public static final String API_VALUE_COMMON = "Common";
	public static final String API_TAGS_COMMON = "Common";

	@Autowired
	private List<Tag> tags;

	@Value("${swagger.BPMN_DESCRIPTION}")
	private String BPMN_DESCRIPTION;
	public static final String BPMN_TAG_KEY = "Bpmn";

	@Value("${swagger.PROCESS_DESCRIPTION}")
	private String PROCESS_DESCRIPTION;
	public static final String PROCESS_TAG_KEY = "Process";

	@Value("${swagger.DM_PROCESS_DESCRIPTION}")
	private String DM_PROCESS_DESCRIPTION;
	public static final String DM_PROCESS_TAG_KEY = "Dm Process";

	@PostConstruct
	public void agregarTags() {
		tags.add(new Tag(PROCESS_TAG_KEY, PROCESS_DESCRIPTION));
		tags.add(new Tag(BPMN_TAG_KEY, BPMN_DESCRIPTION));
		tags.add(new Tag(DM_PROCESS_TAG_KEY, DM_PROCESS_DESCRIPTION));
	}

}

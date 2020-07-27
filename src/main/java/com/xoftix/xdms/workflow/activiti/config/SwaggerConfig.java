package com.xoftix.xdms.workflow.activiti.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

import com.xoftix.xdms.workflow.activiti.constants.SwaggerConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@DependsOn("tags")
@EnableSwagger2WebMvc
@Configuration
@PropertySource("classpath:lang/lang-es.properties")
public class SwaggerConfig {

	@Value("${swagger.title}")
	private String title;

	@Value("${swagger.description}")
	private String description;

	@Autowired
	private List<Tag> tags;

	@Bean
	public Docket api() {
		Boolean hasTags = tags != null && tags.size() > 0;
		Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(SwaggerConstants.PACKAGE_CONTROLLER)).paths(regex("/.*"))
				.build().apiInfo(apiInfo());

		if (hasTags) {
			docket.tags(tags.get(0), tags.toArray(new Tag[tags.size()]));
		}

		return docket;
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact(SwaggerConstants.CONTACT_NAME, SwaggerConstants.CONTACT_URL,
				SwaggerConstants.CONTACT_EMAIL);
		return new ApiInfoBuilder().title(title).description(description).termsOfServiceUrl(SwaggerConstants.TERMS_URL)
				.contact(contact).license(SwaggerConstants.LICENCE).licenseUrl(SwaggerConstants.LICENCE_URL)
				.version(SwaggerConstants.VERSION).build();
	}
}

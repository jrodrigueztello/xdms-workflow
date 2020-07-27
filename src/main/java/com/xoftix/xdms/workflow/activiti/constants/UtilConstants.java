package com.xoftix.xdms.workflow.activiti.constants;

public abstract class UtilConstants {
	
	private static final String PAQUETE_BASE = "com.xoftix.%s";
	
	private static final String WEBCONTROLLER_PACKAGE = "com.xoftix.%s.webcontroller";
	
	public static final Integer MAX_SIZE = 10;
	
	public static final String WILDCARD = "%";
	
	private static final String SCHEMA = "zero_%s";
	
	public static final String SLASH = "/";
	
	public static final String OPEN_BRACKET = "[";
	
	public static final String CLOSED_BRACKET = "]";
	
	public static final String PAQUETE_BASE(String project) {
		return String.format(PAQUETE_BASE, project);
	}
	
	public static final String WEBCONTROLLER_PACKAGE(String project) {
		return String.format(WEBCONTROLLER_PACKAGE, project);
	}
	
	public static final String SCHEMA(String project) {
		return String.format(SCHEMA, project);
	}
}


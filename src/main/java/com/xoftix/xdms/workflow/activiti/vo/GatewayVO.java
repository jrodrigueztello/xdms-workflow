package com.xoftix.xdms.workflow.activiti.vo;

public class GatewayVO {
	private String id;
	private String name;
	private String code;
	
	
	
	public GatewayVO(String id, String name, String code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}
	
	public GatewayVO() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	

	
	
}

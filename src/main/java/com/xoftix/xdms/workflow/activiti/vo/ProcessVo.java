package com.xoftix.xdms.workflow.activiti.vo;

import java.util.List;

public class ProcessVo {

	private String id;
	private String name;
	private String key;
	private List<TaskVo> taskVo;
	private List<SecuenceVo> secuenceVo;
	private List<GatewayVO> gatewayVo;
	private String nameModel;
	private String nameXml;
	private String descriptionModel;

	public ProcessVo() {

	}

	public ProcessVo(String key, String name) {
		super();
		this.key = key;
		this.name = name;
	}

	public ProcessVo(String id, String key, String name) {
		super();
		this.id = id;
		this.key = key;
		this.name = name;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<TaskVo> getTaskVo() {
		return taskVo;
	}

	public void setTaskVo(List<TaskVo> taskVo) {
		this.taskVo = taskVo;
	}

	public List<SecuenceVo> getSecuenceVo() {
		return secuenceVo;
	}

	public void setSecuenceVo(List<SecuenceVo> secuenceVo) {
		this.secuenceVo = secuenceVo;
	}
	
	

	public List<GatewayVO> getGatewayVo() {
		return gatewayVo;
	}

	public void setGatewayVo(List<GatewayVO> gatewayVo) {
		this.gatewayVo = gatewayVo;
	}

	public String getNameModel() {
		return nameModel;
	}

	public void setNameModel(String nameModel) {
		this.nameModel = nameModel;
	}

	public String getNameXml() {
		return nameXml;
	}

	public void setNameXml(String nameXml) {
		this.nameXml = nameXml;
	}

	public String getDescriptionModel() {
		return descriptionModel;
	}

	public void setDescriptionModel(String descriptionModel) {
		this.descriptionModel = descriptionModel;
	}

}

package com.xoftix.xdms.workflow.activiti.vo;

import java.util.List;

public class StartProcessVo {

	private String processDefinitionKey;
	private List<ParamVo> variables;

	public StartProcessVo() {

	}

	public StartProcessVo(String processDefinitionKey, List<ParamVo> variables) {
		super();
		this.processDefinitionKey = processDefinitionKey;
		this.variables = variables;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String key) {
		this.processDefinitionKey = key;
	}

	public List<ParamVo> getVariables() {
		return variables;
	}

	public void setVariables(List<ParamVo> variables) {
		this.variables = variables;
	}

}

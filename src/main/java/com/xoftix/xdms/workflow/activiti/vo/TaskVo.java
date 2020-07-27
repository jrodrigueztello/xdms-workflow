package com.xoftix.xdms.workflow.activiti.vo;

public class TaskVo {

	private String id;
	private String name;
	private String description;
	private String assignee;
	private String process;
	private String processInstanceId;
	private String code;

	public TaskVo() {
		// TODO Auto-generated constructor stub
	}

	public TaskVo(String id, String name, String assignee) {
		super();
		this.id = id;
		this.name = name;
		this.assignee = assignee;
	}

	public TaskVo(String id, String name, String description, String assignee, String process,
			String processInstaceId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.assignee = assignee;
		this.process = process;
		this.processInstanceId = processInstaceId;
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

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String codigo) {
		this.code = codigo;
	}

}

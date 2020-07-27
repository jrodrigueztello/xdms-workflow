package com.xoftix.xdms.workflow.activiti.vo;

import java.util.ArrayList;
import java.util.List;

import com.xoftix.xdms.workflow.activiti.tipo.ActivitiState;

public class ProcessInstanceVo {

	private String id;
	private List<TaskVo> tasks;
	private Character state;

	public ProcessInstanceVo() {

	}

	public ProcessInstanceVo(String id) {
		this.id = id;
		this.tasks = new ArrayList<TaskVo>();

	}

	public ProcessInstanceVo(String id, List<TaskVo> tasks) {
		this.id = id;
		this.tasks = tasks;

	}

	public ProcessInstanceVo(String id, ActivitiState state) {
		this.id = id;
		this.setState(state);
		this.tasks = new ArrayList<TaskVo>();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TaskVo> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskVo> tasks) {
		this.tasks = tasks;
	}

	public ActivitiState getState() {
		return ActivitiState.searchState(state);
	}

	public void setState(ActivitiState state) {
		this.state = state != null ? state.getValue() : null;
	}

}

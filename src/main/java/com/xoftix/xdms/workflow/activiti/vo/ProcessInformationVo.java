package com.xoftix.xdms.workflow.activiti.vo;

import java.util.List;

public class ProcessInformationVo {

	private ProcessVo process;
	private List<ProcessInstanceVo> instance;

	public ProcessInformationVo() {

	}

	public ProcessInformationVo(ProcessVo process, List<ProcessInstanceVo> instance) {
		super();
		this.process = process;
		this.instance = instance;
	}

	public ProcessVo getProcess() {
		return process;
	}

	public void setProcess(ProcessVo process) {
		this.process = process;
	}

	public List<ProcessInstanceVo> getInstance() {
		return instance;
	}

	public void setInstance(List<ProcessInstanceVo> instance) {
		this.instance = instance;
	}

}

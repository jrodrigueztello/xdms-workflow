package com.xoftix.xdms.workflow.activiti.commons;

import java.io.Serializable;

public abstract class CommonBuilder implements Serializable {
	private static final long serialVersionUID = -5573988559789204386L;
	protected Integer page;
	protected Integer size;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}

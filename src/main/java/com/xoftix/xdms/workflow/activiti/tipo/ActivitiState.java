package com.xoftix.xdms.workflow.activiti.tipo;

public enum ActivitiState {

	RUNNING('R'), SUSPENDED('S'), FINISHED('F');

	private Character value;

	private ActivitiState(Character value) {
		this.value = value;
	}

	public Character getValue() {
		return value;
	}

	public void setValue(Character value) {
		this.value = value;
	}

	public static ActivitiState searchState(Character state) {
		for (ActivitiState as : ActivitiState.values()) {
			if (as.getValue().equals(state)) {
				return as;
			}
		}
		return null;
	}
}

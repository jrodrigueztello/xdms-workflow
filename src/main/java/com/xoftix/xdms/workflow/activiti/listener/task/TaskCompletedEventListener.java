package com.xoftix.xdms.workflow.activiti.listener.task;

import java.util.Date;

import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.events.TaskCompletedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoftix.xdms.workflow.activiti.service.ActiveMqMessageService;
import com.xoftix.xdms.workflow.activiti.tipo.MessageEventType;
import com.xoftix.xdms.workflow.activiti.vo.ActiveMqMessageVo;

@Service
public class TaskCompletedEventListener implements TaskRuntimeEventListener<TaskCompletedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(TaskCompletedEventListener.class);

	private TaskCompletedEvent event;

	@Autowired
	private ActiveMqMessageService messageService;

	@Override
	public void onEvent(TaskCompletedEvent event) {
		Task task = event.getEntity();
		logger.info("task " + task.getId() + " was completed");
		this.event = event;
		messageService.sendMessage(
				new ActiveMqMessageVo(MessageEventType.TASK_COMPLETED, new Date().getTime(), task).toString());

	}

	public TaskCompletedEvent getEvent() {
		return event;
	}

	public void setEvent(TaskCompletedEvent event) {
		this.event = event;
	}

}

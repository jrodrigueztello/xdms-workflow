package com.xoftix.xdms.workflow.activiti.listener.task;

import java.util.Date;

import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.events.TaskCreatedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoftix.xdms.workflow.activiti.service.ActiveMqMessageService;
import com.xoftix.xdms.workflow.activiti.tipo.MessageEventType;
import com.xoftix.xdms.workflow.activiti.vo.ActiveMqMessageVo;

@Service
public class TaskCreatedEventListener implements TaskRuntimeEventListener<TaskCreatedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(TaskCreatedEventListener.class);

	private TaskCreatedEvent event;

	@Autowired
	private ActiveMqMessageService messageService;

	@Override
	public void onEvent(TaskCreatedEvent event) {
		Task task = event.getEntity();
		logger.info("Task " + task.getId() + " was created");
		this.event = event;
		messageService.sendMessage(
				new ActiveMqMessageVo(MessageEventType.TASK_CREATED, new Date().getTime(), task).toString());
	}

	public TaskCreatedEvent getEvent() {
		return event;
	}

	public void setEvent(TaskCreatedEvent event) {
		this.event = event;
	}
}

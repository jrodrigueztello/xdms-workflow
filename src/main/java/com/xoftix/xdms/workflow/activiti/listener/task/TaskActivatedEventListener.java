package com.xoftix.xdms.workflow.activiti.listener.task;

import java.util.Date;

import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.events.TaskActivatedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoftix.xdms.workflow.activiti.service.ActiveMqMessageService;
import com.xoftix.xdms.workflow.activiti.tipo.MessageEventType;
import com.xoftix.xdms.workflow.activiti.vo.ActiveMqMessageVo;

@Service
public class TaskActivatedEventListener implements TaskRuntimeEventListener<TaskActivatedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(TaskActivatedEventListener.class);

	private TaskActivatedEvent event;

	@Autowired
	private ActiveMqMessageService messageService;

	@Override
	public void onEvent(TaskActivatedEvent event) {
		Task task = event.getEntity();
		logger.info("task " + task.getId() + " was activated");
		this.event = event;
		messageService.sendMessage(
				new ActiveMqMessageVo(MessageEventType.TASK_ACTIVATED, new Date().getTime(), task).toString());
	}

	public TaskActivatedEvent getEvent() {
		return event;
	}

	public void setEvent(TaskActivatedEvent event) {
		this.event = event;
	}

}

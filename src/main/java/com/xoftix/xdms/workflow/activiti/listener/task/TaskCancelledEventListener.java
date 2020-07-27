package com.xoftix.xdms.workflow.activiti.listener.task;

import java.util.Date;

import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.events.TaskCancelledEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoftix.xdms.workflow.activiti.service.ActiveMqMessageService;
import com.xoftix.xdms.workflow.activiti.tipo.MessageEventType;
import com.xoftix.xdms.workflow.activiti.vo.ActiveMqMessageVo;

@Service
public class TaskCancelledEventListener implements TaskRuntimeEventListener<TaskCancelledEvent> {

	private static final Logger logger = LoggerFactory.getLogger(TaskCancelledEventListener.class);

	private TaskCancelledEvent event;

	@Autowired
	private ActiveMqMessageService messageService;

	@Override
	public void onEvent(TaskCancelledEvent event) {
		Task task = event.getEntity();
		logger.info("Task " + task.getId() + " was cancelled");
		this.event = event;
		messageService.sendMessage(
				new ActiveMqMessageVo(MessageEventType.TASK_CANCELLED, new Date().getTime(), task).toString());
	}

	public TaskCancelledEvent getEvent() {
		return event;
	}

	public void setEvent(TaskCancelledEvent event) {
		this.event = event;
	}

}

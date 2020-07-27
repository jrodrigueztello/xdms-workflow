package com.xoftix.xdms.workflow.activiti.listener.task;

import java.util.Date;

import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.events.TaskAssignedEvent;
import org.activiti.api.task.runtime.events.listener.TaskRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoftix.xdms.workflow.activiti.service.ActiveMqMessageService;
import com.xoftix.xdms.workflow.activiti.service.ProcessService;
import com.xoftix.xdms.workflow.activiti.tipo.MessageEventType;
import com.xoftix.xdms.workflow.activiti.vo.ActiveMqMessageVo;

@Service
public class TaskAssignedEventListener implements TaskRuntimeEventListener<TaskAssignedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(TaskAssignedEventListener.class);

	private TaskAssignedEvent event;

	@Autowired
	private ActiveMqMessageService messageService;
	
	@Autowired
	private ProcessService processService;

	@Override
	public void onEvent(TaskAssignedEvent event) {
		Task task = event.getEntity();
		logger.info("Task " + task.getId() + " was assigned to " + task.getAssignee());
		this.event = event;
		messageService.sendMessage(
				new ActiveMqMessageVo(MessageEventType.TASK_ASSIGNED, new Date().getTime(), task).toString());
		if(event.getEntity().getAssignee().equals("Sistema")) {
			processService.completeTask(task.getId(), null);
		}
	}

	public TaskAssignedEvent getEvent() {
		return event;
	}

	public void setEvent(TaskAssignedEvent event) {
		this.event = event;
	}

}

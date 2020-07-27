package com.xoftix.xdms.workflow.activiti.listener.process;

import java.util.Date;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.runtime.events.ProcessCompletedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoftix.xdms.workflow.activiti.service.ActiveMqMessageService;
import com.xoftix.xdms.workflow.activiti.tipo.MessageEventType;
import com.xoftix.xdms.workflow.activiti.vo.ActiveMqMessageVo;

@Service
public class ProcessCompletedEventListener implements ProcessRuntimeEventListener<ProcessCompletedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(ProcessCompletedEventListener.class);

	private ProcessCompletedEvent event;

	@Autowired
	private ActiveMqMessageService messageService;

	@Override
	public void onEvent(ProcessCompletedEvent event) {
		ProcessInstance processInstance = event.getEntity();
		logger.info("Process instance " + processInstance.getId() + " was completed");
		this.event = event;
		messageService.sendMessage(
				new ActiveMqMessageVo(MessageEventType.PROCESS_COMPLETED, new Date().getTime(), processInstance)
						.toString());
	}

	public ProcessCompletedEvent getEvent() {
		return event;
	}

	public void setEvent(ProcessCompletedEvent event) {
		this.event = event;
	}
}

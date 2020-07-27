package com.xoftix.xdms.workflow.activiti.listener.process;

import java.util.Date;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.runtime.events.ProcessSuspendedEvent;
import org.activiti.api.process.runtime.events.listener.ProcessRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoftix.xdms.workflow.activiti.service.ActiveMqMessageService;
import com.xoftix.xdms.workflow.activiti.tipo.MessageEventType;
import com.xoftix.xdms.workflow.activiti.vo.ActiveMqMessageVo;

@Service
public class ProcessSuspendedEventListener implements ProcessRuntimeEventListener<ProcessSuspendedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(ProcessSuspendedEventListener.class);

	private ProcessSuspendedEvent event;

	@Autowired
	private ActiveMqMessageService messageService;

	@Override
	public void onEvent(ProcessSuspendedEvent event) {
		ProcessInstance processInstance = event.getEntity();
		logger.info("Process instance " + processInstance.getId() + " was suspended");
		this.event = event;

		messageService.sendMessage(
				new ActiveMqMessageVo(MessageEventType.PROCESS_SUSPENDED, new Date().getTime(), processInstance)
						.toString());
	}

	public ProcessSuspendedEvent getEvent() {
		return event;
	}

	public void setEvent(ProcessSuspendedEvent event) {
		this.event = event;
	}
}

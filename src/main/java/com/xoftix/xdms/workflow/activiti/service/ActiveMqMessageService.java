package com.xoftix.xdms.workflow.activiti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActiveMqMessageService {

	@Value("${xdms.topic}")
	private String topic;

	@Autowired
	private JmsTemplate jmsTemplate;

	public void sendMessage(String message) {
		jmsTemplate.convertAndSend(topic, message);
	}
}

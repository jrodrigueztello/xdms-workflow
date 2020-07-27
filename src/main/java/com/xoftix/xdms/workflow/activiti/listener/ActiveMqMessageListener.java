package com.xoftix.xdms.workflow.activiti.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqMessageListener {

	private static final Logger logger = LoggerFactory.getLogger(ActiveMqMessageListener.class);

	@JmsListener(destination = "${xdms.topic}")
	public void messageReceived(String message) {
		logger.info("received message='{}'", message);
	}

}

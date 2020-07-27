package com.xoftix.xdms.workflow.activiti.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoftix.xdms.workflow.activiti.tipo.MessageEventType;

public class ActiveMqMessageVo {

	private MessageEventType eventType;
	private long timestamp;
	private Object entity;

	public ActiveMqMessageVo() {
		super();
	}

	public ActiveMqMessageVo(MessageEventType eventType, long timestamp, Object entity) {
		super();
		this.eventType = eventType;
		this.timestamp = timestamp;
		this.entity = entity;
	}

	public MessageEventType getEventType() {
		return eventType;
	}

	public void setEventType(MessageEventType eventType) {
		this.eventType = eventType;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return super.toString();
		}
	}

}

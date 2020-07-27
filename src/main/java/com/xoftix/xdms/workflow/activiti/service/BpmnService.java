package com.xoftix.xdms.workflow.activiti.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xoftix.xdms.workflow.activiti.vo.GatewayVO;
import com.xoftix.xdms.workflow.activiti.vo.ProcessVo;
import com.xoftix.xdms.workflow.activiti.vo.SecuenceVo;
import com.xoftix.xdms.workflow.activiti.vo.TaskVo;

@Service
@Transactional
public class BpmnService {

	@Autowired
	private ProcessEngine processEngine;

	String urlPath = "src/main/resources/processes";

	public Process createAdaptiveProcessInstance(ProcessVo processVo) throws Exception {

		Process process = getProcess(processVo);

		// 2. implementar un modelo de proceso básico
		ProcessDefinition processDefinitions = getProcessDefinition(processVo, process);

		exportProcessDefinition(processDefinitions.getId(), urlPath, processVo.getNameModel() + ".bpmn",
				processVo.getNameXml());

		return process;
	}

	private ProcessDefinition getProcessDefinition(ProcessVo processVo) {
		return getProcessDefinition(processVo, getProcess(processVo));
	}

	private ProcessDefinition getProcessDefinition(ProcessVo processVo, Process process) {
		Deployment deploy = deployModelWithProcess(process, processVo.getNameModel(), processVo.getDescriptionModel());

		ProcessDefinition processDefinitions = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.deploymentId(deploy.getId()).singleResult();
		return processDefinitions;
	}

	public Process getProcess(ProcessVo processVo) {
		// 1. Construye el modelo básico
		Process process = new Process();
		process.setId(processVo.getKey());
		process.setName(processVo.getName());
		process.addFlowElement(createStartEvent());
		process.addFlowElement(createEndEvent());

		for (TaskVo tarea : processVo.getTaskVo()) {
			process.addFlowElement(createUserTask(tarea.getCode(), tarea.getName(), tarea.getAssignee()));
		}

		for (SecuenceVo secuencia : processVo.getSecuenceVo()) {
			if(secuencia.getConditionExpression()!=null||!secuencia.getConditionExpression().equals("")) {
				process.addFlowElement(createSequenceFlow(secuencia.getCode(), secuencia.getStart(), secuencia.getEnd(), secuencia.getConditionExpression()));
			}else {
				process.addFlowElement(createSequenceFlow(secuencia.getCode(), secuencia.getStart(), secuencia.getEnd(),null));
			}
		}
		
		for(GatewayVO compuerta: processVo.getGatewayVo()) {
			ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
			 exclusiveGateway.setId(compuerta.getId());
			 process.addFlowElement(exclusiveGateway);
		}
		
		return process;
	}

	private Deployment deployModelWithProcess(Process process, String nameModel, String descriptionModel) {
		BpmnModel model = new BpmnModel();
		model.addProcess(process);
		return processEngine.getRepositoryService().createDeployment().addBpmnModel(nameModel + ".bpmn", model)
				.name(descriptionModel).deploy();
	}

	public String processToXml(ProcessVo processVo) throws Exception {
		BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
		BpmnModel model = getBpmnModel(processVo);
		String bpmn20Xml = new String(bpmnXMLConverter.convertToXML(model), "UTF-8");
		return bpmn20Xml;
	}

	public File getXmlFile(ProcessVo processVo) throws Exception {
		String xml = processToXml(processVo);
		File file = File.createTempFile(processVo.getName(), null);
		FileUtils.writeStringToFile(file, xml, "UTF-8");
		return file;
	}

	public File getImgFile(ProcessVo processVo) throws Exception {

		BpmnModel model = getBpmnModel(processVo);

		ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
		InputStream inputStream = generator.generateDiagram(model, new ArrayList<String>());
		File file = File.createTempFile(processVo.getName(), null);
		FileUtils.copyInputStreamToFile(inputStream, file);
		return file;
	}

	private BpmnModel getBpmnModel(ProcessVo processVo) {
		BpmnModel model = processEngine.getRepositoryService().getBpmnModel(getProcessDefinition(processVo).getId());
		new BpmnAutoLayout(model).execute();
		return model;
	}

	private void exportProcessDefinition(String processDefinitionId, String namePrefix, String resourceName,
			String nameXml) throws IOException {
		ProcessDefinition processDefinition = this.processEngine.getRepositoryService()
				.getProcessDefinition(processDefinitionId);
		InputStream processBpmn = processEngine.getRepositoryService()
				.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		FileUtils.copyInputStreamToFile(processBpmn, new File(namePrefix + nameXml + ".bpmn20.xml"));

	}

	public UserTask createUserTask(String id, String name, String assignee) {
		UserTask userTask = new UserTask();
		userTask.setName(name);
		userTask.setId(id);
		userTask.setAssignee(assignee);
		return userTask;
	}

	public SequenceFlow createSequenceFlow(String id, String from, String to, String condicion) {
		SequenceFlow flow = new SequenceFlow();
		flow.setId(id);
		flow.setSourceRef(from);
		flow.setTargetRef(to);
		if(condicion!=null) {
			flow.setConditionExpression(condicion);
		}
		return flow;
	}

	public StartEvent createStartEvent() {
		StartEvent startEvent = new StartEvent();
		startEvent.setId("start");
		return startEvent;
	}

	public EndEvent createEndEvent() {

		EndEvent endEvent = new EndEvent();
		endEvent.setId("end");
		return endEvent;
	}

}

package com.xoftix.xdms.workflow.activiti.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoftix.xdms.workflow.activiti.dto.DmProcess;
import com.xoftix.xdms.workflow.activiti.repository.DmProcessRepository;
import com.xoftix.xdms.workflow.activiti.tipo.ActivitiState;
import com.xoftix.xdms.workflow.activiti.vo.ParamVo;
import com.xoftix.xdms.workflow.activiti.vo.ProcessInformationVo;
import com.xoftix.xdms.workflow.activiti.vo.ProcessInstanceVo;
import com.xoftix.xdms.workflow.activiti.vo.ProcessVo;
import com.xoftix.xdms.workflow.activiti.vo.StartProcessVo;
import com.xoftix.xdms.workflow.activiti.vo.TaskVo;

@Service
public class ProcessService {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private DmProcessRepository repository;

	@Autowired
	private BpmnService bpmnService;

	// start the process and set user as variable
	public ProcessInformationVo startTheProcess(StartProcessVo params) {

		Map<String, Object> variables = params.getVariables().stream()
				.collect(Collectors.toMap(ParamVo::getKey, ParamVo::getValue));

		ProcessInstance instance = runtimeService.startProcessInstanceByKey(params.getProcessDefinitionKey(),
				variables);

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(runtimeService.createProcessInstanceQuery().processInstanceId(instance.getId())
						.singleResult().getProcessDefinitionKey())
				.singleResult();

		return new ProcessInformationVo(new ProcessVo(processDefinition.getKey(), processDefinition.getName()),
				Arrays.asList(new ProcessInstanceVo(instance.getId())));
	}

	// fetching process information
	public List<ProcessVo> availableProcesses() {

		List<ProcessDefinition> process = repositoryService.createProcessDefinitionQuery().list();
		List<ProcessVo> processInfo = new ArrayList<ProcessVo>();
		ProcessVo item = null;

		for (ProcessDefinition processDefinition : process) {
			item = new ProcessVo(processDefinition.getId(), processDefinition.getKey(), processDefinition.getName());
			processInfo.add(item);
		}

		return processInfo;
	}

	// fetch process instance information from the given process definition key
	public ProcessInformationVo processInformation(String processDefinitionKey) {

		ProcessInformationVo processInfo = new ProcessInformationVo();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).list().get(0);

		processInfo.setProcess(
				new ProcessVo(processDefinition.getId(), processDefinition.getKey(), processDefinition.getName()));

		processInfo.setInstance(runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey)
				.list().stream().map(instance -> {
					ProcessInstanceVo instanceVo = new ProcessInstanceVo(instance.getId());
					instanceVo.setState(getState(instance.getId()));
					instanceVo.setTasks(taskService.createTaskQuery().processInstanceId(instance.getId()).list()
							.stream().map(task -> new TaskVo(task.getId(), task.getName(), task.getAssignee()))
							.collect(Collectors.toList()));
					return instanceVo;
				}).collect(Collectors.toList()));

		return processInfo;
	}

	// fetch task assigned to user
	public List<TaskVo> getTasks(String username) {

		List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).list();
		List<TaskVo> tasksInfo = tasks.stream()
				.map(task -> new TaskVo(task.getId(), task.getName(), task.getDescription(), task.getAssignee(),
						repositoryService.createProcessDefinitionQuery()
								.processDefinitionId(taskService.createTaskQuery().taskId(task.getId()).singleResult()
										.getProcessDefinitionId())
								.singleResult().getKey(),
						task.getProcessInstanceId()))
				.collect(Collectors.toList());

		return tasksInfo;
	}

	// complete the task
	public void completeTask(String taskId, Long opcion) {
		if(opcion!=null) {
			Map<String, Object> variables = new HashMap<>();
			  variables.put("value", opcion);
			taskService.complete(taskId,variables);
		}else {
			taskService.complete(taskId);
		}
	}

	public List<ProcessInstanceVo> history(String processDefinitionId) {

		List<HistoricActivityInstance> history = historyService.createHistoricActivityInstanceQuery()
				.processDefinitionId(processDefinitionId).orderByProcessInstanceId().desc().list();

		List<String> processInstanceIds = history.stream().map(item -> item.getProcessInstanceId()).distinct()
				.collect(Collectors.toList());

		List<ProcessInstanceVo> response = new ArrayList<>();
		ProcessInstanceVo instanceVo = null;
		for (String id : processInstanceIds) {
			instanceVo = new ProcessInstanceVo(id, getState(id));
			instanceVo.setTasks(history.stream().filter(item -> item.getProcessInstanceId().equals(id))
					.map(item -> new TaskVo(item.getTaskId(), item.getActivityName(), item.getAssignee()))
					.filter(task -> task.getId() != null).collect(Collectors.toList()));
			response.add(instanceVo);
		}

		return response;

	}

	private ActivitiState getState(String processInstanceId) {

		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();

		if (instance == null) {
			return ActivitiState.FINISHED;
		}

		return instance.isSuspended() ? ActivitiState.SUSPENDED : ActivitiState.RUNNING;
	}

	public String startProcessFromString(Long dmProcessId) throws Exception {
		Optional<DmProcess> optionalProcess = repository.findById(dmProcessId);

		if (!optionalProcess.isPresent()) {
			throw new Exception("Process with id " + dmProcessId + " not found");
		}

		DmProcess dmProcess = optionalProcess.get();
		ProcessVo processVo = new ObjectMapper().readValue(dmProcess.getContent(), ProcessVo.class);
		Process process = bpmnService.getProcess(processVo);
		BpmnModel model = new BpmnModel();
		model.addProcess(process);
		repositoryService.createDeployment().addBpmnModel("dynamic-model.bpmn", model)
				.name("Dynamic process deployment").deploy();

//	    ProcessInstance processInstance = activitiRule.getRuntimeService()
//	            .startProcessInstanceByKey("my-process");
//		String xmlString = bpmnService.processToXml(process);
//
//		repositoryService.createDeployment().addString(processVo.getKey(), xmlString).deploy();
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processVo.getKey());
		return instance.getProcessDefinitionKey();
	}

}

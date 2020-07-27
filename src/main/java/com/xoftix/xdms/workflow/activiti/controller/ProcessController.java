package com.xoftix.xdms.workflow.activiti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xoftix.xdms.workflow.activiti.constants.MappingConstants;
import com.xoftix.xdms.workflow.activiti.constants.SwaggerConstants;
import com.xoftix.xdms.workflow.activiti.service.ProcessService;
import com.xoftix.xdms.workflow.activiti.vo.ProcessInformationVo;
import com.xoftix.xdms.workflow.activiti.vo.ProcessInstanceVo;
import com.xoftix.xdms.workflow.activiti.vo.ProcessVo;
import com.xoftix.xdms.workflow.activiti.vo.StartProcessVo;
import com.xoftix.xdms.workflow.activiti.vo.TaskVo;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(MappingConstants.PROCESS)
@Api(tags = { SwaggerConstants.PROCESS_TAG_KEY })
public class ProcessController {

	@Autowired
	private ProcessService processService;

	// fetch available activiti process
	@GetMapping
	public List<ProcessVo> process() {
		return processService.availableProcesses();
	}

	// fetch process detail from the given process definition key
	@GetMapping("/info")
	public ProcessInformationVo info(@RequestParam() String processDefinitionKey) {
		return processService.processInformation(processDefinitionKey);
	}

	// start the activiti process engine and set user to perform the task
	@PostMapping("/start")
	public ProcessInformationVo startProcessInstance(@RequestBody StartProcessVo params) {
		return processService.startTheProcess(params);
	}

	// Retrieve the tasks assigned to an user
	@GetMapping("/tasks")
	public List<TaskVo> getTasks(@RequestParam String username) {
		return processService.getTasks(username);
	}

	// Complete the task by their ID
	@GetMapping("/completetask")
	public String completeTask(@RequestParam String taskId, @RequestParam(name = "opcion", required = false) Long opcion) {
		processService.completeTask(taskId, opcion);
		return "Task with id " + taskId + " has been completed!";
	}

	@GetMapping("history")
	public List<ProcessInstanceVo> history(@RequestParam() String processDefinitionId) {
		return processService.history(processDefinitionId);
	}

	@PostMapping("start-from-string/{id}")
	public String startProcessFromString(@PathVariable(MappingConstants.PATH_ID) Long id) throws Exception {
		return processService.startProcessFromString(id);
	}

}

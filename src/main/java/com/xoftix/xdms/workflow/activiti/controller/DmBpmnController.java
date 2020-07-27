package com.xoftix.xdms.workflow.activiti.controller;

import java.io.File;
import java.io.FileInputStream;

import org.activiti.bpmn.model.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xoftix.xdms.workflow.activiti.constants.MappingConstants;
import com.xoftix.xdms.workflow.activiti.constants.SwaggerConstants;
import com.xoftix.xdms.workflow.activiti.service.BpmnService;
import com.xoftix.xdms.workflow.activiti.vo.ProcessVo;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(MappingConstants.BPMN)
@Api(tags = { SwaggerConstants.BPMN_TAG_KEY })
public class DmBpmnController {

	@Autowired
	private BpmnService xdmsBpmnService;

	@PostMapping("create-bpmn-model")
	private Process createBpmnModel(@RequestBody ProcessVo processVo) throws Exception {
		return xdmsBpmnService.createAdaptiveProcessInstance(processVo);
	}

	@PostMapping("get-file")
	private ResponseEntity<InputStreamResource> getXmlFile(@RequestBody ProcessVo processVo) throws Exception {
		// Process process = xdmsBpmnService.getProcess(processVo);
		File file = xdmsBpmnService.getXmlFile(processVo);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		String nameFile = processVo.getName() + ".bpmn20.xml";
		Long lengthFile = file.length();
		file.delete();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + nameFile)
				.contentType(MediaType.APPLICATION_ATOM_XML).contentLength(lengthFile).body(resource);
	}

	@PostMapping("get-string")
	private String getXmlString(@RequestBody ProcessVo processVo) throws Exception {
		// Process process = xdmsBpmnService.getProcess(processVo);
		return xdmsBpmnService.processToXml(processVo);
	}

	@PostMapping("get-image")
	private ResponseEntity<InputStreamResource> getXmlImg(@RequestBody ProcessVo processVo) throws Exception {
		// Process process = xdmsBpmnService.getProcess(processVo);
		File file = xdmsBpmnService.getImgFile(processVo);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		String nameFile = processVo.getName() + ".svg";
		Long lengthFile = file.length();
		file.delete();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + nameFile)
				.contentType(MediaType.APPLICATION_ATOM_XML).contentLength(lengthFile).body(resource);
	}

}

package com.xoftix.xdms.workflow.activiti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.xoftix.xdms.workflow.activiti.constants.MappingConstants;
import com.xoftix.xdms.workflow.activiti.constants.SwaggerConstants;
import com.xoftix.xdms.workflow.activiti.dto.DmProcess;
import com.xoftix.xdms.workflow.activiti.dto.builder.DmProcessBuilder;
import com.xoftix.xdms.workflow.activiti.service.DmProcessService;
import com.xoftix.xdms.workflow.activiti.specifiers.CommonSpecifications;
import com.xoftix.xdms.workflow.activiti.vo.DmProcessVo;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(MappingConstants.DM_PROCESS)
@Api(tags = { SwaggerConstants.DM_PROCESS_TAG_KEY })
public class DmProcessController {

	@Autowired
	private DmProcessService service;

	@GetMapping(MappingConstants.CONSULTAR_STANDARD)
	public DmProcessVo get(@PathVariable(MappingConstants.PATH_ID) Long id) throws Exception {
		return service.get(id);
	}

	@PostMapping
	public DmProcessVo save(@RequestBody(required = true) DmProcessVo dmProcessVo) throws JsonProcessingException {
		return service.save(dmProcessVo);
	}

	@PutMapping(MappingConstants.EDITAR_STANDARD)
	public DmProcessVo update(@PathVariable(MappingConstants.PATH_ID) Long id,
			@RequestBody(required = true) DmProcessVo dmProcessVo) throws JsonProcessingException {
		return service.update(dmProcessVo);
	}

	@DeleteMapping(MappingConstants.ELIMINAR_STANDARD)
	public void eliminar(@PathVariable(MappingConstants.PATH_ID) Long id) {
		service.desactivar(id);
	}
	
	@GetMapping(MappingConstants.LISTAR_STANDARD)
	public Page<DmProcess> listar(DmProcessBuilder dmProcessBuilder){
		return service.listar(new CommonSpecifications<DmProcess>(dmProcessBuilder));
	}
	
	@GetMapping("/consultar"+MappingConstants.CONSULTAR_CODIGO)
	public DmProcessVo consultarCodigo(@PathVariable(MappingConstants.CODIGO) String codigo) throws JsonMappingException, JsonProcessingException {
		return service.consultarCodigo(codigo);
	}

}

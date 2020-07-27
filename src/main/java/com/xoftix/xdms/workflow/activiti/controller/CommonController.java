package com.xoftix.xdms.workflow.activiti.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.xoftix.xdms.workflow.activiti.constants.MappingConstants;

@PropertySource("classpath:lang/lang-es.properties")
public abstract class CommonController<T, K> {

	@GetMapping(MappingConstants.CONSULTAR_STANDARD)
	public abstract T consultar(@PathVariable(MappingConstants.PATH_ID) K id);

	@PostMapping(MappingConstants.GUARDAR_STANDARD)
	public abstract T guardar(@RequestBody(required = true) T object);

	@PutMapping(MappingConstants.EDITAR_STANDARD)
	public abstract T editar(@PathVariable(MappingConstants.PATH_ID) K id, @RequestBody(required = true) T object);

	@DeleteMapping(MappingConstants.ELIMINAR_STANDARD)
	public abstract void eliminar(@PathVariable(MappingConstants.PATH_ID) K id);
}

package com.xoftix.xdms.workflow.activiti.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoftix.xdms.workflow.activiti.dto.DmProcess;
import com.xoftix.xdms.workflow.activiti.repository.DmProcessRepository;
import com.xoftix.xdms.workflow.activiti.specifiers.CommonSpecifications;
import com.xoftix.xdms.workflow.activiti.tipo.TipoEstado;
import com.xoftix.xdms.workflow.activiti.vo.DmProcessVo;
import com.xoftix.xdms.workflow.activiti.vo.ProcessVo;

@Service
public class DmProcessService extends CommonService<DmProcess, Long> {

	@Autowired
	private DmProcessRepository repository;

	public DmProcessService() {
		super.setRepository(repository);
	}

	public DmProcessVo get(Long id) throws Exception {
		DmProcess process = repository.findOneByIdAndEstado(id, TipoEstado.ACTIVO.getValor());

		if (process == null) {
			throw new Exception("Process with id " + id + " not found");
		}

		return new DmProcessVo(process.getId(), process.getName(), getProcessVoFromString(process.getContent()),
				process.getVersion(), process.getDocumentation());

	}

	public DmProcessVo save(DmProcessVo dmProcessVo) throws JsonProcessingException {
		DmProcess process = super.guardar(getDmProcessFromProcessVo(dmProcessVo));
		dmProcessVo.setId(process.getId());
		return dmProcessVo;
	}

	public DmProcessVo update(DmProcessVo dmProcessVo) throws JsonProcessingException {
		DmProcess process = super.actualizar(dmProcessVo.getId(), getDmProcessFromProcessVo(dmProcessVo));
		dmProcessVo.setId(process.getId());
		return dmProcessVo;
	}

	private DmProcess getDmProcessFromProcessVo(DmProcessVo dmProcessVo) throws JsonProcessingException {
		DmProcess process = new DmProcess(dmProcessVo.getCode(), dmProcessVo.getName(),
				objectToString(dmProcessVo.getContent()), dmProcessVo.getVersion(), dmProcessVo.getDocumentation());
		process.setId(dmProcessVo.getId());
		return process;
	}

	private ProcessVo getProcessVoFromString(String processVoString)
			throws JsonMappingException, JsonProcessingException {

		return new ObjectMapper().readValue(processVoString, ProcessVo.class);
	}

	private String objectToString(ProcessVo processVo) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(processVo);
	}
	
	public DmProcessVo consultarCodigo(String codigo) throws JsonMappingException, JsonProcessingException {
		DmProcess process = repository.findByCode(codigo);
		if (process == null) {
			return null;
		}else {
			return new DmProcessVo(process.getId(), process.getName(), getProcessVoFromString(process.getContent()),
					process.getVersion(), process.getDocumentation());
		}
	}
	
	public Page<DmProcess> listar(CommonSpecifications<DmProcess> parametroBusqueda){
		if (parametroBusqueda.getBuilder().getSize() != null && parametroBusqueda.getBuilder().getPage() != null) {
			return repository.findAll(parametroBusqueda.getSpecification(), PageRequest.of(parametroBusqueda.getBuilder().getPage(), parametroBusqueda.getBuilder().getSize()));
		}
		return repository.findAll(parametroBusqueda.getSpecification(),Pageable.unpaged());
	}

	public Page<DmProcessVo> listarVO(CommonSpecifications<DmProcess> parametroBusqueda){
		List<DmProcess> listaDmProcess = this.listar(parametroBusqueda).getContent();
		List<DmProcessVo> listaDmProcessVo = listaDmProcess.stream().map(procesoVO -> new DmProcessVo()).collect(Collectors.toList());
		return new PageImpl<DmProcessVo>(listaDmProcessVo);
	}
	
}

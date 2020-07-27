package com.xoftix.xdms.workflow.activiti.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoftix.xdms.workflow.activiti.dto.Dto;
import com.xoftix.xdms.workflow.activiti.repository.CommonRepository;
import com.xoftix.xdms.workflow.activiti.tipo.TipoEstado;

@Service
public abstract class CommonService<T, K> {

	private CommonRepository<T, K> repository;

	public CommonRepository<T, K> getRepository() {
		return repository;
	}

	@Autowired
	public void setRepository(CommonRepository<T, K> repository) {
		this.repository = repository;
	}

	public T guardar(T o) {
		return repository.save(o);
	}

	public T actualizar(K id, T o) {
		if (repository.existsById(id)) {
			return repository.save(o);
		}
		return null;
	}

	public void eliminar(K id) {
		repository.deleteById(id);
	}

	public void desactivar(K id) {
		T o = consultar(id);
		if (o != null) {
			if (o instanceof Dto) {
				((Dto) o).setEstado(TipoEstado.INACTIVO);
				guardar(o);
			}
		}
	}

	public T consultar(K id) {
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	public T consultarActivo(K id) {
		return repository.findOneByIdAndEstado(id, TipoEstado.ACTIVO.getValor());
	}

	public List<T> listar() {
		return repository.findAll();
	}

}

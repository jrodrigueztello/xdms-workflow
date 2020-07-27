package com.xoftix.xdms.workflow.activiti.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xoftix.xdms.workflow.activiti.tipo.TipoEstado;

@MappedSuperclass
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Dto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "estado", nullable = false, length = 1)
	Character estado = TipoEstado.ACTIVO.getValor();

	public Dto() {
	}

	public TipoEstado getEstado() {
		return TipoEstado.buscar(estado);
	}

	public void setEstado(TipoEstado estado) {
		this.estado = estado != null ? estado.getValor() : null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

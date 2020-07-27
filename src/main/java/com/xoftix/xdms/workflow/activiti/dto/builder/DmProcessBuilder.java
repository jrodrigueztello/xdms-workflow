package com.xoftix.xdms.workflow.activiti.dto.builder;

import com.xoftix.xdms.workflow.activiti.commons.CommonBuilder;
import com.xoftix.xdms.workflow.activiti.tipo.TipoEstado;
import com.xoftix.xdms.workflow.activiti.util.CampoFiltro;
import com.xoftix.xdms.workflow.activiti.util.Condicion;

public class DmProcessBuilder extends CommonBuilder{

	private static final long serialVersionUID = -8716695218994629316L;

	@CampoFiltro(campo = "codigo", condicion = Condicion.LIKE)
	private String codigo;

	@CampoFiltro(campo = "nombre", condicion = Condicion.LIKE)
	private String nombre;

	@CampoFiltro(campo = "estado", condicion = Condicion.IN)
	private TipoEstado[] listaEstados;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoEstado[] getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(TipoEstado[] listaEstados) {
		this.listaEstados = listaEstados;
	}
}

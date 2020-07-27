package com.xoftix.xdms.workflow.activiti.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dmt_proceso")
public class DmProcess extends Dto {

	private static final long serialVersionUID = 1L;

	@Column(name = "codigo", nullable = false, unique = true)
	private String code;

	@Column(name = "nombre")
	private String name;

	@Column(name = "contenido", columnDefinition = "TEXT")
	private String content;

	@Column(name = "version")
	private Integer version;

	@Column(name = "documentacion", columnDefinition = "TEXT")
	private String documentation;

	public DmProcess() {

	}

	public DmProcess(String code, String name, String content, Integer version, String documentation) {
		super();
		this.code = code;
		this.name = name;
		this.content = content;
		this.version = version;
		this.documentation = documentation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentacion) {
		this.documentation = documentacion;
	}

}

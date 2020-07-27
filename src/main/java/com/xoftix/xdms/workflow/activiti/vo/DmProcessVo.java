package com.xoftix.xdms.workflow.activiti.vo;

public class DmProcessVo {

	private Long id;
	private String code;
	private String name;
	private ProcessVo content;
	private Integer version;
	private String documentation;

	public DmProcessVo() {

	}

	public DmProcessVo(Long id, String name, ProcessVo content, Integer version, String documentation) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.version = version;
		this.documentation = documentation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public ProcessVo getContent() {
		return content;
	}

	public void setContent(ProcessVo content) {
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

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	@Override
	public String toString() {
		return "DmProcessVo [id=" + id + ", code=" + code + ", name=" + name + ", content=" + content + ", version="
				+ version + ", documentation=" + documentation + "]";
	}

}

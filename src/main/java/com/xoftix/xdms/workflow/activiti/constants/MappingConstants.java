package com.xoftix.xdms.workflow.activiti.constants;

public abstract class MappingConstants {

	public static final String GET_VERB = "GET-";
	public static final String POST_VERB = "POST-";
	public static final String PUT_VERB = "PUT-";
	public static final String DELETE_VERB = "DELETE-";
	public static final String PATH_ID = "id";
	public static final String PAGE = "page";
	public static final String SIZE = "size";
	public static final String CODIGO = "codigo";

	public static final String GUARDAR_STANDARD = "";
	public static final String EDITAR_STANDARD = "/{id}";
	public static final String CONSULTAR_STANDARD = "/{id}";
	public static final String ELIMINAR_STANDARD = "/{id}";
	public static final String LISTAR_STANDARD = "";
	public static final String LISTAR_SPECIFIC = "/listar-especifico";
	public static final String CONSULTAR_CODIGO = "/{codigo}";

	// COMMONS service
	public static final String REQUEST_COMMON = "/common";
	public static final String LISTAR_TIPOS = "/tipos";

	// WORKFLOW
	public static final String BPMN = "/bpmn";
	public static final String PROCESS = "/process";
	public static final String DM_PROCESS = "/dm-process";
}

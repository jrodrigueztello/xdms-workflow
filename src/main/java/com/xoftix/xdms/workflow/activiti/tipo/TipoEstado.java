package com.xoftix.xdms.workflow.activiti.tipo;

public enum TipoEstado {

	ACTIVO('A'),
	INACTIVO('I'),
	PENDIENTE('P'), 			// pending
	INICIADO('B'),  			// Beginning
	RECHAZADO('D'), 			// decline
	APROBADO('S'),  			// success
	ESPERANDO_APROBACION('W'), 	// waiting
	FINALIZADO('F');
	

	private Character valor;

	private TipoEstado(Character valor) {
		this.valor = valor;
	}

	public Character getValor() {
		return valor;
	}

	public void setEstado(Character valor) {
		this.valor = valor;
	}

	public static TipoEstado buscar(Character valor) {
		for (TipoEstado tipo : TipoEstado.values()) {
			if (tipo.getValor().equals(valor)) {
				return tipo;
			}
		}
		return null;
	}
}

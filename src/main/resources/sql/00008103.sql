CREATE TABLE dmt_proceso (
    id BIGSERIAL NOT NULL,
    codigo VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    estado CHARACTER(1) NOT NULL,
   	contenido TEXT NOT NULL,
	version int NOT NULL DEFAULT 1,
    documentacion TEXT
);

// Constrains
ALTER TABLE dmt_proceso ADD CONSTRAINT dmt_proceso_pk PRIMARY KEY (id);
ALTER TABLE dmt_proceso ADD CONSTRAINT dmt_proceso_u1 UNIQUE(codigo);

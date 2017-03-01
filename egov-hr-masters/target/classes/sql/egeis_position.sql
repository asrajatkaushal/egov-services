CREATE TABLE egeis_position (
	id BIGINT NOT NULL,
	name CHARACTER VARYING(100) NOT NULL,
	deptdesigId BIGINT NOT NULL,
	isPostOutsourced BOOLEAN NOT NULL,
	active BOOLEAN NOT NULL,
	tenantId CHARACTER VARYING(250) NOT NULL,

	CONSTRAINT pk_egeis_position PRIMARY KEY (id),
	CONSTRAINT uk_egeis_position_name UNIQUE KEY (name),
	CONSTRAINT fk_egeis_position_deptdesigId FOREIGN KEY (deptdesigId)
		REFERENCES egeis_departmentDesignation(id)
);

CREATE SEQUENCE seq_egeis_position INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
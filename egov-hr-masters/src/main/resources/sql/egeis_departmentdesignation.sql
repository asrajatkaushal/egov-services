CREATE TABLE egeis_departmentDesignation (
	id BIGINT NOT NULL,
	departmentId BIGINT NOT NULL,
	designationId BIGINT NOT NULL,
	tenantId CHARACTER VARYING(250) NOT NULL,

	CONSTRAINT pk_egeis_departmentDesignation PRIMARY KEY (id),
	CONSTRAINT fk_egeis_departmentDesignation_designationId FOREIGN KEY (designationId)
		REFERENCES egeis_designation(id)
);

CREATE SEQUENCE seq_egeis_departmentDesignation INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
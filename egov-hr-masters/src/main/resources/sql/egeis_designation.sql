CREATE TABLE egeis_designation (
	id BIGINT NOT NULL,
	name CHARACTER VARYING(100) NOT NULL,
	code CHARACTER VARYING(20) NOT NULL,
	description CHARACTER VARYING(250),
	chartOfAccounts CHARACTER VARYING(10),
	active BOOLEAN NOT NULL,
	tenantId CHARACTER VARYING(250) NOT NULL,

	CONSTRAINT pk_egeis_designation PRIMARY KEY (id),
	CONSTRAINT uk_egeis_designation_code UNIQUE KEY (code)
);

CREATE SEQUENCE seq_egeis_designation INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
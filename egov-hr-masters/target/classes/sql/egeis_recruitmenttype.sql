CREATE TABLE egeis_recruitmentType (
	id BIGINT NOT NULL,
	name CHARACTER VARYING(50) NOT NULL,
	description CHARACTER VARYING(250),
	tenantId CHARACTER VARYING(250) NOT NULL,

	CONSTRAINT pk_egeis_recruitmentType PRIMARY KEY (id)
);

CREATE SEQUENCE seq_egeis_recruitmentType INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE eg_language (
	id BIGINT NOT NULL,
	name CHARACTER VARYING(100) NOT NULL,
	description CHARACTER VARYING(250),
	active BOOLEAN NOT NULL,
	tenantId CHARACTER VARYING(250) NOT NULL,

	CONSTRAINT pk_eg_language PRIMARY KEY (id),
	CONSTRAINT uk_eg_language_name UNIQUE KEY (name)
);

CREATE SEQUENCE seq_eg_language INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE quarkus-social;

CREATE TABLE USERS (
	id bigserial not null primary key, -- serial e bigserial são equivalentes aos comandos integer e long do java, com a diferença que são auto-increment
	name varchar(100) not null,
	age integer not null
);
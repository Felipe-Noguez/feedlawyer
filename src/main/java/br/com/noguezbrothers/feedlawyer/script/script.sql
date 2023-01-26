CREATE
USER FEEDLAWYER IDENTIFIED BY oracle;
GRANT CONNECT TO FEEDLAWYER;
GRANT CONNECT, RESOURCE, DBA TO FEEDLAWYER;
GRANT
CREATE
SESSION TO FEEDLAWYER;
GRANT DBA TO FEEDLAWYER;
GRANT
CREATE VIEW, CREATE PROCEDURE, CREATE SEQUENCE to FEEDLAWYER;
GRANT
UNLIMITED
TABLESPACE TO FEEDLAWYER;
GRANT CREATE
MATERIALIZED VIEW TO FEEDLAWYER;
GRANT
CREATE TABLE TO FEEDLAWYER;
GRANT
GLOBAL
QUERY REWRITE TO FEEDLAWYER;
GRANT
SELECT ANY TABLE TO FEEDLAWYER;

alter
session set current_schema=FEEDLAWYER

CREATE TABLE FUNCIONARIO
(
    id_funcionario NUMBER NOT NULL,
    nome           varchar2(255) NOT NULL,
    cpf            varchar2(11) NOT NULL UNIQUE,
    especializacao varchar2(255) NOT NULL,
    login          varchar2(255) UNIQUE,
    senha          varchar2(255),
    tipo_perfil    NUMBER NOT null,
    PRIMARY KEY (id_funcionario)
);

CREATE TABLE CARGO
(
    id_perfil number not null,
    nome      varchar2(255) not null unique,
    PRIMARY KEY (id_perfil)
);

CREATE TABLE FUNCIONARIO_CARGO
(
    id_funcionario_cargo number not null,
    id_cargo             number,
    id_funcionario       number,
    PRIMARY KEY (id_funcionario_cargo),
    FOREIGN KEY (id_funcionario_cargo) REFERENCES FUNCIONARIO (id_funcionario),
    FOREIGN KEY (id_funcionario_cargo) REFERENCES CARGO (id_perfil)
);

CREATE TABLE AVALIACAO
(
    id_avaliacao   number not null,
    descricao      varchar2(2000) not null,
    nome_advogado  varchar2(255) not null,
    nota_avaliacao number not null,
    nome_cliente   varchar2(255) not null,
    sugestao       varchar2(2000),
    email_cliente  varchar2(255) not null,
    PRIMARY KEY (id_avaliacao)
);

CREATE TABLE SERVICO
(
    id_servico   number not null,
    descricao    varchar2(2000),
    id_avaliacao number,
    PRIMARY KEY (id_servico),
    CONSTRAINT AVALIACAO
        FOREIGN KEY (id_avaliacao)
            REFERENCES AVALIACAO (id_avaliacao)
);

CREATE TABLE SERVICO_FUNCIONARIO
(
    id_servico_funcionario number not null,
    id_servico             number,
    id_funcionario         number,
    PRIMARY KEY (id_servico_funcionario),
    FOREIGN KEY (id_servico_funcionario) REFERENCES SERVICO (id_servico),
    FOREIGN KEY (id_servico_funcionario) REFERENCES FUNCIONARIO (id_funcionario)
);

CREATE SEQUENCE SEQ_CARGO
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_AVALIACAO
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_FUNCIONARIO
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_SERVICO
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_SERVICO_FUNCIONARIO
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

CREATE SEQUENCE SEQ_FUNCIONARIO_CARGO
    START WITH 1
    INCREMENT BY 1 NOCACHE NOCYCLE;

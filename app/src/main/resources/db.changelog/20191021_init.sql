CREATE SEQUENCE hibernate_sequence;

CREATE TABLE profile
(
    id                BIGSERIAL   NOT NULL,
    name              varchar(50) not null,
    surname           varchar(50) not null,
    username          varchar(50) not null,
    encryptedPassword varchar(80) not null,
    email             varchar(80) not null,
    birthDate         date        not null,

    PRIMARY KEY (id)
);
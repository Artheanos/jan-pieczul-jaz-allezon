create table auction
(
    id           bigserial   not null,
    title        varchar(60) not null,
    price        int         not null,
    htmlFileName varchar(20) not null unique,
    owner_id     bigint      not null,
    category_id  bigint,
    primary key (id)
);



create table image
(
    name       varchar(20) not null,
    auction_id bigint,
    primary key (name)
);



create table parameter
(
    id   bigserial,
    name varchar(30) unique,
    primary key (id)
);

create table auction_parameter
(
    auction_id   bigint,
    parameter_id bigint,
    value        varchar(50),
    primary key (auction_id, parameter_id)
);



create table section
(
    id   bigserial,
    name varchar(30) unique,
    primary key (id)
);


create table category
(
    id         bigserial,
    name       varchar(30) unique,
    section_id bigint,
    primary key (id)
);


create table auction_profile_access
(
    auction_id  bigint,
    profile_id  bigint,
    access_type smallint
);
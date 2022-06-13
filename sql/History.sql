CREATE TABLE HISTORY
(
    id         integer unique           not null,
    shopunitid uuid                     not null,
    name       varchar                  not null,
    date       timestamp with time zone not null,
    parentid   uuid,
    type       SHOPUNITTYPE             not null,
    price      integer,

    PRIMARY KEY (id)
);
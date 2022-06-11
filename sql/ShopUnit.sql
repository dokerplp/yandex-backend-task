CREATE TYPE SHOPUNITTYPE AS ENUM ('OFFER', 'CATEGORY');

CREATE TABLE SHOPUNIT (
    id uuid unique not null,
    name varchar not null,
    date timestamp with time zone not null,
    parentid uuid,
    type SHOPUNITTYPE not null,
    price integer,

    PRIMARY KEY (id)
);

CREATE FUNCTION get_type(uuid) RETURNS SHOPUNITTYPE
    RETURN (SELECT TYPE FROM SHOPUNIT u WHERE u.id = $1);

CREATE FUNCTION check_parent(uuid) RETURNS BOOLEAN
    RETURN get_type($1) = 'CATEGORY' OR get_type($1) IS NULL;

CREATE FUNCTION check_no_children(uuid) RETURNS BOOLEAN
    RETURN (SELECT count(*) FROM SHOPUNIT U WHERE u.parentid = $1) = 0;

ALTER TABLE SHOPUNIT
ADD CONSTRAINT type_check CHECK (check_parent(SHOPUNIT.parentid) AND (SHOPUNIT.type = 'CATEGORY' OR check_no_children(SHOPUNIT.id)));

ALTER TABLE SHOPUNIT
ADD CONSTRAINT price_check CHECK ( (SHOPUNIT.type = 'CATEGORY' AND SHOPUNIT.price IS NULL) OR (SHOPUNIT.type != 'CATEGORY' AND SHOPUNIT.price IS NOT NULL AND SHOPUNIT.price >= 0 ));


comment on column SHOPUNIT.id is 'Уникальный идентфикатор';
comment on column SHOPUNIT.name is 'Имя категории';
comment on column SHOPUNIT.date is 'Время последнего обновления элемента';
comment on column SHOPUNIT.parentid is 'UUID родительской категории';
comment on column SHOPUNIT.type is 'Уникальный идентфикатор';
comment on column SHOPUNIT.price is 'Целое число, для категории - это средняя цена всех дочерних товаров(включая товары подкатегорий). Если цена является не целым числом, округляется в меньшую сторону до целого числа. Если категория не содержит товаров цена равна null.';
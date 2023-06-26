drop table if exists beer_order;

drop table if exists beer_order_line;

create table beer_order
(
    id           varchar(36) NOT NULL PRIMARY KEY,
    customer_ref varchar(255) DEFAULT NULL,
    customer_id  varchar(36)  DEFAULT NULL REFERENCES customer (id),
    version      bigint       DEFAULT NULL,
    created_date datetime(6)  DEFAULT NULL,
    update_date  datetime(6)  DEFAULT NULL

) engine = InnoDB;

create table beer_order_line
(
    id                 varchar(36) NOT NULL PRIMARY KEY,
    order_quantity     integer     DEFAULT NULL,
    quantity_allocated integer     DEFAULT NULL,
    beer_id            varchar(36) DEFAULT NULL REFERENCES beer (id),
    beer_order_id      varchar(36) DEFAULT NULL REFERENCES beer_order (id),
    version            bigint      DEFAULT NULL,
    created_date       datetime(6) DEFAULT NULL,
    update_date        datetime(6) DEFAULT NULL

) engine = InnoDB;


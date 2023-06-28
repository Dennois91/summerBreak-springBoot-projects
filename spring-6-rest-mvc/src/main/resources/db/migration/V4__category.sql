drop table if exists category;
drop table if exists beer_category;

create table category
(

    id           varchar(36) NOT NULL PRIMARY KEY,
    description  varchar(50),
    version      bigint      DEFAULT NULL,
    created_date datetime(6) DEFAULT NULL,
    update_date  datetime(6) DEFAULT NULL

) ENGINE = InnoDB;

create table beer_category
(

    beer_id     varchar(36) NOT NULL REFERENCES beer (id),
    category_id varchar(36) NOT NULL REFERENCES category (id),
    PRIMARY KEY (beer_id, category_id)
) ENGINE = InnoDB;
drop table if exists beer_order_shipment;

CREATE TABLE beer_order_shipment
(
    id              VARCHAR(36) NOT NULL PRIMARY KEY,
    beer_order_id   VARCHAR(36) UNIQUE,
    tracking_number VARCHAR(50),
    version         bigint      DEFAULT NULL,
    created_date    datetime(6) DEFAULT NULL,
    update_date     datetime(6) DEFAULT NULL,
    CONSTRAINT FOREIGN KEY (beer_order_id) REFERENCES beer_order (id)

) ENGINE = InnoDB;

ALTER TABLE beer_order
    ADD COLUMN beer_order_shipment_id VARCHAR(36);
ALTER TABLE beer_order
    ADD CONSTRAINT FOREIGN KEY (beer_order_shipment_id) REFERENCES beer_order_shipment (id);
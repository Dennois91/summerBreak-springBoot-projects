alter table beer_order
add constraint FOREIGN KEY (customer_id) REFERENCES customer (id)
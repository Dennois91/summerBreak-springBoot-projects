alter table beer_category
    add CONSTRAINT FOREIGN KEY (beer_id) REFERENCES beer (id),
    add CONSTRAINT FOREIGN KEY (category_id) REFERENCES category (id);
alter table beer_order_line
add CONSTRAINT FOREIGN KEY (beer_id) REFERENCES beer (id),
    add CONSTRAINT FOREIGN KEY (beer_order_id) REFERENCES beer_order (id);


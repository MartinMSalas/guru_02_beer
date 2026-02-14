drop table if exists beer_order_shipment;


create table beer_order_shipment (
    beer_order_shipment_id CHAR(36) not null primary key,
    beer_order_id CHAR(36) not null,
    shipment_tracking_number varchar(255),
    created_date timestamp,
    last_modified_date datetime(6) not null,
    version bigint,
    CONSTRAINT bos_pk FOREIGN KEY (beer_order_id) REFERENCES beer_order(beer_order_id)
) ENGINE=InnoDB;

ALTER TABLE beer_order
    ADD COLUMN beer_order_shipment_id CHAR(36),
    ADD CONSTRAINT bos_fk FOREIGN KEY (beer_order_shipment_id) REFERENCES beer_order_shipment(beer_order_shipment_id);
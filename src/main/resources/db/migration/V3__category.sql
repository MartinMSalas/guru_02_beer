drop table if exists category;
drop table if exists beer_category;

create table category (
    category_id CHAR(36) not null primary key,
    category_description varchar(50),
    created_date timestamp,
    last_modified_date datetime(6) not null,
    version bigint
) ENGINE=InnoDB;

create table beer_category (
    beer_id CHAR(36) not null,
    category_id CHAR(36) not null,
    primary key (beer_id, category_id),
    constraint pc_beer_id_fk FOREIGN KEY (beer_id) references beer(beer_id),
    constraint pc_category_id_fk FOREIGN KEY (category_id) references category(category_id)
) ENGINE=InnoDB;
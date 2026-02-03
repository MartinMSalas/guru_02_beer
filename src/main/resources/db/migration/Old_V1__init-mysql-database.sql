
    create table beer (
        price decimal(19,2) not null,
        quantity_on_hand integer not null,
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        upc varchar(50) not null,
        beer_name varchar(255) not null,
        beer_style enum ('ALE','GOSE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT') not null,
        primary key (beer_id)
    ) engine=InnoDB;

    create table customer (
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        customer_name varchar(255) not null,
        primary key (beer_id)
    ) engine=InnoDB;

    alter table beer 
       add constraint UKp9mb364xktkjqmprmg89u2etr unique (upc);

    create table beer (
        price decimal(19,2) not null,
        quantity_on_hand integer not null,
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        upc varchar(50) not null,
        beer_name varchar(255) not null,
        beer_style enum ('ALE','GOSE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT') not null,
        primary key (beer_id)
    ) engine=InnoDB;

    create table customer (
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        customer_name varchar(255) not null,
        primary key (beer_id)
    ) engine=InnoDB;

    alter table beer 
       add constraint UKp9mb364xktkjqmprmg89u2etr unique (upc);

    create table beer (
        price decimal(19,2) not null,
        quantity_on_hand integer not null,
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        upc varchar(50) not null,
        beer_name varchar(255) not null,
        beer_style enum ('ALE','GOSE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT') not null,
        primary key (beer_id)
    ) engine=InnoDB;

    create table customer (
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        customer_name varchar(255) not null,
        primary key (beer_id)
    ) engine=InnoDB;

    alter table beer 
       add constraint UKp9mb364xktkjqmprmg89u2etr unique (upc);

    create table beer (
        price decimal(19,2) not null,
        quantity_on_hand integer not null,
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        upc varchar(50) not null,
        beer_name varchar(255) not null,
        beer_style enum ('ALE','GOSE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT') not null,
        primary key (beer_id)
    ) engine=InnoDB;

    create table customer (
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        customer_name varchar(255) not null,
        primary key (beer_id)
    ) engine=InnoDB;

    alter table beer 
       add constraint UKp9mb364xktkjqmprmg89u2etr unique (upc);

    create table beer (
        price decimal(19,2) not null,
        quantity_on_hand integer not null,
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        upc varchar(50) not null,
        beer_name varchar(255) not null,
        beer_style enum ('ALE','GOSE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT') not null,
        primary key (beer_id)
    ) engine=InnoDB;

    create table customer (
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        customer_name varchar(255) not null,
        primary key (beer_id)
    ) engine=InnoDB;

    alter table beer 
       add constraint UKp9mb364xktkjqmprmg89u2etr unique (upc);

    create table beer (
        price decimal(19,2) not null,
        quantity_on_hand integer not null,
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        upc varchar(50) not null,
        beer_name varchar(255) not null,
        beer_style enum ('ALE','GOSE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT') not null,
        primary key (beer_id)
    ) engine=InnoDB;

    create table customer (
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        customer_name varchar(255) not null,
        primary key (beer_id)
    ) engine=InnoDB;

    alter table beer 
       add constraint UKp9mb364xktkjqmprmg89u2etr unique (upc);

    create table beer (
        price decimal(19,2) not null,
        quantity_on_hand integer not null,
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        upc varchar(50) not null,
        beer_name varchar(255) not null,
        beer_style enum ('ALE','GOSE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT') not null,
        primary key (beer_id)
    ) engine=InnoDB;

    create table customer (
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        customer_name varchar(255) not null,
        primary key (beer_id)
    ) engine=InnoDB;

    alter table beer 
       add constraint UKp9mb364xktkjqmprmg89u2etr unique (upc);

    create table beer (
        price decimal(19,2) not null,
        quantity_on_hand integer not null,
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        upc varchar(50) not null,
        beer_name varchar(255) not null,
        beer_style enum ('ALE','GOSE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT') not null,
        primary key (beer_id)
    ) engine=InnoDB;

    create table customer (
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        customer_name varchar(255) not null,
        primary key (beer_id)
    ) engine=InnoDB;

    alter table beer 
       add constraint UKp9mb364xktkjqmprmg89u2etr unique (upc);

    create table beer (
        price decimal(19,2) not null,
        quantity_on_hand integer not null,
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        upc varchar(50) not null,
        beer_name varchar(255) not null,
        beer_style enum ('ALE','GOSE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT') not null,
        primary key (beer_id)
    ) engine=InnoDB;

    create table customer (
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        beer_id CHAR(36) not null,
        customer_name varchar(255) not null,
        primary key (beer_id)
    ) engine=InnoDB;

    alter table beer 
       add constraint UKp9mb364xktkjqmprmg89u2etr unique (upc);


    create table beer (
        beer_id CHAR(36) not null,
        beer_name varchar(255) not null,
        beer_style enum ('ALTBIER','AMBER_ALE','AMERICAN_AMBER_LAGER','AMERICAN_DARK_LAGER','AMERICAN_IPA','AMERICAN_LAGER','AMERICAN_LIGHT_LAGER','AMERICAN_PALE_ALE','AMERICAN_PORTER','AMERICAN_STOUT','AMERICAN_STRONG_ALE','AMERICAN_WHEAT','BALTIC_PORTER','BARLEYWINE','BELGIAN_PALE_ALE','BELGIAN_STRONG_ALE','BELGIAN_WHITE','BERLINER_WEISSE','BIERE_DE_GARDE','BITTER','BLACK_IPA','BROWN_ALE','CHILE_BEER','CREAM_ALE','CZECH_PILSNER','DOUBLE_IPA','DRY_STOUT','DUNKEL','DUNKELWEIZEN','EXTRA_SPECIAL_BITTER','FARMHOUSE_ALE','FOREIGN_EXTRA_STOUT','FRUIT_BEER','GERMAN_PILSNER','GOSE','GUEUZE','HEFEWEIZEN','HELLES','HERBED_BEER','IMPERIAL_IPA','IMPERIAL_STOUT','IPA','KOLSCH','LAGER','LAMBIC','MARZEN','OATMEAL_STOUT','OKTOBERFEST','PALE_ALE','PILSNER','PORTER','PUMPKIN_ALE','ROBUST_PORTER','RYE_BEER','SAHTI','SAISON','SCHWARZBIER','SCOTCH_ALE','SESSION_IPA','SMOKED_BEER','SOUR_ALE','SPICE_BEER','STOUT','STRONG_ALE','SWEET_STOUT','UNKNOWN','VIENNA_LAGER','WHEAT','WITBIER') not null,
        external_beer_id integer not null,
        price decimal(19,2) not null,
        quantity_on_hand integer not null,
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        upc varchar(50) not null,
        primary key (beer_id)
    ) engine=InnoDB;

    create table customer (
        customer_id CHAR(36) not null,
        customer_name varchar(255) not null,
        email varchar(255),
        created_date datetime(6) not null,
        last_modified_date datetime(6) not null,
        version bigint,
        primary key (customer_id)
    ) engine=InnoDB;

    create index ix_beer_external_id 
       on beer (external_beer_id);

    create index ix_beer_upc 
       on beer (upc);

    alter table beer 
       add constraint uk_beer_external_id unique (external_beer_id);

    alter table beer 
       add constraint uk_beer_upc unique (upc);

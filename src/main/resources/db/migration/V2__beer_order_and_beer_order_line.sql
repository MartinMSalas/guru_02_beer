-- -----------------------------------------------------
-- Table `mydatabase`.`beer_order`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `beer_order` (
                                                         `beer_order_id` CHAR(36) NOT NULL,
                                                         `customer_ref` VARCHAR(255) NULL,
                                                         `created_date` DATETIME(6) NOT NULL,
                                                         `last_modified_date` DATETIME(6) NOT NULL,
                                                         `version` BIGINT NULL DEFAULT NULL,
                                                         `customer_id` CHAR(36) NULL,
                                                         PRIMARY KEY (`beer_order_id`),
                                                         INDEX `customer_id_idx` (`customer_id` ASC) VISIBLE,
                                                         CONSTRAINT `customer_id`
                                                             FOREIGN KEY (`customer_id`)
                                                                 REFERENCES `customer` (`customer_id`)
                                                                 ON DELETE NO ACTION
                                                                 ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydatabase`.`beer_order_line`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `beer_order_line` (
                                                              `beer_order_line_id` CHAR(36) NOT NULL,
                                                              `created_date` DATETIME(6) NOT NULL,
                                                              `last_modified_date` DATETIME(6) NOT NULL,
                                                              `version` BIGINT NULL DEFAULT NULL,
                                                              `beer_id` CHAR(36) NULL,
                                                              `beer_order_id` CHAR(36) NULL,
                                                              `order_quantity` INT NULL,
                                                              `quantity_allocated` INT NULL,
                                                              PRIMARY KEY (`beer_order_line_id`),
                                                              INDEX `beer_order_id_idx` (`beer_order_id` ASC) VISIBLE,
                                                              INDEX `beer_id_idx` (`beer_id` ASC) VISIBLE,
                                                              CONSTRAINT `beer_order_id`
                                                                  FOREIGN KEY (`beer_order_id`)
                                                                      REFERENCES `beer_order` (`beer_order_id`)
                                                                      ON DELETE NO ACTION
                                                                      ON UPDATE NO ACTION,
                                                              CONSTRAINT `beer_id`
                                                                  FOREIGN KEY (`beer_id`)
                                                                      REFERENCES `beer` (`beer_id`)
                                                                      ON DELETE NO ACTION
                                                                      ON UPDATE NO ACTION)
    ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS product (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    current_price DECIMAL(12, 2) NOT NULL,
    description VARCHAR(1000) DEFAULT NULL,
    created_by VARCHAR(100) NOT NULL,
    created_date DATETIME NOT NULL,
    last_modified_by VARCHAR(100) NOT NULL,
    last_modified_date DATETIME NOT NULL,
	PRIMARY KEY (id));

-- Base data to test

INSERT INTO product (name, current_price, description, created_by, created_date, last_modified_by, last_modified_date)
    VALUES ('Banana', 2000.00, 'Fruit by unit', 'admin', NOW(), 'admin', NOW()),
           ('Orange', 1000.00, 'Fruit by unit', 'admin', NOW(), 'admin', NOW()),
           ('Strawberry', 2000.00, 'Kg price', 'admin', NOW(), 'admin', NOW());

-- ROLLBACK
-- DELETE FROM flyway_schema_history WHERE version = '0001';
-- DROP TABLE product;
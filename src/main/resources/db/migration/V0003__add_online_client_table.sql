CREATE TABLE IF NOT EXISTS online_client (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(500) NOT NULL,
    address_line1 VARCHAR(500) NOT NULL,
    address_line2 VARCHAR(500) DEFAULT NULL,
    address_city VARCHAR(100) NOT NULL,
    address_zip_code VARCHAR(50) NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    created_date DATETIME NOT NULL,
    last_modified_by VARCHAR(100) NOT NULL,
    last_modified_date DATETIME NOT NULL,
	PRIMARY KEY (id));

INSERT INTO online_client (name, address_line1, address_line2, address_city, address_zip_code, created_by, created_date, last_modified_by, last_modified_date)
    VALUES ('Robotito', 'Street 1', 'Street 2', 'City 1', '12345', 'admin', NOW(), 'admin', NOW()),
           ('Pablo Mármol', 'Street 3', 'Street 4', 'City 2', '54321', 'admin', NOW(), 'admin', NOW()),
           ('Chuck Berry','Street 5', 'Street 6', 'City 3', '67890', 'admin', NOW(), 'admin', NOW());

-- ROLLBACK
-- DELETE FROM flyway_schema_history WHERE version = '0003';
-- DROP TABLE online_client;
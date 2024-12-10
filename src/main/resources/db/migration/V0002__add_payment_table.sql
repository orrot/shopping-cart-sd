CREATE TABLE IF NOT EXISTS payment (
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    fee_calc_method VARCHAR(50) DEFAULT NULL,
    fee_value DECIMAL(12,2) DEFAULT NULL,
    created_by VARCHAR(100) NOT NULL,
    created_date DATETIME NOT NULL,
    last_modified_by VARCHAR(100) NOT NULL,
    last_modified_date DATETIME NOT NULL,
    CONSTRAINT chk_fee_calc_method_values CHECK (fee_calc_method IN ('PERCENTAGE', 'FIXED')),
	PRIMARY KEY (code));


INSERT INTO payment(code, name, fee_calc_method, fee_value, created_by, created_date, last_modified_by, last_modified_date)
VALUES ('CASH', 'Cash', NULL, NULL, 'admin', NOW(), 'admin', NOW()),
       ('VISA_CC', 'Visa Credit Card', 'PERCENTAGE', 3.00, 'admin', NOW(), 'admin', NOW()),
       ('MASTERCARD_CC', 'Mastercard Credit Card', 'PERCENTAGE', 2.00, 'admin', NOW(), 'admin', NOW()),
       ('STORE_CC', 'Store Credit Card', 'FIXED', 5.00, 'admin', NOW(), 'admin', NOW());
-- ROLLBACK
-- DELETE FROM flyway_schema_history WHERE version = '0002';
-- DROP TABLE payment;
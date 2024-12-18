CREATE TABLE IF NOT EXISTS payment_method (
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    fixed_fee DECIMAL(12,2) DEFAULT 0,
    percentage_fee DECIMAL(8,4) DEFAULT 0,
    created_by VARCHAR(100) NOT NULL,
    created_date DATETIME NOT NULL,
    last_modified_by VARCHAR(100) NOT NULL,
    last_modified_date DATETIME NOT NULL,
    PRIMARY KEY (code));

INSERT INTO payment_method(code, name, fixed_fee, percentage_fee, created_by, created_date, last_modified_by, last_modified_date)
VALUES ('CASH', 'Cash', 0, 0, 'admin', NOW(), 'admin', NOW()),
       ('VISA', 'Visa Credit Card', 800, 0.02, 'admin', NOW(), 'admin', NOW()),
       ('MASTERCARD', 'Mastercard Credit Card', 800, 0.04, 'admin', NOW(), 'admin', NOW());


-- ROLLBACK
-- DELETE FROM flyway_schema_history WHERE version = '0002';
-- DROP TABLE payment_method;
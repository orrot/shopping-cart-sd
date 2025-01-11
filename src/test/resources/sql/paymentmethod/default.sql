INSERT INTO payment_method(code, name, fixed_fee, percentage_fee, created_by, created_date, last_modified_by, last_modified_date)
VALUES ('CASH', 'Cash', 0, 0, 'admin', NOW(), 'admin', NOW()),
       ('VISA', 'Visa Credit Card', 800, 0.02, 'admin', NOW(), 'admin', NOW());

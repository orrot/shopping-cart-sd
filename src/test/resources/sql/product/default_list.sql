DELETE FROM product;
INSERT INTO product (id, name, current_price, description, created_by, created_date, last_modified_by, last_modified_date)
VALUES  (-1, 'Banana IntegrationTest', 10000, "Banana Desc", 'test', NOW(), 'test', NOW()),
        (-2, 'Apple IntegrationTest', 20000, "Apple Desc", 'test', NOW(), 'test', NOW()),
        (-3, 'Pineapple', 30000, "Pineapple Desc", 'test', NOW(), 'test', NOW());

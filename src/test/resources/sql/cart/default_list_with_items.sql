INSERT INTO cart (id, payment_method_code, online_client_owner_id, created_by, created_date, last_modified_by, last_modified_date)
VALUES (-3, 'CASH', -1, 'admin', NOW(), 'admin', NOW()),
       (-4, 'VISA', -2, 'admin', NOW(), 'admin', NOW());

INSERT INTO cart_item (cart_id, product_id, quantity)
VALUES (-3, -1, 4), -- Cart -3 items: 4 Bananas, 2 Apples
       (-3, -2, 2),
       (-4, -1, 3), -- Cart -4 items: 3 Bananas, 1 Apple
       (-4, -2, 1);
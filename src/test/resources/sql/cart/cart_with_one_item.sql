INSERT INTO cart (id, payment_method_code, online_client_owner_id, created_by, created_date, last_modified_by, last_modified_date)
VALUES (-5, 'VISA', -1, 'admin', NOW(), 'admin', NOW());

INSERT INTO cart_item (cart_id, product_id, quantity)
    VALUES (-5, -2, 1), -- Product -2 has 1 of quantity (Apple)
           (-5, -3, 2); -- Product -3 has 2 of quantity (Pineapple)
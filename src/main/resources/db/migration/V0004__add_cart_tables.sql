CREATE TABLE IF NOT EXISTS cart (
    id BIGINT NOT NULL AUTO_INCREMENT,
    payment_method_code VARCHAR(50) NOT NULL,
    online_client_owner_id BIGINT DEFAULT NULL,
    created_by VARCHAR(100) NOT NULL,
    created_date DATETIME NOT NULL,
    last_modified_by VARCHAR(100) NOT NULL,
    last_modified_date DATETIME NOT NULL,
    CONSTRAINT fk_cart_payment_method FOREIGN KEY (payment_method_code) REFERENCES payment_method(code),
    CONSTRAINT fk_cart_online_client FOREIGN KEY (online_client_owner_id) REFERENCES online_client(id),
	PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS cart_item (
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_cart_cart_item FOREIGN KEY (cart_id) REFERENCES cart(id),
    CONSTRAINT fk_product_cart_item FOREIGN KEY (product_id) REFERENCES product(id),
	PRIMARY KEY (cart_id, product_id));

-- ROLLBACK
-- DELETE FROM flyway_schema_history WHERE version = '0004';
-- DROP TABLE cart;
-- DROP TABLE cart_item;
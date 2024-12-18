CREATE TABLE IF NOT EXISTS cart (
    id BIGINT NOT NULL AUTO_INCREMENT,
    payment_method_code VARCHAR(50) NOT NULL,
    cart_user_owner VARCHAR(100) DEFAULT NULL,
    created_by VARCHAR(100) NOT NULL,
    created_date DATETIME NOT NULL,
    last_modified_by VARCHAR(100) NOT NULL,
    last_modified_date DATETIME NOT NULL,
    CONSTRAINT fk_payment_method_cart FOREIGN KEY (payment_method_code) REFERENCES payment_method(code),
	PRIMARY KEY (id));


CREATE TABLE IF NOT EXISTS cart_item (
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_cart_cart_item FOREIGN KEY (cart_id) REFERENCES cart(id),
    CONSTRAINT fk_product_cart_item FOREIGN KEY (product_id) REFERENCES product(id),
	PRIMARY KEY (cart_id, product_id));

-- ROLLBACK
-- DELETE FROM flyway_schema_history WHERE version = '0003';
-- DROP TABLE cart;
-- DROP TABLE cart_item;
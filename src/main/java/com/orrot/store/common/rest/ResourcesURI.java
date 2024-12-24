package com.orrot.store.common.rest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcesURI {

    public static final String CARTS_URI = "/v1/carts";
    public static final String CARTS_TAG = "Carts";

    public static final String CART_ITEMS_URI = "/v1/carts/{cartId}/items";
    public static final String CART_ITEMS_TAG = "Cart Items";

    public static final String PRODUCTS_URI = "/v1/products";
    public static final String PRODUCTS_TAG = "Products";

    public static final String PAYMENT_METHODS_URI = "/v1/payment-methods";
    public static final String PAYMENT_METHODS_TAG = "Payment Methods";

    public static final String ONLINE_CLIENT_URI = "/v1/online-clients";
    public static final String ONLINE_CLIENT_TAG = "Online Clients";
}

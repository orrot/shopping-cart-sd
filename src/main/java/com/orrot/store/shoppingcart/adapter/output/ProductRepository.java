package com.orrot.store.shoppingcart.adapter.output;

import com.orrot.store.shoppingcart.port.output.ProductOutputPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ProductRepository implements ProductOutputPort {
}

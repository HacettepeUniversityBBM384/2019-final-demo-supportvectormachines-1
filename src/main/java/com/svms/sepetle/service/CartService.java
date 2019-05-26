package com.svms.sepetle.service;

import com.svms.sepetle.model.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {

    void addProduct(Product product);

    void removeProduct(Product product);

    Map<Product, Integer> getProductsInCart();

    void checkout();

    BigDecimal getTotal();

}

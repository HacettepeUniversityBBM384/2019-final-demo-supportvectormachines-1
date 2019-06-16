package com.svms.sepetle.service;

import com.svms.sepetle.model.Order;
import com.svms.sepetle.model.Product;
import com.svms.sepetle.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order> findById(Long id);

    Order get(long id);

    Order saveOrder(Order order);

    List<Order> findAllByUser(User user);

    List<Product> getProductsOfOrder(Order order);


}

package com.svms.sepetle.service.impl;



import com.svms.sepetle.model.Order;
import com.svms.sepetle.model.Product;
import com.svms.sepetle.model.User;
import com.svms.sepetle.repository.OrderRepository;
import com.svms.sepetle.repository.ProductRepository;
import com.svms.sepetle.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;

    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order get(long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    @Override
    public List<Product> getProductsOfOrder(Order order) {
        return order.getProducts();
    }


}

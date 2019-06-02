package com.svms.sepetle.service;

import com.svms.sepetle.model.Category;
import com.svms.sepetle.model.Product;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Long id);

    void delete(Product product);

    Product save(Product product);

    Product update(Product product);

    Collection<Product> findAll();

    List<Product> getProductsByCategory(Category category);

    List<Product> getProductsBySeller(int s_id);
}

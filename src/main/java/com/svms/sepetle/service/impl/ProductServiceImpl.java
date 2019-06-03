package com.svms.sepetle.service.impl;

import com.svms.sepetle.model.Category;
import com.svms.sepetle.model.Product;
import com.svms.sepetle.repository.ProductRepository;
import com.svms.sepetle.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Collection<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        List<Product> productsByCategory = new ArrayList<>();

        for(Product product: productRepository.findAll()) {
            if(category.equals(product.getCategory())){
                productsByCategory.add(product);
            }
        }

        return productsByCategory;
    }

    @Override
    public List<Product> getProductsBySeller(int s_id) {

        List<Product> productsByManufacturer = new ArrayList<>();

        for(Product product: productRepository.findAll()) {
            System.out.println(s_id + " " + product.getSeller());
            if(product.getSeller().getId() == s_id){
                productsByManufacturer.add(product);
            }
        }
        System.out.println(productsByManufacturer);
        return productsByManufacturer;
    }
}

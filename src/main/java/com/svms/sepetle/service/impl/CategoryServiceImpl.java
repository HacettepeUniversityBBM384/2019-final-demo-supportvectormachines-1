package com.svms.sepetle.service.impl;

import com.svms.sepetle.model.Category;
import com.svms.sepetle.repository.CategoryRepository;
import com.svms.sepetle.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository productRepository) {
        this.categoryRepository = productRepository;
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Collection<Category> findAll() {
        return categoryRepository.findAll();
    }
}

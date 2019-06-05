package com.svms.sepetle.service;

import com.svms.sepetle.model.Category;

import java.util.Collection;

public interface CategoryService {
    Category findByName(String name);
    Collection<Category> findAll();
}

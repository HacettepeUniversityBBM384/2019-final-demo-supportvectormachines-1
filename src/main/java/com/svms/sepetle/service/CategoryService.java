package com.svms.sepetle.service;

import com.svms.sepetle.model.Category;

public interface CategoryService {
    Category findByName(String name);
}

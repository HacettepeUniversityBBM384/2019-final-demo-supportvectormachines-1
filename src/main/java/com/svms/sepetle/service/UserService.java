package com.svms.sepetle.service;

import java.util.Collection;

import com.svms.sepetle.model.User;

public interface UserService {

    User save(User user);

    Boolean delete(int id);

    User update(User user);

    User findById(int id);

    User findByUserName(String username);
    
    User findByEmail(String email);

    Collection<User> findAll();

    Collection<User>  findSellers();

    Collection<User>  findCustomers();

    
}

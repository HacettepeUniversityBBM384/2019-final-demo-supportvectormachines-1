package com.svms.sepetle.repository;

import org.springframework.data.repository.CrudRepository;

import com.svms.sepetle.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

}
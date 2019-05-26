package com.svms.sepetle.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svms.sepetle.model.User;
import com.svms.sepetle.repository.UserRepository;
import com.svms.sepetle.service.UserService;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collection;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean delete(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public  Collection<User>  findSellers() {
    	Collection<User> users = this.findAll();
    	Collection<User> sellers = new ArrayList<User>();
    	for(User user : users){
    		// User role 3 corresponds to seller
    		if(user.getRole() == 3) {
    			sellers.add(user);
    		}
    		
    	}
    	return sellers;
    }
    
    @Override
    public  Collection<User>  findCustomers() {
    	Collection<User> users = this.findAll();
    	Collection<User> customers = new ArrayList<User>();
    	for(User user : users){
    		// User role 2 corresponds to customer
    		if(user.getRole() == 2) {
    			customers.add(user);
    		}
    		
    	}
    	return customers;
    }

    
    @Override
    public Collection<User> findAll() {
        Iterable<User> itr = userRepository.findAll();
        return (Collection<User>) itr;
    }
}

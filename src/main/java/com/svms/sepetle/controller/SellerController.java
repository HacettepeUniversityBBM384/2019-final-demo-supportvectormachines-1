package com.svms.sepetle.controller;


import com.svms.sepetle.model.Product;
import com.svms.sepetle.model.User;
import com.svms.sepetle.service.ProductService;
import com.svms.sepetle.service.UserService;
import com.svms.sepetle.utils.Encoder;
import com.svms.sepetle.utils.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Optional;

@Controller
public class SellerController {


    @Autowired
    AppController globalController;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;   
    
    @RequestMapping("/seller")
    public ModelAndView homeAdmin() {
        Collection<Product> products = productService.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.setViewName("seller");
        return modelAndView;

    }

 
}

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
    
    @RequestMapping(value="/seller/editProduct/{id}", method = RequestMethod.GET)
    public String showUpdateProductForm(@PathVariable("id") Long id, Model model) {

        Optional<Product> o_product = productService.findById(id);
        Product product = o_product.get();

        model.addAttribute("product", product);
        return "seller_edit_product";
    }
    
    @PostMapping("/seller/updateProduct/{id}")
    public ModelAndView updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product, Model model,
                             final RedirectAttributes redirectAttributes) {

        Optional<Product> o_oldProduct = productService.findById(id);
        Product oldProduct = o_oldProduct.get();

        product.setDescription(oldProduct.getDescription());
        product.setRate(oldProduct.getRate());
        product.setReview_count(oldProduct.getReview_count());

        productService.save(product);

        return new ModelAndView("redirect:/seller");
        
    }
    
    @GetMapping("/seller/removeProduct/{id}")
    public ModelAndView removeProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product, Model model,
                                      final RedirectAttributes redirectAttributes) {

        Optional<Product> o_oldProduct = productService.findById(id);
        Product oldProduct = o_oldProduct.get();

        productService.delete(oldProduct);

        return new ModelAndView("redirect:/seller");
    }
    
    /*@PostMapping("/seller/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(cartService::addProduct);
        return new ModelAndView("redirect:/shoppingCart");
    }*/

 
}

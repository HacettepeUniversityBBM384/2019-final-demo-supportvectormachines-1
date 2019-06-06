package com.svms.sepetle.controller;


import com.svms.sepetle.model.Category;
import com.svms.sepetle.model.Product;
import com.svms.sepetle.model.User;
import com.svms.sepetle.service.CategoryService;
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

    @Autowired
    CategoryService categoryService;
    
    @RequestMapping("/seller")
    public ModelAndView homeAdmin() {

        int seller_id = globalController.getLoginUser().getId();
        Collection<Product> products = productService.getProductsBySeller(seller_id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.setViewName("seller");
        return modelAndView;

    }
    
    @RequestMapping(value="/seller/editProduct/{id}", method = RequestMethod.GET)
    public String showUpdateProductForm(@PathVariable("id") Long id, Model model) {
        Optional<Product> o_product = productService.findById(id);
        Product product = o_product.get();
        Collection<Category> categories = categoryService.findAll();

        model.addAttribute("product", product);
        // model.addAttribute("category", new Category());
        model.addAttribute("categories", categories);

        return "seller_edit_product";
    }
    
    @PostMapping("/seller/updateProduct/{id}")
    public ModelAndView updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product) {

        Optional<Product> o_oldProduct = productService.findById(id);
        Product oldProduct = o_oldProduct.get();

        product.setDescription(oldProduct.getDescription());
        product.setRate(oldProduct.getRate());
        product.setReview_count(oldProduct.getReview_count());
        product.setSeller(oldProduct.getSeller());
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

    @RequestMapping("/seller/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("rProduct", new Product());
        Collection<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "seller_add_product";
    }

    @RequestMapping(value = {"/seller/saveProduct"}, method = RequestMethod.POST)
    public String register(@ModelAttribute("rProduct") Product rProduct,
                           final RedirectAttributes redirectAttributes) {

        rProduct.setSeller(userService.findById(globalController.getLoginUser().getId()));
        rProduct.setReview_count(0);
        rProduct.setRate((float) 0);
        if (productService.save(rProduct) != null) {
            redirectAttributes.addFlashAttribute("saveProduct", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveProduct", "fail");
        }

        return "redirect:/seller";
    }

 
}

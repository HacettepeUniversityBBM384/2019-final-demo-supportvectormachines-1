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
public class AdminController {


    @Autowired
    AppController globalController;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;   
    
    @RequestMapping("/admin")
    public ModelAndView homeAdmin() {
        Collection<Product> products = productService.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.setViewName("admin");
        return modelAndView;

    }

    @RequestMapping("/admin/seller")
    public ModelAndView sellerAdmin() {
    	Collection<User> users = userService.findSellers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin_seller");
        return modelAndView;

    }
    
    @RequestMapping("/admin/customer")
    public ModelAndView customerAdmin() {
    	Collection<User> users = userService.findCustomers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin_customer");
        return modelAndView;

    }
    
    @RequestMapping(value="/admin/editProduct/{id}", method = RequestMethod.GET)
    public String showUpdateProductForm(@PathVariable("id") Long id, Model model) {

        Optional<Product> o_product = productService.findById(id);
        Product product = o_product.get();

        model.addAttribute("product", product);
        return "edit_product";
    }

    @PostMapping("/admin/updateProduct/{id}")
    public ModelAndView updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product, Model model,
                             final RedirectAttributes redirectAttributes) {

        Optional<Product> o_oldProduct = productService.findById(id);
        Product oldProduct = o_oldProduct.get();

        product.setDescription(oldProduct.getDescription());
        product.setRate(oldProduct.getRate());
        product.setReview_count(oldProduct.getReview_count());

        productService.save(product);

        return new ModelAndView("redirect:/admin");
    }

    @RequestMapping(value="/admin/editSeller/{id}", method = RequestMethod.GET)
    public String showUpdateSellerForm(@PathVariable("id") int id, Model model) {

        User o_user = userService.findById(id);
        model.addAttribute("user", o_user);
        return "edit_seller";
    }
    
    @RequestMapping(value="/admin/editCustomer/{id}", method = RequestMethod.GET)
    public String showUpdateCustomerForm(@PathVariable("id") int id, Model model) {

        User o_user = userService.findById(id);
        model.addAttribute("user", o_user);
        return "edit_customer";
    }
    
    @PostMapping("/admin/updateCustomer/{id}")
 	public String updateCustomer(@PathVariable("id") int id, @ModelAttribute("user") User user, Model model,
                              final RedirectAttributes redirectAttributes) {
     	  User oldUser = userService.findById(id);
     	  String oldPassword = oldUser.getPassword();
 	      user.setRole(oldUser.getRole());
 		  userService.update(user);
 	      if(user.getPassword() == "") {
 	    	  user.setPassword(oldPassword);
 	      } else {
 	    	  if(user.getPassword().length() < 8) {
 	         	 redirectAttributes.addFlashAttribute("err", "invalid-pass");
 	         	 user.setPassword(oldPassword);
 	         	 userService.save(user);
 	             return "redirect:/admin/customer";	
 	         }
 	    	  user.setPassword(Encoder.getInstance().passwordEncoder.encode(user.getPassword()));
 	      }
 	      
 	      userService.save(user);
 	      
 	      System.out.println(user.toString());
 		  model.addAttribute("msg", "success");
 		  return "edit_customer";
 	  }
     
    
    @PostMapping("/admin/updateSeller/{id}")
 	public String updateSeller(@PathVariable("id") int id, @ModelAttribute("user") User user, Model model,
                              final RedirectAttributes redirectAttributes) {
     	  User oldUser = userService.findById(id);
     	  String oldPassword = oldUser.getPassword();
 	      user.setRole(oldUser.getRole());
 		  userService.update(user);
 	      if(user.getPassword() == "") {
 	    	  user.setPassword(oldPassword);
 	      } else {
 	    	  if(user.getPassword().length() < 8) {
 	         	 redirectAttributes.addFlashAttribute("err", "invalid-pass");
 	         	 user.setPassword(oldPassword);
 	         	 userService.save(user);
 	             return "redirect:/admin/edit_seller";	
 	         }
 	    	  user.setPassword(Encoder.getInstance().passwordEncoder.encode(user.getPassword()));
 	      }
 	      
 	      userService.save(user);
 	      
 	      System.out.println(user.toString());
 		  model.addAttribute("msg", "success");
 		  return "edit_seller";
 	  }
         
    
    
    @GetMapping("/admin/removeProduct/{id}")
    public ModelAndView removeProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product, Model model,
                                      final RedirectAttributes redirectAttributes) {

        Optional<Product> o_oldProduct = productService.findById(id);
        Product oldProduct = o_oldProduct.get();

        productService.delete(oldProduct);

        return new ModelAndView("redirect:/admin");
    }
    
    @GetMapping("/admin/removeSeller/{id}")
    public ModelAndView removeSeller(@PathVariable("id") int id, @ModelAttribute("user") User user, Model model,
                                      final RedirectAttributes redirectAttributes) {

        userService.delete(id);
        return new ModelAndView("redirect:/admin/seller");
    }
    
    
    @GetMapping("/admin/removeCustomer/{id}")
    public ModelAndView removeCustomer(@PathVariable("id") int id, @ModelAttribute("user") User user, Model model,
                                      final RedirectAttributes redirectAttributes) {

        userService.delete(id);
        return new ModelAndView("redirect:/admin/customer");
    }    
}

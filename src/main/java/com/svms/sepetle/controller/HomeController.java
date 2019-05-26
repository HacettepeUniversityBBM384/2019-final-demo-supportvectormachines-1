package com.svms.sepetle.controller;

import com.svms.sepetle.model.Product;
import com.svms.sepetle.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;

@Controller
public class HomeController {
    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value={"/", "/home"})
    public ModelAndView home() {

        Collection<Product> products = productService.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public String viewProduct(@PathVariable("productId") Long productId, Model model) {
        Optional<Product> optional_product = productService.findById(productId);
        Product product = optional_product.get();
        System.out.println(product);
        model.addAttribute("product", product);
        return "view_product";
    }

    @RequestMapping(value = "/product/rate/{productId}", method = RequestMethod.POST)
    public ModelAndView rateProduct(@PathVariable("productId") Long productId, @ModelAttribute("product") Product product, Model model) {
        Optional<Product> optional_product = productService.findById(productId);
        Product m_product = optional_product.get();
        Integer count = m_product.getReview_count() + 1;
        Float rate = (m_product.getRate()*(count-1) + product.getRate()) / count;
        m_product.setRate(rate);
        m_product.setReview_count(count);
        System.out.println(m_product.getRate());
        productService.update(m_product);
        return new ModelAndView("redirect:/product/{productId}");
    }

}
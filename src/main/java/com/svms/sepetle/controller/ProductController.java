package com.svms.sepetle.controller;

import com.svms.sepetle.model.Category;
import com.svms.sepetle.model.Product;
import com.svms.sepetle.service.CategoryService;
import com.svms.sepetle.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class ProductController {

    private final ProductService productService;
    CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @RequestMapping("/{category}")
    public String filterProducts(@PathVariable("category") String productCategory, Model model) {

        Category category = categoryService.findByName(productCategory);

        List<Product> productsByCategory = productService.getProductsByCategory(category);

        System.out.println(productsByCategory);

        model.addAttribute("products", productsByCategory);

        return "home";
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

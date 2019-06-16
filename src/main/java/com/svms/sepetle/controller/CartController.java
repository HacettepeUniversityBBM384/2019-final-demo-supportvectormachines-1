package com.svms.sepetle.controller;

import com.svms.sepetle.service.CartService;
import com.svms.sepetle.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartController {
    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("/shopping_cart");
        modelAndView.addObject("products", cartService.getProductsInCart());
        modelAndView.addObject("total", cartService.getTotal().toString());
        return modelAndView;
    }

    @PostMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(cartService::addProduct);
        return new ModelAndView("redirect:/shoppingCart");
    }

    @PostMapping("/shoppingCart/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(cartService::removeProduct);
        return new ModelAndView("redirect:/shoppingCart");
    }

    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout() {
        try {
            cartService.checkout();
        } catch (Exception e) {
            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
        }
        return shoppingCart();
    }

}

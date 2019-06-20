package com.svms.sepetle.controller;

import com.svms.sepetle.model.Category;
import com.svms.sepetle.model.Order;
import com.svms.sepetle.model.Product;
import com.svms.sepetle.model.User;
import com.svms.sepetle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@SessionAttributes("Order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final AppController globalController;
    private final ProductService productService;
    private final CategoryService categoryService;


    @Autowired
    public OrderController(OrderService orderService, UserService userService, CartService cartService, AppController globalController, ProductService productService, CategoryService categoryService) {
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
        this.globalController = globalController;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/order_form", method = RequestMethod.GET)
    public ModelAndView order() {
        System.out.println("ORDER GET");
        ModelAndView modelAndView = new ModelAndView();
        Order order = new Order();
        Map<Product,Integer> products = cartService.getProductsInCart();

        modelAndView.addObject("order", order);
        modelAndView.addObject("products", products);
        modelAndView.addObject("total", cartService.getTotal().toString());
        modelAndView.setViewName("/checkout");
        return modelAndView;
    }

    @RequestMapping(value = "/post_order", method = RequestMethod.POST)
    public String postOrder(@ModelAttribute("order") final Order order,
                                  final RedirectAttributes redirectAttributes) {

        System.out.println("ORDER POST");

        System.out.println();
        List<Product> productList = new ArrayList<>(cartService.getProductsInCart().keySet());
        order.setProducts(productList);


        order.setUser(globalController.getLoginUser());
        order.setPurchase_date(new Date());
        order.setPrice(cartService.getTotal());
        redirectAttributes.addFlashAttribute("Order", order);

        return "redirect:payment";
    }

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String paymentForm(@ModelAttribute("Order") final Order order, final Model model){
        model.addAttribute("reqOrder", order);
        System.out.println("PAYMENT GET");

        // System.out.println(orderService.findAllByUser(o_loginUser.get()));

        return "payment";

    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public ModelAndView completePayment(@ModelAttribute("Order") final Order order) {
        System.out.println("PAYMENT POST");
        cartService.checkout();
        System.out.println(order);
        orderService.saveOrder(order);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("my_orders");

        int id = globalController.getLoginUser().getId();
        User user = userService.findById(id);

        Collection<Order> orders = user.getOrders();
        modelAndView.addObject("orders", orders);

        return modelAndView;
    }


}


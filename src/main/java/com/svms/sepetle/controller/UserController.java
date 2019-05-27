package com.svms.sepetle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.svms.sepetle.model.User;
import com.svms.sepetle.service.UserService;
import com.svms.sepetle.utils.Encoder;
import com.svms.sepetle.utils.Roles;

@Controller
public class UserController {


    @Autowired
    AppController globalController;

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("rUser", new User());
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("rUser", new User());
        return "register";
    }

    @RequestMapping(value = {"/user/register"}, method = RequestMethod.POST)
    public String register(@ModelAttribute("rUser") User rUser,
                           final RedirectAttributes redirectAttributes) {
    	
    	System.out.println(rUser.toString());
        User user = userService.findByUserName(rUser.getUsername());
        if (user != null) {
            redirectAttributes.addFlashAttribute("saveUser", "exist-name");
            return "redirect:/register";
        }
        user = userService.findByEmail(rUser.getEmail());
        if (user != null) {
            redirectAttributes.addFlashAttribute("saveUser", "exist-email");
            return "redirect:/register";
        }

        if(rUser.getPassword().length() < 8) {
        	 redirectAttributes.addFlashAttribute("saveUser", "invalid-pass");
             return "redirect:/register";	
        }
        
        rUser.setPassword(Encoder.getInstance().passwordEncoder.encode(rUser.getPassword()));
        rUser.setRole(Roles.ROLE_USER.getValue());

        if (userService.save(rUser) != null) {
            redirectAttributes.addFlashAttribute("saveUser", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveUser", "fail");
        }

        return "redirect:/register";
    }
	
    @RequestMapping(value="/help", method = RequestMethod.GET)
    public String showHelpPage(Model model) {
    	
	      return "help";
    }
	
	
    
    @RequestMapping(value="/user/edit", method = RequestMethod.GET)
    public String showUpdateForm(Model model) {
    	  int id = globalController.getLoginUser().getId();
	      User user = userService.findById(id);
	      model.addAttribute("user", user);
	      model.addAttribute("userId", id);
	      return "editUser";
    }
    
    @PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") int id, @ModelAttribute("user") User user, Model model,
                             final RedirectAttributes redirectAttributes) {
    	  System.out.println(user.toString());
    	  
    	  User oldUser = userService.findById(id);
    	  String oldPassword = oldUser.getPassword();
	      user.setRole(oldUser.getRole());
		  userService.update(user);
	      System.out.println(user.toString());
	      if(user.getPassword() == "") {
	    	  user.setPassword(oldPassword);
	      } else {
	    	  if(user.getPassword().length() < 8) {
	         	 redirectAttributes.addFlashAttribute("err", "invalid-pass");
	         	 user.setPassword(oldPassword);
	         	 userService.save(user);
	             return "redirect:/user/edit";	
	         }
	    	  user.setPassword(Encoder.getInstance().passwordEncoder.encode(user.getPassword()));
	      }
	      
	      userService.save(user);
	      
	      System.out.println(user.toString());
		  model.addAttribute("msg", "success");
		  return "editUser";
	  }
    


}

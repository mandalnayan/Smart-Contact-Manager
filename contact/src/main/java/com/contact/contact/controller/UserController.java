package com.contact.contact.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contact.contact.Repository.UserRepository;
import com.contact.contact.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/index") 
    public String dashboard(Model model, Principal principal) {
       model.addAttribute("User - Smart Contact Manage");
       
       String userName = principal.getName();

       User user = userRepository.getUserByUserName(userName);
      model.addAttribute(user);

       return "user_dashboard/index";
    }
}

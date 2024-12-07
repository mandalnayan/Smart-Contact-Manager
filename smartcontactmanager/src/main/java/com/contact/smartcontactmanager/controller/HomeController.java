package com.contact.smartcontactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController { 

   @RequestMapping("/home")  
   public String homePage(Model model) {
      model.addAttribute("title", "Home - Smart Contact Manager");
      return "home";
   }  

   @RequestMapping("/about")  
   public String aboutPage(Model model) {
      model.addAttribute("title", "About - Smart Contact Manager");
      return "about";
   }

   @RequestMapping("/signup") 
   public String signUp(Model model) {
      model.addAttribute("title", "Sign - Smart Contact Manager");
      return "signup";
   }

   @RequestMapping("/login") 
   public String login(Model model) {
      model.addAttribute("title", "Sign - Smart Contact Manager");
      return "login";
   }

}

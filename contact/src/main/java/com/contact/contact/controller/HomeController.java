package com.contact.contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contact.contact.Repository.UserRepository;
import com.contact.contact.entities.User;
import com.contact.contact.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class HomeController { 
   @Autowired
   private UserRepository userRepository;
   
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

   @RequestMapping("/")
   public String home(Model model) {
      model.addAttribute("title", "Home - Smart Contact Manager");
    return "home";
   }
 
   @RequestMapping("/about")
   public String about(Model model) {
      model.addAttribute("title", "About - Smart Contact Manager");
      return "about";
   }

   @RequestMapping("/signup")
   public String singUp(Model model) {
      model.addAttribute("title", "Sign Up - Smart Contact Manage");
      model.addAttribute("user", new User());
      return "signup";
   }

   // Handler for registering user    
   @PostMapping("/do_register")
   public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value="agreement", defaultValue="false")boolean agreement, Model model, HttpSession session) {
     
      try {
         if (result.hasErrors()) {
            System.out.println("Error " + result.toString());
            model.addAttribute("user", user);
            return "signup";
         }
      if (!agreement) {
         System.out.println("You have not agreed the terms and conditions");
         throw new Exception("You have not agreed the terms and conditions");
      }      

      user.setRole("ROLE_USER");
      user.setEnabled(true);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      System.out.println("Password: " + user.getPassword());

      System.out.println("Agreement " + agreement);
      System.out.println("USER " + user);

      User savedUser = this.userRepository.save(user);

      model.addAttribute("user", new User());
      session.setAttribute("message", new Message("Successfully Registered !!","alert-success"));
   }
   catch(Exception e) {
      e.printStackTrace();
      model.addAttribute("user", user);
      session.setAttribute("message", new Message("Something went wrong !!" + e.getMessage().toString(), "alert-danger"));
   }
   return "signup";
   }

   @RequestMapping("/login")
   public String signIn(Model model) {
      model.addAttribute("title", "Login - Smart Contact Manage");
     
      return "login";
   }
}

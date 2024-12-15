package com.contact.smartcontactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.smartcontactmanager.dao.UserRepository;
import com.contact.smartcontactmanager.entities.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController { 

@Autowired
 private UserRepository userRepository;
@Autowired
 private BCryptPasswordEncoder passwordCoder;

   @RequestMapping("/")  
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
      model.addAttribute("user", new User());
      return "signup";
   }

   @RequestMapping("/login") 
   public String login(Model model) {
      model.addAttribute("title", "Sign - Smart Contact Manager");
      return "login";
   }

   @RequestMapping(value="/do_register", method=RequestMethod.POST) 
   public String registerUser(@Valid @ModelAttribute User user, BindingResult result, @RequestParam(value="agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {       
    try{
  
         if (result.hasErrors()) {
            System.out.println("\n\nYour error ERROR Data: \n");
            return "signup";
         }
         
         if (!agreement) {
            System.out.println("Agrement value: " + agreement);
            throw new Exception("You must accept terms and conditions.");
         }
  
         user.setRole("ROLE_USER");
         String enPass = passwordCoder.encode(user.getPassword());
         System.out.println("Encoded Password: " + enPass);
         user.setPassword(enPass);
         user.setEnabled(true);
         model.addAttribute("user", new User());
         
         User Userresult = this.userRepository.save(user);
         
         model.addAttribute("success", "You're Registertation is Successfull!");
         return "signup";         
      } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            model.addAttribute("errorMessage", "Something went wrong! " + e.getMessage());
           return "signup";
      }
     
   }

}

package com.contact.smartcontactmanager.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contact.smartcontactmanager.dao.UserRepository;
import com.contact.smartcontactmanager.dao.ContactRepository;
import com.contact.smartcontactmanager.entities.Contact;
import com.contact.smartcontactmanager.entities.User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired 
    private ContactRepository contactRepository;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String userName = principal.getName();
        // System.out.println("Logined User Name: " + userName);
        
        // Getting User from user name
        User user = userRepository.getUserByUserName(userName);
        System.out.println("Logined User : " + user);

        model.addAttribute("user", user);
    }
    
    @RequestMapping("/index") 
    public String dashboard(Model model) { 

        return "nonAdmin/user_dashboard";       
    }

    @RequestMapping("/addContact")
    public String addContact(Model model) {
        model.addAttribute("title", "Contact Add");
        model.addAttribute("contact", new Contact());
        return "nonAdmin/AddContact";
    }    

    @RequestMapping(value="/process-contact", method=RequestMethod.POST)
    public String processContact(@Valid @ModelAttribute Contact contact, BindingResult data, Model model) {
        try{
            if (data.hasErrors()) {
    model.addAttribute("err_message", "Something went wrong!");
        return "nonAdmin/AddContact";
            }
            
            contact.setImage("default.png");
            contactRepository.save(contact);
            model.addAttribute("suc_message", "Added successfully!");
            
        } catch(Exception e) {
            System.out.println("Error " + e.getMessage());
            model.addAttribute("contact", contact);
            model.addAttribute("err_message", "Something went wrong!" + e.getMessage());
        }
        return "nonAdmin/AddContact";
    }
}
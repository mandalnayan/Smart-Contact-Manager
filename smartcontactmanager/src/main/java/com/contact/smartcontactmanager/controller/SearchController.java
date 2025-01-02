package com.contact.smartcontactmanager.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import com.contact.smartcontactmanager.dao.ContactRepository;
import com.contact.smartcontactmanager.dao.UserRepository;
import com.contact.smartcontactmanager.entities.User;
import com.contact.smartcontactmanager.entities.Contact;


@RestController
public class SearchController {
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private ContactRepository contactRepository;

    // search handler
    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal) {
     System.out.println("Query: " + query);

     User user = userRepository.getUserByUserName(principal.getName());
     List<Contact> contacts = contactRepository.findByNameContainingAndUser(query, user);
     return ResponseEntity.ok(contacts);
    }
}

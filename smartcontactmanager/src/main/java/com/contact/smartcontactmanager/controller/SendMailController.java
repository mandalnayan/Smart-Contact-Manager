package com.contact.smartcontactmanager.controller;

import org.springframework.web.bind.annotation.RestController;

import com.contact.smartcontactmanager.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SendMailController {
    @Autowired
    EmailService sendMailService; 

    @GetMapping("/send-mail")
    public String sendMail() { 
        sendMailService.sendMail("nayankm99@gmail.com", "Test Mail", "I am testing the email");
        return "Send successfully";
    }
}

package com.contact.smartcontactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailsender;
   @Value("$(Spring.mail.username)")
   private String from;
   
    public boolean sendMail(String to, String subject, String message) {
     SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
     simpleMailMessage.setTo(to);
     simpleMailMessage.setFrom(from);
     simpleMailMessage.setSubject(subject);
     simpleMailMessage.setText(message);
     javaMailsender.send(simpleMailMessage);
     return true; 
    }
}

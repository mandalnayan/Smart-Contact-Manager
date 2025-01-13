package com.contact.smartcontactmanager.controller;

import java.security.Principal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import com.contact.smartcontactmanager.dao.UserRepository;
import com.contact.smartcontactmanager.entities.User;
import com.contact.smartcontactmanager.service.EmailService;

@Controller
public class ForgetPassController {
    @Autowired
    EmailService mailService;
     
    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;
    
    String otp;
    
    @RequestMapping("/forget-password")
    public String EnteredEmail(Model model) {
        model.addAttribute("title", "Change Password");
        return "forget_pass";
    }

    @PostMapping("/send-otp") 
    public String generateOtp(@RequestParam("email") String email, Model model, HttpSession session) {       
        try {
        // OTP Generating
        Random random = new Random();
        otp = random.nextLong(999999) + "";

        // Save information 
        session.setAttribute("email", email);
        
        // Sending OTP in entered mail
        String subject = "OTP Varification for forget password";
        String message = "This OTP will be valid for 10 min. OTP: " + otp;

       boolean res = mailService.sendMail(email, subject, message);
       if (res) {
           model.addAttribute("suc_mess", "OTP send successfully! Please check your email");
           return "verify_pass";
       } else {  
        throw new Exception();
       }
     } catch(Exception e) {
        model.addAttribute("err_mess", "Please entered Valid Registered email Id");
        return "forget_pass";
       }       

    }

    @PostMapping("/otp-varify")
    public String otpVerify(@RequestParam("otp") String user_otp, Model model) {
        try {
        if (user_otp.equals(otp)) {
            return "Enter_Password";
        } else {
            throw new Exception();
        }
    } catch(Exception e) {
        model.addAttribute("err_mess", "OTP not matched, Please entere valid OTP!");
        return "verify_pass";
    }
    }

    @PostMapping("/change-password")
    public String changeForgetPassword(@RequestParam("password1") String pass1, @RequestParam("password2") String pass2, Model model, Principal principal, HttpSession session) {
        try {
        if (pass1.equals(pass2)) {
            String userName = (String) session.getAttribute("email");
            User user = userRepository.getUserByUserName(userName);
            if (user == null) {
                model.addAttribute("err_mess", "User doesn't exit with this email please enter valid email id");
                return "forget_pass";      
            }
           String encryptedPass = encoder.encode(pass1);
           user.setPassword(encryptedPass);
           userRepository.save(user);

           return "redirect:/login?change_pass=zPassword%0Changed%20";
         } else { 
            throw new Exception();
        } 
    }
        catch (Exception e) {
            model.addAttribute("err_mess", "Both Entered password not matched, Please enter correct password!");
            return "Enter_Password";            
        }
    }
}

package com.contact.contact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.contact.contact.Repository.UserRepository;
import com.contact.contact.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.getUserByUserName(username);
       
        System.out.println("\nUser Details: " + user);

       if (user == null) {
            throw new UsernameNotFoundException("User could not found!!");
       }
       
       CustomUserDetails customUserDetails = new CustomUserDetails(user);
       return customUserDetails;
    } 
}

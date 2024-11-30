package com.contact.contact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// @EnableWebSecurity
@Configuration
public class SecurityConfiguration{  
    
    @Bean
    public UserDetailsService getUserDetailService() {
        return new UserDetailsServiceImpl();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
          DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
          daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
          daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        
          return daoAuthenticationProvider;
        }
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests().
        requestMatchers("/admin/**").hasRole("ADMIN").
        requestMatchers("/user/**").hasRole("USER").
        requestMatchers("/**").permitAll().and()
        .formLogin()
        .loginPage("/login")   // loginPage("/customizePath of login")
        .loginProcessingUrl("/dologin")
        .defaultSuccessUrl("/user/index");
        // .failureUrl("/fail-login");
        return http.build();
    } 

    // @Bean
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.authorizeHttpRequests(
    //         auth -> auth.requestMatchers("/admin/**").hasRole("ADMIN")
    //     .requestMatchers("/user/**").hasRole("USER")
    //     .requestMatchers("/**").permitAll().and().formLogin().loginPage("/login")
    //     ).build() ;          
    // }
}

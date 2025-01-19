package com.contact.smartcontactmanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartcontactmanagerApplication {
  @Value("${server.port}")
  static String port;
	public static void main(String[] args) {
		SpringApplication.run(SmartcontactmanagerApplication.class, args);
		System.out.println("\n Spring boot is running on port " + port);    
	}
}

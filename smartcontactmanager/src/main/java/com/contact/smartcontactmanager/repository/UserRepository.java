package com.contact.smartcontactmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contact.smartcontactmanager.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    
}

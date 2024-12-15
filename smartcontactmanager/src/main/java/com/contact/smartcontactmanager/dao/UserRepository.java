package com.contact.smartcontactmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.smartcontactmanager.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    @Query("SELECT u from User as u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);     
    
}



package com.contact.smartcontactmanager.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contact.smartcontactmanager.entities.Contact;
import com.contact.smartcontactmanager.entities.User;

public interface ContactRepository extends JpaRepository<Contact, Integer>{
    
    // Pageable contains two information: 1) currentPage & 2) contact per page
    @Query("Select C from Contact as C where C.user.id=:userId")
   public Page<Contact> getContactByUserId(int userId, Pageable pePageable);
   
//    Find by name keyword of specific user.
  public List<Contact> findByNameContainingAndUser(String name, User user);
}

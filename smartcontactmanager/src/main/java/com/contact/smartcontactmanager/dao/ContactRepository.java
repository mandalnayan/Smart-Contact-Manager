package com.contact.smartcontactmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contact.smartcontactmanager.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer>{
    
}

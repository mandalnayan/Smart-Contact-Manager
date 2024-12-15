package com.contact.smartcontactmanager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table; 

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

@Entity
@Table(name="CONTACT")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cId;
    private String name;
    private String secondName;
    @Size(min=10, max=12)
    private String phones;
    @Email(message="Email is not valid!")
    private String email;
    private String work;
    private String description;
    private String image;
    
    @ManyToOne()
    private User user; 

public User getUser() {
    return this.user;
}

public void setUser(User user) {
    this.user = user;
}

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getcId() {
        return cId;
    }
    public void setcId(int cId) {
        this.cId = cId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getWork() {
        return work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhones() {
        return phones;
    }
    public void setPhones(String phones) {
        this.phones = phones;
    }

}

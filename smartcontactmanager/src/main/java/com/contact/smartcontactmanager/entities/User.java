package com.contact.smartcontactmanager.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table (name="USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Size(min = 2, max = 50, message="min 2 and max 50 character is required !!")
    @NotBlank(message = "Name field is required !")
    private String name;
    
    @Column(unique = true)   
    @Email(message="Email is not valid !")
    @NotBlank(message="Email field is required !")
    private String email;
    @NotBlank(message = "Password field is required !")
    private String password;
    private String role;
    private boolean enabled;
    private String imageUrl;
    @Column(length=500)
    private String about; 

    // one to many (One use has many contact)
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="user")
    private List<Contact> contacts = new ArrayList<>();
    
    public List<Contact> getContacts() {
        return this.contacts;
    }
    
    public void setContacts(List<Contact> contacts) {
         this.contacts = contacts;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }  

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
                + ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", about=" + about + ", contacts=" + contacts
                + "]";
    }
}

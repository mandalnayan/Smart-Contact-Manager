package com.contact.smartcontactmanager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.smartcontactmanager.dao.UserRepository;
import com.contact.smartcontactmanager.dao.ContactRepository;
import com.contact.smartcontactmanager.entities.Contact;
import com.contact.smartcontactmanager.entities.User;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    BCryptPasswordEncoder encoder;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String userName = principal.getName();
        // System.out.println("Logined User Name: " + userName);

        // Getting User from user name
        User user = userRepository.getUserByUserName(userName);
        System.out.println("Logined User : " + user);

        model.addAttribute("user", user);
    }

    @RequestMapping("/index")
    public String dashboard(Model model) {
        return "nonAdmin/user_dashboard";
    }

    @RequestMapping("/addContact")
    public String addContact(Model model) {
        model.addAttribute("title", "Contact Add");
        model.addAttribute("contact", new Contact());
        return "nonAdmin/AddContact";
    }

    @RequestMapping(value = "/process-contact", method = RequestMethod.POST)
    public String processContact(@Valid @ModelAttribute Contact contact,
            BindingResult data, @RequestParam("profileImage") MultipartFile file, Principal principal, Model model) {
        try {
            if (data.hasErrors()) {
                model.addAttribute("err_message", "Something went wrong!");
                return "nonAdmin/AddContact";
            }

            // Image processing

            if (file.isEmpty()) {
                System.out.println("File is empty");
                contact.setImage("default.jpg");
            } else {
                contact.setImage(file.getOriginalFilename());

                File saveFile = new ClassPathResource("static/image").getFile(); // to get file location to add image in
                                                                                 // local dir

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image has uploaded");
            }

            String userName = principal.getName();
            User user = userRepository.getUserByUserName(userName);
            contact.setUser(user);
            // user.getContacts().add(contact);

            this.contactRepository.save(contact); // save using contactRepository
            // this.userRepository.save(user);
            model.addAttribute("suc_message", "Added successfully!");
            model.addAttribute("contact", new Contact());
        } catch (Exception e) {
            // System.out.println("Contact Details: " + contact);
            System.out.println("Error " + e.getMessage());
            model.addAttribute("contact", contact);
            model.addAttribute("err_message", "Something went wrong! " + e.getMessage());
        }
        return "nonAdmin/AddContact";
    }

    @GetMapping("/view-contacts/{page}")
    public String viewContacts(@PathVariable("page") Integer page, Model model, Principal principal) {
        User user = userRepository.getUserByUserName(principal.getName());
        model.addAttribute("title", "View Contact");
        int con_per_page = 5;
        Pageable pageable = PageRequest.of(page, con_per_page);

        Page<Contact> contacts = contactRepository.getContactByUserId(user.getId(), pageable);
        // long n = contacts.getTotalElements();        // to know total element it contained

        model.addAttribute("contacts", contacts);
        model.addAttribute("totalPages", contacts.getTotalPages());
        model.addAttribute("currentPage", page);

        return "nonAdmin/viewContacts";
    }

    @GetMapping("/{cId}/{curPage}/contact")
    public String showContact(@PathVariable("cId") Integer cId, @PathVariable("curPage") Integer PNo, Model model,
            Principal principal) {
            /* Add title   */
            model.addAttribute("title", "Contact");
  
            try {
            Optional<Contact> optionalContact = contactRepository.findById(cId);
            Contact contact = optionalContact.get();

            if (contact == null) {
                throw new Exception("Contact Not found!");
            }            

            User user = userRepository.getUserByUserName(principal.getName());
            if (user.getId() == contact.getUser().getId()) {
                model.addAttribute("contact", contact);
                model.addAttribute("currentPage", PNo);
                // model.addAttribute("suc", "Success!");
            } else {
                throw new Exception("You don't have access!");
            }
        } catch (Exception e) {
            model.addAttribute("err_mes", "Something went wrong. " + e.getMessage());
        }
        return "nonAdmin/showContact";
    }
    
   @GetMapping("/profile")
   public String viewContact(Model model, Principal principal) {
    model.addAttribute("title", "Profile");
    User user = userRepository.getUserByUserName(principal.getName());
    model.addAttribute("user", user);

    return "nonAdmin/profile";
   }

    @GetMapping("/{cId}/update")
    public String updateContact(@PathVariable("cId")Integer cId, Model model) {
         model.addAttribute("title", "Update");
         Optional<Contact>optionalContact = contactRepository.findById(cId);
         Contact contact = optionalContact.get();
         model.addAttribute("contact", contact);
         
        return "nonAdmin/updateContact";
    }

    @PostMapping("/update-contact")
    public String updateContactSave(@Valid @ModelAttribute Contact contact, BindingResult data, @RequestParam("profileImage") MultipartFile file, Principal principal, Model model) {
        try {
            System.out.println("\n Updating ............... \n");
            // Image processing
            Contact oldContact = this.contactRepository.findById(contact.getcId()).get();
            System.out.println("\nContact Id: \n" + oldContact.getcId());

            if (data.hasErrors()) {
                model.addAttribute("err_message", "Something went wrong!");
                contact.setImage(oldContact.getImage());

                return "nonAdmin/updateContact";
            }

            if (file.isEmpty()) {
                System.out.println("File is empty");
                contact.setImage(oldContact.getImage());
                System.out.println("Image : ..." + oldContact.getImage());
            } else {

                // Delete the old image
                if(oldContact.getImage() != null) {
                   File deleteFilePath = new ClassPathResource("static/image").getFile();
                   File deleteFile = new File(deleteFilePath, oldContact.getImage());
                   deleteFile.delete();
                }

                contact.setImage(file.getOriginalFilename());

                File saveFile = new ClassPathResource("static/image").getFile(); // to get file location to add image in
                                                                                 // local dir

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image has uploaded");
            }

            String userName = principal.getName();
            User user = userRepository.getUserByUserName(userName);
            contact.setUser(user);
            this.contactRepository.save(contact); // save using contactRepository

            model.addAttribute("suc_message", "Updated successfully!");
            model.addAttribute("contact", contact);
            return "nonAdmin/showContact";
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            model.addAttribute("contact", contact);
            model.addAttribute("err_message", "Something went wrong! " + e.getMessage());
            return "nonAdmin/updateContact";
        }
    }

    @GetMapping("/{cId}/{curPage}/delete")
    public String deleteUpdate(@PathVariable("cId")Integer cId, @PathVariable("curPage")Integer curPage ,Model model, Principal principal) {
        try {
          Optional<Contact>optContact = contactRepository.findById(cId);
          Contact contact = optContact.get();

          
        // Delete image file
        File filePath = new ClassPathResource("static/image").getFile();
        File deleteFile = new File(filePath, contact.getImage());

        deleteFile.delete();
        
        //   Unlink contact with user
        contact.setUser(null);
        User user = this.userRepository.getUserByUserName(principal.getName());
        user.getContacts().remove(contact);

        userRepository.save(user);
        

          model.addAttribute("suc_mes", "Contact Deleted Successfully!");
          return "redirect:/user/view-contacts/" + curPage; 
        //   return "redirect:/user/view-contacts/0"; 
        } catch(Exception e) {
            model.addAttribute("err_mes", "Something went wrong. " + e.getMessage());            
            return "redirect:/user/view-contacts/" + curPage;
        }
    }
    
    @GetMapping("/setting")
    public String setting() {
        return "nonAdmin/setting";
    }

    @PostMapping("/setting-handler")
    public String changePassword(@RequestParam("old-password") String old_pass, @RequestParam("new-password") String new_pass, Principal principal, Model model) {
       try {
        User user = userRepository.getUserByUserName(principal.getName());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(old_pass, user.getPassword())) {
            System.out.println("Password matched!");
            String decryptedPassword = encoder.encode(new_pass);
            user.setPassword(decryptedPassword);
            userRepository.save(user);
            model.addAttribute("suc_mes", "Password changed successfully!");
        } else {
            // System.out.println("Sorry, You have to try");    
            model.addAttribute("err_mes", "Password Not matched! Please Enter correct");
            throw new Exception();        
        }
    } catch(Exception e) {
        model.addAttribute("err_mes", "Password Not matched!");
    }
        return "nonAdmin/setting";
    }
    
}
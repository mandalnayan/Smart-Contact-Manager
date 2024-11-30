package com.contact.contact.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.contact.contact.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
 
    @Query(value = "Select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);
}

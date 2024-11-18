package com.coms309.demo2.controller;

import java.util.List;
import java.util.Optional;

import com.coms309.demo2.entity.User;
import com.coms309.demo2.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fury Poudel and Madeleine Carydis
 * Creates and updates a user
 */


@RestController
@Tag(name = "Signup Controller", description = "Handles user registration and signup operations")
public class SignupController {

    @Autowired
    UserRepository repository;

    /**
     * Signup method; creates a User
     * @param user  new User
     * @return new User
     */
    @Operation(summary = "Creates a User", description = "allows creation of a user")
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        // Find user by email to avoid loading all users into memory
        Optional<User> existingUser = repository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        user = repository.save(user); // save new user, get new ID
        return user; // Per REST standards, we return the new object
    }

    /**
     * Get user by ID, handling case if user is not found
     * @param id user id
     * @return user
     */
    @Operation(summary = "Get user by ID", description = "Gets user by ID and handles case if user is not found")
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        /// <summary>My super duper data</summary>
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            /// <summary>The unique identifier</summary>
            User foundUser = user.get();
            foundUser.setPassword(null); // Hide password
            return foundUser;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Get all users, hiding their passwords
     * @return a list of all users with passwords obscured
     */
    @Operation(summary = "Get all users", description = "Get all users, hiding their passwords and return a list of all users with passwords obscured")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        for (User cUser : users) {
            cUser.setPassword(null); // Hide passwords
        }
        return users;
    }

    // // Change email
    // @PutMapping("/users/{id}/email")
    // public String changeEmail(@PathVariable Long id, @RequestBody String email) {
    //     Optional<User> userOpt = repository.findById(id);
    //     if (userOpt.isPresent()) {
    //         User user = userOpt.get();
    //         // Set the new email
    //         user.setEmail(email);
    //         // Save the updated user back to the repository
    //         repository.save(user);
    //         return "Email updated successfully";
    //     } else {
    //         return "User not found";
    //     }
    // }

    /**
     * Delete user by ID
     * @param id id of user
     * @return informative message
     */
    @Operation(summary = "Delete user by ID", description = "Delete user by ID")
    @DeleteMapping("/users/{id}")
    public String removeUser(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id); // Delete user
            return "OK";
        } else {
            return "User not found";
        }
    }

    @Operation(summary = "Delete all users", description = "Delete all users")
    @DeleteMapping("/users")
    public String removeUsers() {
      repository.deleteAll();
      return "all users deleted";
    }
}

package com.coms309.demo2.controller;

import java.util.List;
import java.util.Optional;

import com.coms309.demo2.entity.User;
import com.coms309.demo2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignupController {

    @Autowired
    UserRepository repository;

    // Signup method with email uniqueness check
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

    // Get user by ID, handling case if user is not found
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setPassword(null); // Hide password
            return foundUser;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Get all users, hiding their passwords
    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        for (User cUser : users) {
            cUser.setPassword(null); // Hide passwords
        }
        return users;
    }

    // Change email
    @PutMapping("/users/{id}/email")
    public String changeEmail(@PathVariable Long id, @RequestBody String email) {
        Optional<User> userOpt = repository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Set the new email
            user.setEmail(email);
            // Save the updated user back to the repository
            repository.save(user);
            return "Email updated successfully";
        } else {
            return "User not found";
        }
    }

    // Delete user by ID
    @DeleteMapping("/users/{id}")
    public String removeUser(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id); // Delete user
            return "OK";
        } else {
            return "User not found";
        }
    }

    @DeleteMapping("/users")
    public String removeUsers() {
      repository.deleteAll();
      return "all users deleted";
    }
}

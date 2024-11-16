package com.coms309.demo2.controller;

import com.coms309.demo2.entity.User;
import com.coms309.demo2.entity.Vet;
import com.coms309.demo2.repository.UserRepository;
import com.coms309.demo2.repository.VetsRepo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Fury Poudel and Madeleine Carydis
 * Authenticates a log on request from a user
 */
@RestController
@Tag(name = "Login Controller", description = "Handles user authentication and login operations")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VetsRepo vetsRepo;

    /**
     * Authenticates a login request and logs them in
     * @param loginRequest username and password (login data)
     * @return the view to display and the id
     */
    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest) {
        // Retrieve user by email
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Check if the password matches
            if (user.getPassword().equals(loginRequest.getPassword())) {

                // Check if the user is an Admin
                if (loginRequest.getEmail().startsWith("admin")) {
                    return "admin_view" + "," + user.getId();  // Admin View
                }

                // Check if the user is a Vet
                Optional<Vet> vetOptional = vetsRepo.findByVetEmail(user.getEmail()); // Update method call
                if (vetOptional.isPresent()) {
                    return "vet_view" + "," + user.getId();  // Vet View
                }

                return "client_view" + "," + user.getId();  // Client View (default for non-vets, non-admins)

            } else {
                return "Username or password is incorrect";
            }

        } else {
            return "User not found";
        }
    }
}

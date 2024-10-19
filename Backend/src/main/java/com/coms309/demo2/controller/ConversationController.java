package com.coms309.demo2.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.coms309.demo2.entity.Conversation;
import com.coms309.demo2.entity.User;
import com.coms309.demo2.repository.ConversationRepository;
import com.coms309.demo2.repository.UserRepository;
import com.coms309.demo2.repository.VetsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ConversationController {
    //Post, Put, Delete all taken care of by websocket
    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VetsRepo vetRepository;

    //get list of prior conversations
    @GetMapping("/users/{id}/conversations")
    public List<Conversation> getAllConversations(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Conversation> conversations = conversationRepository.findByUser(user);
            return conversations;
        }
        else {
            throw new RuntimeException("User does not exist :(");
        }
    }
    
}

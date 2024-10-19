package com.coms309.demo2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.coms309.demo2.entity.Message;
import com.coms309.demo2.entity.User;
import com.coms309.demo2.entity.Vet;
import com.coms309.demo2.repository.MessageRepository;
import com.coms309.demo2.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;


@RestController
public class ChatController {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;

    //Posting done via websocket

    //get users/vets the person has chatted with before
    @GetMapping("/user/{id}/chat")
    public List<Vet> getUserChats(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Message> vets = messageRepository.findByUser(user);

            return new String();
        }
        else {
            throw new RuntimeException("User not found");
        }
    }

    @GetMapping("/vet/{id}/chat")
    public List<User> getVetChats(@RequestParam String param) {
        return new String();
    }

    //get conversations with selected person
    @GetMapping("path")
    public String getConversation(@RequestParam String param) {
        return new String();
    }
    
    
    
    
}

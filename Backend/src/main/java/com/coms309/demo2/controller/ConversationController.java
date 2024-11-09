package com.coms309.demo2.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.coms309.demo2.entity.Conversation;
import com.coms309.demo2.entity.ConversationKey;
import com.coms309.demo2.entity.User;
import com.coms309.demo2.entity.Vet;
import com.coms309.demo2.repository.ConversationRepository;
import com.coms309.demo2.repository.UserRepository;
import com.coms309.demo2.repository.VetsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class ConversationController {
    //Post, Put, Delete all taken care of by websocket
    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VetsRepo vetRepository;

    //get list of prior conversations for a user
    @GetMapping("/users/{id}/conversations")
    public List<Conversation> getAllConversationsUser(@PathVariable Long id) {
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

    //get list of prior conversations for a vet
    @GetMapping("/vet/{id}/conversations")
    public List<Conversation> getAllConversationsVet(@PathVariable Integer id) {
        Optional<Vet> vetOptional = vetRepository.findById(id);
        if (vetOptional.isPresent()) {
            Vet vet = vetOptional.get();
            List<Conversation> conversations = conversationRepository.findByVet(vet);
            return conversations;
        }
        else {
            throw new RuntimeException("Vet does not exist :(");
        }
    }
    
    //get all messages for a conversation with a vet
    @GetMapping("/users/{idUser}/conversations/{idVet}")
    public Conversation getAllMessagesUser(@PathVariable Long idUser, @PathVariable Integer idVet) {

        Optional<User> userOptional = userRepository.findById(idUser);
        Optional<Vet> vetOptional = vetRepository.findById(idVet);
        if (userOptional.isPresent() && vetOptional.isPresent()) {
            User user = userOptional.get();
            Vet vet = vetOptional.get();

            Optional<Conversation> conversationOptional = conversationRepository.findById(new ConversationKey(user, vet));
            if (conversationOptional.isPresent()) {
                Conversation conversation = conversationOptional.get();
                return conversation;
            }
            else {
                throw new RuntimeException("Conversation does not exist");
            }
        }
        else {
            throw new RuntimeException("User and/or Vet does not exist :(");
        }
    }


    //get all messages for a conversation with a user
    @GetMapping("/vet/{idVet}/conversations/{idUser}")
    public Conversation getAllMessagesVet(@PathVariable Integer idVet, @PathVariable Long idUser) {
        Optional<Vet> vetOptional = vetRepository.findById(idVet);
        Optional<User> userOptional = userRepository.findById(idUser);
        if (vetOptional.isPresent() && userOptional.isPresent()) {
            Vet vet = vetOptional.get();
            User user = userOptional.get();
            Optional<Conversation> conversationOptional = conversationRepository.findById(new ConversationKey(user, vet));
            if (conversationOptional.isPresent()) {
                Conversation conversation = conversationOptional.get();
                return conversation;
            }
            else {
                throw new RuntimeException("Conversation does not exist");
            }
        }
        else {
            throw new RuntimeException("Vet and/or User does not exist :(");
        }
    }

}

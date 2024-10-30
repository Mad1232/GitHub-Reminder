package com.coms309.demo2.repository;

import com.coms309.demo2.websocket.GlobalChatServer;
import com.coms309.demo2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class RepositoryInjector {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        GlobalChatServer.setUserRepository(userRepository);
    }
}

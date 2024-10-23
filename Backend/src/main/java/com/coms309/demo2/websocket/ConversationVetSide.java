package com.coms309.demo2.websocket;

import org.springframework.stereotype.Component;

import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/vet/{vetid}/conversations/{userid}")
@Component
public class ConversationVetSide {
    
}

package com.coms309.demo2.websocket;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.coms309.demo2.repository.ConversationRepository;
import com.coms309.demo2.repository.MessageRepository;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/users/{userid}/conversations/{vetid}")
@Controller
public class ConversationUserSide {
    private static MessageRepository messageRepository;
    private static ConversationRepository conversationRepository;

    @Autowired
    public void setUpRepositories(MessageRepository messageRepository, ConversationRepository conversationRepository) {
        ConversationUserSide.messageRepository = messageRepository;
        ConversationUserSide.conversationRepository = conversationRepository;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userid") Long userid, @PathParam("vetid") Integer vetid) {
        // 1. Check for existing conversation in database
        //     a. Create if it doesn't exist
        // 2. Use the conversation key to check with vet side to find an ongoing conversation
        //     a. Call a function, make link (give it session and convo key)
        // 3. Make a map to store link between our session and the conversation key
        // 4. Send all messages in the conversation
    }

    @OnMessage
    public void onMessage(String session, String message) throws IOException {
        // 1. Save message to repository
        // 2. Tell vet side
        //     a. if vet side has a vet session, it will relay the message to their side
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // 1. Tell vet side we are closing session
        //     a. It will remove us from their map
        // 2. Remove session from map
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Something went wrong! Print the error
    }

}

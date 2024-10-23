package com.coms309.demo2.config;

import com.coms309.demo2.entity.GlobalChatMessage;
import com.coms309.demo2.repository.GlobalChatMessageRepo;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

import com.coms309.demo2.entity.User;
import com.coms309.demo2.repository.UserRepository;

@ServerEndpoint("/chat/{username}")
@Component
public class GlobalChatServer {
    // cannot autowire static directly (instead we do it by the below method
    private static GlobalChatMessageRepo chatMessageRepo;

    /*
     * Grabs the MessageRepository singleton from the Spring Application
     * Context.  This works because of the @Controller annotation on this
     * class and because the variable is declared as static.
     * There are other ways to set this. However, this approach is
     * easiest.
     */
    @Autowired
    public void setGlobalChatMessageRepo(GlobalChatMessageRepo globalChatMessageRepo) {
        chatMessageRepo = globalChatMessageRepo;  // we are setting the static variable
    }


    // Store all socket sessions and their corresponding usernames
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    // Server-side logger
    private final Logger logger = LoggerFactory.getLogger(GlobalChatServer.class);

    // Static reference to UserRepository
    private static UserRepository userRepository;

    // Method to inject UserRepository from a Spring bean
    public static void setUserRepository(UserRepository repo) {
        userRepository = repo;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        logger.info("[onOpen] " + username);

        // Check if the user exists in the database using the injected repository
        Optional<User> userOptional = userRepository.findByEmail(username);
        if (userOptional.isEmpty()) {
            session.getBasicRemote().sendText("User not registered.");
            session.close();
            return;
        }

        if (usernameSessionMap.containsKey(username)) {
            session.getBasicRemote().sendText("Username already connected.");
            session.close();
        } else {
            sessionUsernameMap.put(session, username);
            usernameSessionMap.put(username, session);

            sendMessageToPArticularUser(username, "Welcome to the chat, " + username);
            broadcast("User " + username + " has joined the chat.");
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        String sender = sessionUsernameMap.get(session);  // Get the sender from session
        logger.info("[onMessage] " + sender + ": " + message);

        if (message.startsWith("@")) {
            String[] split_msg = message.split("\\s+", 2);
            String destUserName = split_msg[0].substring(1); // "@username" -> "username"
            String actualMessage = split_msg.length > 1 ? split_msg[1] : "";

            sendMessageToPArticularUser(destUserName, "[DM from " + sender + "]: " + actualMessage);
            sendMessageToPArticularUser(sender, "[DM to " + destUserName + "]: " + actualMessage);
        } else {
            broadcast(sender + ": " + message);
        }

        // Saving chat history to repository with the current timestamp
        GlobalChatMessage chatMessage = new GlobalChatMessage(sender, message, java.time.LocalDateTime.now());
        chatMessageRepo.save(chatMessage);
    }


    @OnClose
    public void onClose(Session session) throws IOException {
        String username = sessionUsernameMap.get(session);
        logger.info("[onClose] " + username);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
        broadcast(username + " disconnected");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String username = sessionUsernameMap.get(session);
        logger.error("[onError] " + username + ": " + throwable.getMessage());
    }

    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("[DM Exception] " + e.getMessage());
        }
    }

    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("[Broadcast Exception] " + e.getMessage());
            }
        });
    }
}

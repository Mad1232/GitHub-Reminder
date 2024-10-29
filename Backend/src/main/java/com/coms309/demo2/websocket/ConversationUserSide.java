package com.coms309.demo2.websocket;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.coms309.demo2.entity.Conversation;
import com.coms309.demo2.entity.ConversationKey;
import com.coms309.demo2.entity.Message;
import com.coms309.demo2.entity.User;
import com.coms309.demo2.entity.Vet;
import com.coms309.demo2.repository.ConversationRepository;
import com.coms309.demo2.repository.MessageRepository;
import com.coms309.demo2.repository.UserRepository;
import com.coms309.demo2.repository.VetsRepo;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.NonNull;

@ServerEndpoint("/users/{userid}/conversations/{vetid}")
@Controller
public class ConversationUserSide {
    private static MessageRepository messageRepository;
    private static ConversationRepository conversationRepository;
    private static UserRepository userRepository;
    private static VetsRepo vetRepository;

    private static Map<Session, ConversationKey> mySessionToConvo = new Hashtable<>();
    private static Map<ConversationKey, Session> convoToMySession = new Hashtable<>();
    private static Map<ConversationKey, Session> convoToTheirSession = new Hashtable<>();

    public static Optional<Session> link(@NonNull Session theirSession, @NonNull ConversationKey key) {
        Session mySession = convoToMySession.get(key);
        if (mySession != null) {
            convoToTheirSession.put(key, theirSession);
        }
        return Optional.ofNullable(mySession);
    }

    public static void unlink(@NonNull ConversationKey key) {
        convoToTheirSession.remove(key);
    }

    @Autowired
    public void setUpRepositories(MessageRepository messageRepository, ConversationRepository conversationRepository,
            VetsRepo vetRepository, UserRepository userRepository) {
        ConversationUserSide.messageRepository = messageRepository;
        ConversationUserSide.conversationRepository = conversationRepository;
        ConversationUserSide.userRepository = userRepository;
        ConversationUserSide.vetRepository = vetRepository;
    }

    @OnOpen
    public void onOpen(Session mySession, @PathParam("userid") Long userid, @PathParam("vetid") Integer vetid) {
        User user = userRepository.findById(userid).get();
        Vet vet = vetRepository.findById(vetid).get();
        ConversationKey key = new ConversationKey(user, vet);

        mySessionToConvo.put(mySession, key);
        convoToMySession.put(key, mySession);

        // 1. Check for existing conversation in database
        Optional<Conversation> conversationOpt = conversationRepository.findById(key);
        Conversation conversation;
        if (conversationOpt.isPresent()) {
            conversation = conversationOpt.get();
        } else {
            // 1a. Create if it doesn't exist
            Conversation c = new Conversation(key);
            conversation = conversationRepository.save(c);
        }
        // 2. Use the conversation key to check with vet side to find an ongoing
        // conversation
        // 2a. Call a function, make link (give it session and convo key)
        Optional<Session> theirSessionOpt = ConversationVetSide.link(mySession, key);
        // 3. Make a map to store link between our session and the conversation key
        if (theirSessionOpt.isPresent()) {
            Session theirSession = theirSessionOpt.get();
            convoToTheirSession.put(key, theirSession);
        }
        // 4. Send all messages in the conversation
        List<Message> messages = messageRepository.findByConversation(conversation);
        for (Message m : messages) {
            sendToSession(mySession, m);
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // 1. Save message to repository
        Long curTime = System.currentTimeMillis();
        ConversationKey key = mySessionToConvo.get(session);
        Conversation conversation = conversationRepository.findById(key).get();
        Message newMessage = new Message(message, curTime, false, conversation);
        messageRepository.save(newMessage);
        // 2. Relay the message to their side
        Session theirSession = convoToTheirSession.get(key);
        if (theirSession != null) {
            sendToSession(theirSession, newMessage);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        ConversationKey key = mySessionToConvo.get(session);
        // 1. Tell vet side we are closing session
        ConversationVetSide.unlink(key);
        // 2. Remove session from map
        mySessionToConvo.remove(session);
        convoToMySession.remove(key);
        convoToTheirSession.remove(key);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Something went wrong! Print the error
        throwable.printStackTrace();
    }

    public static void sendToSession(Session session, Message message) {
        try {
            session.getBasicRemote().sendText(
                    message.getTime() + " " + (message.isVetOrUser() ? "vet" : "user") + " " + message.getContent());
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

}

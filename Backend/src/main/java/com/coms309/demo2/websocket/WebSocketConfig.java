package com.coms309.demo2.websocket;

import com.coms309.demo2.repository.GlobalChatMessageRepo;
import com.coms309.demo2.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *
 * What happens here is that the serverendpoint -- in this case it is
 * the /chat endpoint handler is registered with SPRING
 * so that requests to ws:// will be honored.
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Bean
    public GlobalChatServer globalChatServer(GlobalChatMessageRepo chatMessageRepo, UserRepository userRepository) {
        GlobalChatServer chatServer = new GlobalChatServer();
        chatServer.setGlobalChatMessageRepo(chatMessageRepo);
        chatServer.setUserRepository(userRepository);
        return chatServer;
    }
}
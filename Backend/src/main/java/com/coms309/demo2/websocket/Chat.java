package com.coms309.demo2.websocket;

import org.springframework.stereotype.Component;

import jakarta.websocket.OnOpen;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/{user}/{vet}")
@Component
public class Chat {
    /**
     * This method is called when a new WebSocket connection is established.
     *
     * @param session represents the WebSocket session for the connected user.
     * @param username username specified in path parameter.
     */
    //@OnOpen
   // public void onOpen(Session session, String message)
}

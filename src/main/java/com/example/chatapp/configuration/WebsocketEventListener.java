package com.example.chatapp.configuration;

import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@AllArgsConstructor
public class WebsocketEventListener {
    private final UserRepository myUserRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void onConnect(SessionConnectedEvent event) {
        myUserRepository.increaseUserCount(event.getUser().getName());
        messagingTemplate
                .convertAndSend("/topic/user-number", myUserRepository.getUserCount());
        messagingTemplate
                .convertAndSend("/topic/user-list", myUserRepository.getOnlineUsers());
    }

    @EventListener
    public void printUserEnter(SessionConnectedEvent event) {
        System.out.println("New user has joined");
        System.out.println(event.toString());
    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        myUserRepository.decreaseUserCount(event.getUser().getName());
        messagingTemplate.convertAndSend("/topic/user-number", myUserRepository.getUserCount());
        messagingTemplate.convertAndSend("/topic/user-list", myUserRepository.getOnlineUsers());

    }

}

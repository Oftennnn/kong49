package com.example.chatapp.controller;

import lombok.RequiredArgsConstructor;
import com.example.chatapp.dto.CreateMessage;
import com.example.chatapp.dto.EditMessage;
import com.example.chatapp.dto.UserTyping;
import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.repository.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WebsocketController {

    private final MessageRepository myMessageRepository;
    private final UserRepository myUserRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    // /app/chat/message
    @MessageMapping("/chat/message")
    public void sendMessage(@Payload CreateMessage createMessage, Principal principal) {
        boolean isOperationSuccess = myMessageRepository
                .sendMessage(createMessage.getUserID(), createMessage.getMessage());
        if(isOperationSuccess) {
            messagingTemplate
                    .convertAndSend("/topic/messages",
                            myMessageRepository.getMessages());
            //broadcast new message to all users
        }else{
            System.out.println("Something went wrong for uuid: " + principal.getName());
            messagingTemplate.
                    convertAndSendToUser(principal.getName(),"/queue/errors","Fail to send Message");
            //unicast to user that your operation failed.
        }
        // broadcast to /topic/messages
    }
    // /app/chat/delete-message
    @MessageMapping("/chat/delete-message")
    public void deleteMessage(@Payload EditMessage deleteMessage) {
        boolean isOperationSuccess = myMessageRepository.softDeleteMessage(deleteMessage.getUserID(), deleteMessage.getMessageID());
        if(isOperationSuccess) {
            messagingTemplate.convertAndSend("/topic/messages", myMessageRepository.getMessages());
        }
        else {
            messagingTemplate.convertAndSendToUser(deleteMessage.getUserID().toString(), "queue/errors", "Failed to delete message");
        }
    }

    // /app/chat/edit-message
    @MessageMapping("/chat/edit-message")
    public void editMessage(@Payload EditMessage editMessage) {
        boolean isOperationSuccess = myMessageRepository.editMessage(editMessage.getMessageID(), editMessage.getUserID(), editMessage.getNewMessage());
        if(isOperationSuccess) {
            messagingTemplate.convertAndSend("/topic/messages", myMessageRepository.getMessages());
        }
        else {
            messagingTemplate.convertAndSendToUser(editMessage.getUserID().toString(), "queue/errors", "Failed to edit message");
        }
    }

    // /app/chat/typing
    @MessageMapping("/chat/typing")
    public void userTyping(@Payload UserTyping user) {
        myUserRepository.setUserTyping(user.getUserID(), user.isTyping());
        messagingTemplate.convertAndSend("/topic/typing", myUserRepository.getTypingUsers());
    }
}
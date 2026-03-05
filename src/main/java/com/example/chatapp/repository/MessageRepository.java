package com.example.chatapp.repository;

import com.example.chatapp.model.MessageModel;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {

    List<MessageModel> getMessages();

    boolean sendMessage(UUID userID, String message);

    boolean deleteMessage(UUID userID, UUID messageID);
    boolean softDeleteMessage(UUID userID, UUID messageID);

    MessageModel getMessage(UUID messageID);

    List<MessageModel> searchMessages(String filter);
    boolean editMessage(UUID userID, UUID messageID, String newMessage);

}
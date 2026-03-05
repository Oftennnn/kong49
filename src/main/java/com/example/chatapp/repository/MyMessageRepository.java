package com.example.chatapp.repository;

import lombok.RequiredArgsConstructor;
import com.example.chatapp.model.MessageModel;
import com.example.chatapp.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MyMessageRepository implements MessageRepository {

    private final List<MessageModel> messages = new ArrayList<>();
    private final UserRepository userRepository;

    @Override
    public List<MessageModel> getMessages() {
        return messages;
    }

    @Override
    public boolean sendMessage(UUID userID, String message) {
        UserModel userModel = userRepository.getUserByID(userID);
        if (userModel == null) return false;

        MessageModel messageModel =
                new MessageModel(UUID.randomUUID(), userModel, message,false);

        messages.add(messageModel);
        return true;
    }

    // แก้เป็น SOFT DELETE
    @Override
    public boolean deleteMessage(UUID userID, UUID messageID) {
        UserModel userModel = userRepository.getUserByID(userID);
        if (userModel == null) return false;

        MessageModel messageModel = getMessage(messageID);
        if (messageModel == null) return false;

        messageModel.setDeleted(true);   // ไม่ลบออกจาก list
        return true;
    }

    @Override
    public boolean softDeleteMessage(UUID userID, UUID messageID) {
        UserModel userModel = userRepository.getUserByID(userID);
        if(userModel == null) return false;
        MessageModel messageModel = getMessage(messageID);
        if(messageModel == null) return false;
        messageModel.setDeleted(true);
        return true;
    }

    @Override
    public MessageModel getMessage(UUID messageID) {
        for (MessageModel messageModel : messages) {
            if (messageModel.getMessageID().equals(messageID)) {
                return messageModel;
            }
        }
        return null;
    }

    //เพิ่ม SEARCH
    @Override
    public List<MessageModel> searchMessages(String filter) {
        if (filter == null || filter.isBlank()) return messages;

        List<MessageModel> result = new ArrayList<>();

        for (MessageModel message : messages) {
            if (!message.isDeleted() &&
                    message.getMessage().toLowerCase()
                            .contains(filter.toLowerCase())) {
                result.add(message);
            }
        }

        return result;
    }

    // เพิ่ม EDIT
    @Override
    public boolean editMessage(UUID messageID, UUID userID, String newmessage) {
        System.out.println("editMessage: "+newmessage);
        MessageModel messageModel = getMessage(messageID);
        if(messageModel == null) return false;
        if(messageModel.getUser().getUserID().equals(userID) && !messageModel.isDeleted()) {
            messageModel.setMessage(newmessage);
            return true;
        }
        return false;
    }
}
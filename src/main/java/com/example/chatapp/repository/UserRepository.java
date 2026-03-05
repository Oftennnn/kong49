package com.example.chatapp.repository;

import com.example.chatapp.model.UserModel;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    UUID addUser(String username);
    UserModel getUserByID(UUID id);
    int getUserCount();
    void increaseUserCount(String uuid);
    void decreaseUserCount(String uuid);
    UserModel[] getOnlineUsers();
    void setUserTyping(UUID id, boolean typing);
    List<UserModel> getTypingUsers();
}
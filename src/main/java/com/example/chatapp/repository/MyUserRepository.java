package com.example.chatapp.repository;

import com.example.chatapp.model.UserModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Component("MyUserRepository")
public class MyUserRepository implements UserRepository {

    private Map<UUID, UserModel> users = new HashMap<>();
    private int userCount = 0;

    @Override
    public UUID addUser(String username) {
        UserModel userModel = new UserModel(UUID.randomUUID(), username, true, false);
        users.put(userModel.getUserID(), userModel);
        return userModel.getUserID();
    }

    @Override
    public UserModel getUserByID(UUID id) {
        return users.get(id);
    }

    @Override
    public int getUserCount() {
        return userCount;
    }

    @Override
    public void increaseUserCount(String uuid) {
        users.get(UUID.fromString(uuid)).setOnline(true);
        userCount++;
    }

    @Override
    public void decreaseUserCount(String uuid) {
        users.get(UUID.fromString(uuid)).setOnline(false);
        userCount--;
    }

    @Override
    public UserModel[] getOnlineUsers() {
        return users.values().toArray(new UserModel[userCount]);
    }

    @Override
    public void setUserTyping(UUID uuid, boolean typing) {
        UserModel user = users.get(uuid);
        System.out.println(user.getUsername() + "is typing? " + typing);
        if(user != null) {
            user.setTyping(typing);
        }
    }

    @Override
    public List<UserModel> getTypingUsers() {
        List<UserModel> list = new ArrayList<>();
        for(UserModel user : users.values()) {
            if(user.isTyping() && user.isOnline()) {
                list.add(user);
            }
        }
        return list;
    }
}
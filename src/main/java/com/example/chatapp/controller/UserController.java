package com.example.chatapp.controller;

import lombok.RequiredArgsConstructor;
import com.example.chatapp.dto.CreateUser;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(@Qualifier("MyUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public UUID addUser(@RequestBody CreateUser createUser) {
        return userRepository.addUser(createUser.getUsername());
    }

    @GetMapping("/user-number")
    public int getUserNumber(){
        return userRepository.getUserCount();
    }

    @GetMapping("/user-list")
    public UserModel[] getOnlineUsers() {
        return userRepository.getOnlineUsers();
    }
}
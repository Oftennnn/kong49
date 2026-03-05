package com.example.chatapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MessageModel {

    private UUID messageID;
    private UserModel user;
    private String message;
    private boolean deleted;
}
package com.example.chatapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class EditMessage {

    private UUID userID;
    private UUID messageID;
    private String newMessage;
}
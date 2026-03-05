package com.example.chatapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class CreateMessage {

    private UUID userID;
    private String message;

}

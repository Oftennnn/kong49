package com.example.chatapp.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.Message;
import com.example.chatapp.dto.CreateMessage;
import com.example.chatapp.dto.EditMessage;   // เพิ่ม
import com.example.chatapp.model.MessageModel;
import com.example.chatapp.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageRepository messageRepository;

    @GetMapping
    public List<MessageModel> getMessages() {
        return messageRepository.getMessages();
    }


    @PostMapping("/send")
    public boolean sendMessage(@RequestBody CreateMessage message) {
        return messageRepository.sendMessage(
                message.getUserID(),
                message.getMessage()
        );
    }

    // เพิ่ม EDIT
    @PutMapping("/edit")
    public boolean editMessage(@RequestBody EditMessage editMessage) {
        return messageRepository.editMessage(
                editMessage.getUserID(),
                editMessage.getMessageID(),
                editMessage.getNewMessage()
        );
    }

    @DeleteMapping("/{userID}/{messageID}")
    public boolean deleteMessage(
            @PathVariable UUID userID,
            @PathVariable UUID messageID
    ) {
        return messageRepository.deleteMessage(userID, messageID);
    }

    @GetMapping("/search")
    public List<MessageModel> filterMessages(@RequestParam String filter) {
        return messageRepository.searchMessages(filter);
    }
}
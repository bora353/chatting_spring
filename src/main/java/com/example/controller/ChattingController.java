/*
package com.example.controller;

import com.example.service.ChatService;
import com.example.model.ChattingMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChattingController {

    private final ChatService chatService;

    public ChattingController(ChatService chatService){
        this.chatService = chatService;
    }

    @MessageMapping("/chat")
    public void processMessage(ChattingMessage message){
        chatService.sendMessage("/topic/chat",message);
    }
}
*/

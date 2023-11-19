package com.example.service;

import com.example.model.ChattingMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatService(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(String topic, ChattingMessage message){
        // 메시지를 Redis로 전송
        messagingTemplate.convertAndSend(topic, message);
    }

}

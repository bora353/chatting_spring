package com.example.service;

import com.example.model.ChattingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service // Redis 저장 및 조회 Service
public class ChatService {

    @Autowired
    private RedisTemplate<String, ChattingMessage> redisTemplate;
    private final Logger log = LoggerFactory.getLogger(getClass());


    private static final String MESSAGE_KEY = "messages";

    public void saveMessage(ChattingMessage message){
        log.info("ChatService_saveMessage : " + message);

        String roomKey = MESSAGE_KEY + ":" + message.getRoomNo();
        redisTemplate.opsForList().rightPush(roomKey, message);
    }

    public List<ChattingMessage> getMessages() {
        log.info("ChatService_getMessages");

        Long size = redisTemplate.opsForList().size(MESSAGE_KEY);
        if (size != null) {
            return redisTemplate.opsForList().range(MESSAGE_KEY, 0, size - 1);
        }
        return Collections.emptyList();
    }

    public List<ChattingMessage> getMessagesByRoom(String roomNo) {
        log.info("ChatService_getMessagesByRoom : "+ roomNo);
        String roomKey = MESSAGE_KEY + ":" + roomNo;

        Long size = redisTemplate.opsForList().size(roomKey);
        log.info("Room key: {}, Message count: {}", roomKey, size);

        if (size != null) {
            List<ChattingMessage> messages = redisTemplate.opsForList().range(roomKey, 0, size - 1);
            log.info("Messages retrieved: {}", messages);
            return messages;
        }
        log.info("No messages found.");
        return Collections.emptyList();
    }


//    private final SimpMessagingTemplate messagingTemplate;
//
//    public ChatService(SimpMessagingTemplate messagingTemplate){
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    public void sendMessage(String topic, ChattingMessage message){
//        // 메시지를 Redis로 전송
//        messagingTemplate.convertAndSend(topic, message);
//    }

}

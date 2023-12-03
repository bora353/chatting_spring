package com.example.controller;
import com.example.model.ChattingMessage;
import com.example.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequiredArgsConstructor
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatService chatService;

    private final Logger log = LoggerFactory.getLogger(getClass());


    @GetMapping("/api/messages/{roomNo}")
    public List<ChattingMessage> getMessagesByRoom(@PathVariable String roomNo) {
        log.info("Received request to get messages for room: {}", roomNo);

        return chatService.getMessagesFromRedis(roomNo);
    }

    @MessageMapping("/chat/join/{roomNo}")
    public void join(@DestinationVariable String roomNo, @Payload ChattingMessage chatDto) {
        log.info("User '{}' joined the room '{}'", chatDto.getUser(), roomNo);

        chatDto.setMessage(chatDto.getUser() + "님이 입장하셨습니다.");
        chatDto.setUser("");

        chatService.saveMessage(chatDto); // Redis 저장

        // 채팅 받아서 다시 모든 구독자에게 전송
        simpMessagingTemplate.convertAndSend("/sub/chat/join/" + roomNo, chatDto);
    }

    @MessageMapping("/chat/message/{roomNo}")
    public void sendMessage(@DestinationVariable String roomNo, @Payload ChattingMessage chatDto) {
        log.info("User '{}' sent a message in the room '{}': '{}'", chatDto.getUser(), roomNo, chatDto.getMessage());

        chatService.saveMessage(chatDto);

        //if (chatDto.getMessage().startsWith("님이 입장하셨습니다.")) {
            simpMessagingTemplate.convertAndSend("/sub/chat/room/" + roomNo, chatDto);
        //}
    }



}

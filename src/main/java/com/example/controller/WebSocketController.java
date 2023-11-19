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

        return chatService.getMessagesByRoom(roomNo);
    }

    @MessageMapping("/chat/join/{roomNo}")
    public void join(@DestinationVariable String roomNo, @Payload ChattingMessage chatDto) {
        log.info("User '{}' joined the room '{}'", chatDto.getUser(), roomNo);

        chatService.saveMessage(chatDto);

        chatDto.setMessage(chatDto.getUser() + "님이 입장하셨습니다.");
        simpMessagingTemplate.convertAndSend("/sub/chat/join/" + roomNo, chatDto);
    }

    @MessageMapping("/chat/message/{roomNo}")
    public void sendMessage(@DestinationVariable String roomNo, @Payload ChattingMessage chatDto) {
        log.info("User '{}' sent a message in the room '{}': '{}'", chatDto.getUser(), roomNo, chatDto.getMessage());

        chatService.saveMessage(chatDto);

        if (chatDto.getMessage().startsWith("님이 방에 들어왔습니다.")) {
            simpMessagingTemplate.convertAndSend("/sub/chat/room/" + roomNo, chatDto);
        }
    }


//    // 추가적인 SubscribeMapping을 활용할 경우
//    @SubscribeMapping("/chat/room/{id}")
//    public List<ChattingMessage> subscribeToRoom(@DestinationVariable String id) {
//        // 특정 방에 처음 접속할 때 기존 메시지 이력을 가져오는 로직을 작성
//        // 이는 클라이언트가 해당 주제를 구독할 때 호출됩니다.
//        // 예를 들어, ChattingMessage 리스트를 반환하면 클라이언트에서 해당 방의 이전 메시지를 받을 수 있습니다.
//        // 이는 적절한 비즈니스 로직에 맞게 수정하셔야 합니다.
//        return Collections.emptyList(); // 예시로 빈 리스트 반환
//    }

}

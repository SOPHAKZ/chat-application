package com.student.chat.controller;

import com.student.chat.entity.ChatMessage;
import com.student.chat.entity.Message;
import com.student.chat.repository.ChatMessageRepository;
import com.student.chat.service.ChatMessageService;
import com.student.exception.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageRepository chatMessageRepository;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/private-message")
    @SendTo("/chatroom/public")
    public Message recMessage(@Payload Message message){
        try {
            simpMessagingTemplate.convertAndSendToUser(message.getSenderId(),"/queue/messages",message);
            chatMessageService.save(
                    ChatMessage.builder()
                            .chatId(message.getSenderId()+"_"+message.getRecipientId())
                            .senderId(message.getSenderId())
                            .recipientId(message.getRecipientId())
                            .date(message.getDate())
                            .content(message.getContent())
                            .build()
            );
            return message;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<ResponseModel> findChatMessage(@PathVariable String senderId,
                                                         @PathVariable String receiverId){

        List<ChatMessage> messages = chatMessageService.findChatMessages(senderId,receiverId);
        return ResponseEntity.ok()
                .body(
                        ResponseModel.builder()
                                .status(HttpStatus.OK)
                                .success(true)
                                .data(messages)
                                .build()
                );
    }

}

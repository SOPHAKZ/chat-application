package com.student.chat.entity;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {
    private String senderId;
    private String recipientId;
    private String content;
    private String date;
    private Status status;
}
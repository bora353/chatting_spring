package com.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ChattingMessage implements Serializable {

    private String roomNo;
    private String message;
    private String user;
    private Long timeStamp;


    public ChattingMessage(){

    }

    public ChattingMessage(String roomNo, String message, String user, Long timeStamp){
        this.roomNo = roomNo;
        this.user = user;
        this.message = message;
        this.timeStamp = timeStamp;
    }



}

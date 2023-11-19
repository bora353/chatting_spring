package com.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class ChattingMessage implements Serializable {

    private String roomNo;
    private String message;
    private String user;
    private String timeStamp;


    public ChattingMessage(){

    }

    public ChattingMessage(String roomNo, String message, String user, String timeStamp){
        this.roomNo = roomNo;
        this.user = user;
        this.message = message;
        this.timeStamp = timeStamp;
    }



}

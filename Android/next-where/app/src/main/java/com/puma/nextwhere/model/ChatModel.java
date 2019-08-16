package com.puma.nextwhere.model;

/**
 * Created by rajesh on 2/6/17.
 */

public class ChatModel {
    private int messageType;
    private String message, time;

    public ChatModel(int messageType, String message, String time) {
        this.messageType = messageType;
        this.message = message;
        this.time = time;
    }

    public int getMessageType() {
        return messageType;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
}

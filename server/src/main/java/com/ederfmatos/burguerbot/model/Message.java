package com.ederfmatos.burguerbot.model;

import java.time.LocalDateTime;

public class Message {

    private String message;
    private LocalDateTime dateTime;

    public Message(String message) {
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public Message setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }
}

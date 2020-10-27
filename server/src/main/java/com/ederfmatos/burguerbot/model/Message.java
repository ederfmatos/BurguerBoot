package com.ederfmatos.burguerbot.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("message", message)
                .append("dateTime", dateTime)
                .toString();
    }

}

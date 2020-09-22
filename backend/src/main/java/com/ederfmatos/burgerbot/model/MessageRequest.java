package com.ederfmatos.burgerbot.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MessageRequest {

    private String id;
    private String name;
    private String phoneNumber;
    private String message;

    public String getId() {
        return id;
    }

    public MessageRequest setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MessageRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public MessageRequest setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageRequest setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("name", name)
                .append("phoneNumber", phoneNumber)
                .append("message", message)
                .toString();
    }

}

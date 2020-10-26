package com.ederfmatos.burguerbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class MessageRequest {
    private String id;
    private String name;
    private String phoneNumber;
    private String message;

    @Override
    public String toString() {
        return "MessageRequest{" + "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
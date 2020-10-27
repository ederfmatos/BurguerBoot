package com.ederfmatos.burguerbot.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Document
public class Comment {

    @Id
    private final String id;
    private final Customer customer;
    private final String text;
    private final LocalDateTime createdAt;

    public Comment(String id, Customer customer, String text, LocalDateTime createdAt) {
        this.id = id;
        this.customer = customer;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

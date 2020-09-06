package com.ederfmatos.burguerbot.model;

import com.ederfmatos.burguerbot.model.options.Option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Attendance {

    private boolean started;
    private boolean finished;
    private final Customer customer;
    private final List<String> messages;
    private final List<Product> products;
    private long createdAt;
    private Option lastMessage;

    public Attendance(Customer customer) {
        this.messages = new ArrayList<>();
        this.products = new ArrayList<>();
        this.customer = customer;
    }

    public void start() {
        this.createdAt = new Date().getTime();
        this.started = true;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isNotFinished() {
        return !finished;
    }

    public void finish() {

    }

    public Customer getCustomer() {
        return customer;
    }

    public List<String> getMessages() {
        if (messages.isEmpty()) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(messages.subList(1, messages.size()));
    }

    public List<Product> getProducts() {
        return products;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public Product getLastProduct() {
        return products.get(products.size() - 1);
    }

    public void changeLastProduct(Product product) {
        this.products.add(product);
    }

    public boolean hasProducts() {
        return this.products.size() > 0;
    }

    public Option getLastMessage() {
        return lastMessage;
    }

    public Attendance setLastMessage(Option lastMessage) {
        this.lastMessage = lastMessage;
        return this;
    }
}

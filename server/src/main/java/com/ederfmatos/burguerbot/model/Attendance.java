package com.ederfmatos.burguerbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document
public class Attendance {

    @Id
    protected String id;
    protected Customer customer;
    protected List<String> messages = new ArrayList<>();
    protected List<Product> products = new ArrayList<>();
    protected boolean started;
    protected boolean finished;
    protected LocalDateTime createdAt;
    protected LocalDateTime finishedAt;
    private String lastMessage;
    private int indexChildAction = -1;

    public Attendance(Customer customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public Attendance setId(String id) {
        this.id = id;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<String> getMessages() {
        if (this.messages.isEmpty()) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(this.messages.subList(1, this.messages.size()));
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(this.products);
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isFinished() {
        return finished;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Attendance setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public Attendance setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
        return this;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public Attendance setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
        return this;
    }

    public void start() {
        this.createdAt = LocalDateTime.now();
        this.started = true;
    }

    public boolean isNotFinished() {
        return !this.isFinished();
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
        this.products.set(this.products.size() - 1, product);
    }

    public boolean hasProducts() {
        return this.products.size() > 0;
    }

    public BigDecimal getTotalValue() {
        return this.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public int getIndexChildAction() {
        return indexChildAction;
    }

    public Attendance setIndexChildAction(int indexChildAction) {
        this.indexChildAction = indexChildAction;
        return this;
    }

    public Attendance incrementIndexChildAction() {
        this.indexChildAction++;
        return this;
    }

    public void finish() {

    }

}

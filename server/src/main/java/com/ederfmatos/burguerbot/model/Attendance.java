package com.ederfmatos.burguerbot.model;

import com.ederfmatos.burguerbot.utils.BurguerBotUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
    protected List<Message> messages = new ArrayList<>();
    protected List<Product> products = new ArrayList<>();
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

    public List<Message> getMessages() {
        if (this.messages.isEmpty()) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(this.messages);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(this.products);
    }

    public boolean isStarted() {
        return this.createdAt != null;
    }

    public boolean isFinished() {
        return this.finishedAt != null;
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
    }

    public boolean isNotFinished() {
        return !this.isFinished();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addMessage(String message) {
        this.messages.add(new Message(message));
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
                .map(Product::getTotalPrice)
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
        this.finishedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("customer", customer)
                .append("messages", messages)
                .append("products", products)
                .append("createdAt", createdAt)
                .append("finishedAt", finishedAt)
                .append("lastMessage", lastMessage)
                .append("indexChildAction", indexChildAction)
                .toString();
    }

    public long getTimeToPrepare() {
        if (getProducts().size() == 1) {
            return getProducts().get(0).getPreparationTimeInMinutes();
        }

        long time = getProducts().stream()
                .mapToLong(Product::getPreparationTimeInMinutes)
                .sum();

        if (time % 5 != 0) {
            time = BurguerBotUtils.roundUpFive(time);
        }

        return time;
    }
}

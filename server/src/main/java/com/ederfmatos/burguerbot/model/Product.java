package com.ederfmatos.burguerbot.model;

import com.ederfmatos.burguerbot.model.options.Option;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
public class Product extends Option {

    private BigDecimal price;
    private int quantity;
    private String observation;
    private boolean hasObservation;

    public Product() {
    }

    public Product(String value, String name) {
        super(value, name);
    }

    public Product(String value, String name, BigDecimal price) {
        this(value, name);
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getObservation() {
        return observation;
    }

    public Product setObservation(String observation) {
        this.observation = observation;
        return this;
    }

    public boolean hasObservation() {
        return hasObservation;
    }

    public Product setHasObservation(boolean hasObservation) {
        this.hasObservation = hasObservation;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - R$ %s", StringUtils.leftPad(this.getValue(), 2, "0"), this.getName(), this.getPrice().setScale(2).toPlainString());
    }

//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("");
//        sb.append("Pedido: ").append(this.getName());
//        sb.append("Quantidade: ").append(quantity);
//        sb.append("Valor unitário: ").append(price);
//        sb.append("Valor total: ").append(price.multiply(BigDecimal.valueOf(quantity)));
//
//        if (Strings.isNotBlank(observation)) {
//            sb.append("Observação: ").append(observation);
//        }
//
//        return sb.toString();
//    }

}

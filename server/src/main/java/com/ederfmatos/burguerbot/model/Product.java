package com.ederfmatos.burguerbot.model;

import com.ederfmatos.burguerbot.model.options.Option;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
public class Product extends Option {

    private BigDecimal price;
    private int quantity;
    private String observation;

    public Product() {
    }

    public Product(String value, String name, BigDecimal price) {
        super(value, name);
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append("Pedido: ").append(this.getName());
        sb.append("Quantidade: ").append(quantity);
        sb.append("Valor unitário: ").append(price);
        sb.append("Valor total: ").append(price.multiply(BigDecimal.valueOf(quantity)));

        if (Strings.isNotBlank(observation)) {
            sb.append("Observação: ").append(observation);
        }

        return sb.toString();
    }

}

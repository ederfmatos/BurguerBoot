package com.ederfmatos.burguerbot.model;

import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.utils.BurgerBotMessages;
import com.ederfmatos.burguerbot.utils.BurguerBotUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

import static com.ederfmatos.burguerbot.utils.BurguerBotUtils.formatPrice;

@Document
public class Product extends Option {

    private BigDecimal price;
    private int quantity;
    private String observation;
    private String emoji;
    private boolean hasObservation;
    private long preparationTimeInMinutes;

    public Product() {
    }

    public Product(String value, String name) {
        super(value, name);
    }

    public Product(String value, String name, BigDecimal price) {
        this(value, name);
        this.price = price;
    }

    public Product(String value, String name, BigDecimal price, long preparationTimeInMinutes) {
        this(value, name, price);
        this.preparationTimeInMinutes = preparationTimeInMinutes;
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

    public BigDecimal getTotalPrice() {
        return this.price.multiply(BigDecimal.valueOf(this.quantity));
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

    public Product setEmoji(String emoji) {
        this.emoji = emoji;
        return this;
    }

    public long getPreparationTimeInMinutes() {
        if (quantity == 0 || preparationTimeInMinutes == 0) {
            return preparationTimeInMinutes;
        }

        if (quantity == 1) {
            return preparationTimeInMinutes;
        }

        return BurguerBotUtils.roundUpFive((long) (preparationTimeInMinutes *  quantity * 0.625));
    }

    public Product setPreparationTimeInMinutes(long preparationTimeInMinutes) {
        this.preparationTimeInMinutes = preparationTimeInMinutes;
        return this;
    }

    @Override
    public String format() {
        return String.format("%s - R$ %s", super.toString(), formatPrice(this.getPrice()));
    }

    @Override
    public String toString() {
        String name = StringUtils.isNotBlank(emoji) ? emoji + " " + getName() : getName();

        final StringBuilder sb = new StringBuilder("");
        sb.append(name).append("\n");
        sb.append(BurgerBotMessages.from("labels.quantity")).append(": ").append(quantity).append("\n");
        sb.append(BurgerBotMessages.from("labels.unity_value")).append(": ").append(formatPrice(price)).append("\n");
        sb.append(BurgerBotMessages.from("labels.total_value")).append(": ").append(formatPrice(getTotalPrice()));

        if (this.hasObservation()) {
            sb.append("\n").append(BurgerBotMessages.from("labels.observation")).append(": ").append(observation);
        }

        return sb.toString();
    }

}

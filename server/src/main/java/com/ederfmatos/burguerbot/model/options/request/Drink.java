package com.ederfmatos.burguerbot.model.options.request;

import com.ederfmatos.burguerbot.model.enumeration.RequestEnum;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Drink extends Request {

    private String emoji;

    public Drink() {
    }

    public Drink(String value, String name) {
        super(value, name, RequestEnum.DRINK);
    }

    public Drink(String value, String name, String emoji) {
        this(value, name);
        this.emoji = emoji;
    }

    @Override
    public String getEmoji() {
        return emoji;
    }
}

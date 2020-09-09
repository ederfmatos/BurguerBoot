package com.ederfmatos.burguerbot.model.options.request;

import com.ederfmatos.burguerbot.model.enumeration.RequestEnum;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Snack extends Request {

    private String emoji;

    public Snack() {
    }

    public Snack(String value, String name) {
        super(value, name, RequestEnum.SNACK);
    }

    public Snack(String value, String name, String emoji) {
        this(value, name);
        this.emoji = emoji;
    }

    @Override
    public String getEmoji() {
        return emoji;
    }

}

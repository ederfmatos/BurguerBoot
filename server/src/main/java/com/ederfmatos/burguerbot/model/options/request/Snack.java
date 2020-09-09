package com.ederfmatos.burguerbot.model.options.request;

import com.ederfmatos.burguerbot.model.enumeration.RequestEnum;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Snack extends Request {

    public Snack(String value, String name) {
        super(value, name, RequestEnum.SNACK);
    }

}

package com.ederfmatos.burguerbot.model.options;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class ActionOption extends Option {

    public ActionOption() {
    }

    public ActionOption(String value, String name) {
        super(value, name);
    }

    public ActionOption(String value, String name, List<? extends Option> options) {
        super(value, name, options);
    }

}

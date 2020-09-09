package com.ederfmatos.burguerbot.model.enumeration;

import com.ederfmatos.burguerbot.model.options.request.Drink;
import com.ederfmatos.burguerbot.model.options.request.Request;
import com.ederfmatos.burguerbot.model.options.request.Snack;

public enum RequestEnum {

    SNACK(Snack.class),
    DRINK(Drink.class);

    private final Class<? extends Request> classAction;

    RequestEnum(Class<? extends Request> classAction) {
        this.classAction = classAction;
    }

    public Class<? extends Request> getClassAction() {
        return classAction;
    }

}

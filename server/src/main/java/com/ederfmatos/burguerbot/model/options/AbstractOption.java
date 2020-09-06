package com.ederfmatos.burguerbot.model.options;

import com.ederfmatos.burguerbot.handler.BurgerBotSocketHandler.MessageRequest;
import com.ederfmatos.burguerbot.model.Attendance;

public abstract class AbstractOption extends Option {

    public AbstractOption(String value, String name) {
        super(value, name);
    }

    public AbstractOption(String value, String name, Option... options) {
        super(value, name, options);
    }

    public abstract String execute(MessageRequest messageRequest, Attendance attendance);

}

package com.ederfmatos.burguerbot.model.options.request;

import com.ederfmatos.burguerbot.exception.InvalidOptionException;
import com.ederfmatos.burguerbot.model.enumeration.RequestEnum;
import com.ederfmatos.burguerbot.model.options.ActionOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.utils.BurguerBotUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Request extends ActionOption {

    private RequestEnum requestEnum;

    public Request() {
    }

    public Request(String value, String name) {
        super(value, name);
    }

    public Request(String value, String name, RequestEnum requestEnum) {
        this(value, name);
        this.requestEnum = requestEnum;
    }

    public Request(String value, String name, List<? extends Option> options) {
        super(value, name, options);
    }

    public RequestEnum getRequestEnum() {
        return requestEnum;
    }

    public void validateQuantity(String message) {
        if (!BurguerBotUtils.isNumber(message)) {
            throw new InvalidOptionException();
        }

        if (Integer.parseInt(message) <= 0) {
            throw new InvalidOptionException("Desculpe, mas você tem que escolher pelo menos 1!");
        }
    }

}

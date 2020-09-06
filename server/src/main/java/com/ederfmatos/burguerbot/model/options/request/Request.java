package com.ederfmatos.burguerbot.model.options.request;

import com.ederfmatos.burguerbot.exception.InvalidOptionException;
import com.ederfmatos.burguerbot.handler.BurgerBotSocketHandler;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.options.AbstractOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.utils.BurguerBotUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Request extends AbstractOption {

    public Request() {
        super("1", "Realizar pedido");
    }

    public Request(String value, String name) {
        super(value, name);
    }

    @Override
    public String execute(BurgerBotSocketHandler.MessageRequest messageRequest, Attendance attendance) {
        return null;
    }

    public List<Option> getOptions() {
        return Stream.of(
                new Drink(),
                new Snack()
        ).sorted(Comparator.comparing(Option::getValue)).collect(Collectors.toList());
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

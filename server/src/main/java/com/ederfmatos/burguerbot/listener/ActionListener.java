package com.ederfmatos.burguerbot.listener;

import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.service.BotService;

@FunctionalInterface
public interface ActionListener {

    String execute(MessageRequest messageRequest, Attendance attendance, Option option);

    default ActionListener bind(BotService botService) {
        return this;
    }

}

package com.ederfmatos.burguerbot.service;

import com.ederfmatos.burguerbot.handler.BurgerBotSocketHandler.MessageRequest;
import com.ederfmatos.burguerbot.listener.ActionListener;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.options.Option;
import org.springframework.stereotype.Service;

@Service
public class RequestService implements ActionListener {

    private final OptionService optionService;

    public RequestService(OptionService optionService) {
        this.optionService = optionService;
    }

    @Override
    public String execute(MessageRequest messageRequest, Attendance attendance, Option option) {
        return "null";
    }

}

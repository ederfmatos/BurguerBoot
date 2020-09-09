package com.ederfmatos.burguerbot.listener;

import com.ederfmatos.burguerbot.handler.BurgerBotSocketHandler;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.options.Option;

public interface ActionListener {

    String execute(BurgerBotSocketHandler.MessageRequest messageRequest, Attendance attendance, Option option);

}

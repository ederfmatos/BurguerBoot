package com.ederfmatos.burguerbot.service;

import com.ederfmatos.burguerbot.handler.BurgerBotSocketHandler.MessageRequest;
import com.ederfmatos.burguerbot.listener.ActionListener;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.options.Option;
import org.springframework.stereotype.Service;

@Service
public class FinishAttendanceService implements ActionListener {

    @Override
    public String execute(MessageRequest messageRequest, Attendance attendance, Option option) {
        attendance.finish();
        return "Finalizando atendimento \uD83D\uDD96\uD83C\uDF74";
    }

}

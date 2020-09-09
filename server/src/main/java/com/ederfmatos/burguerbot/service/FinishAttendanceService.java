package com.ederfmatos.burguerbot.service;

import com.ederfmatos.burguerbot.listener.ActionExecutable;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.options.Option;
import org.springframework.stereotype.Service;

@Service
public class FinishAttendanceService implements ActionExecutable {

    @Override
    public String execute(MessageRequest messageRequest, Attendance attendance, Option option) {
        attendance.finish();
        return "Finalizando atendimento \uD83D\uDD96\uD83C\uDF74";
    }

}

package com.ederfmatos.burguerbot.model.options;

import com.ederfmatos.burguerbot.handler.BurgerBotSocketHandler;
import com.ederfmatos.burguerbot.model.Attendance;
import lombok.Getter;

@Getter
public class FinishAttendanceOption extends AbstractOption {

    public FinishAttendanceOption() {
        super("3", "Finalizar atendimento");
    }

    @Override
    public String execute(BurgerBotSocketHandler.MessageRequest messageRequest, Attendance attendance) {
        attendance.finish();
        return "Finalizando atendimento \uD83D\uDD96\uD83C\uDF74";
    }

}

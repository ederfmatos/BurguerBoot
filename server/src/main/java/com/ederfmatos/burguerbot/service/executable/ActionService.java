package com.ederfmatos.burguerbot.service.executable;

import com.ederfmatos.burguerbot.listener.ActionExecutable;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.options.ActionOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.service.BotService;
import com.ederfmatos.burguerbot.service.FinishAttendanceService;
import com.ederfmatos.burguerbot.service.OptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class ActionService implements ActionExecutable {

    protected final OptionService optionService;
    protected final FinishAttendanceService finishAttendanceService;
    protected BotService botService;

    public ActionService(OptionService optionService, FinishAttendanceService finishAttendanceService) {
        this.optionService = optionService;
        this.finishAttendanceService = finishAttendanceService;
    }

    protected abstract List<ActionExecutable> getListeners();

    @Override
    public ActionExecutable configure(BotService botService) {
        this.botService = botService;
        return this;
    }

    @Override
    public String execute(MessageRequest messageRequest, Attendance attendance, Option option) {
        attendance.incrementIndexChildAction();
        return getListeners().get(attendance.getIndexChildAction()).execute(messageRequest, attendance, option);
    }

    protected String finishAttendance(MessageRequest messageRequest, Attendance attendance, Option option) {
        return this.finishAttendanceService
                .configure(botService)
                .execute(messageRequest, attendance, option);
    }

    @Override
    public boolean isInstanceOf(ActionOption actionOption) {
        return true;
    }

}

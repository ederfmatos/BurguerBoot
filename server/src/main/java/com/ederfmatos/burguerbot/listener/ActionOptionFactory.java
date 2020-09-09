package com.ederfmatos.burguerbot.listener;

import com.ederfmatos.burguerbot.model.options.FinishAttendanceOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.model.options.request.Request;
import com.ederfmatos.burguerbot.service.FinishAttendanceService;
import com.ederfmatos.burguerbot.service.RequestService;
import org.springframework.stereotype.Component;

@Component
public class ActionOptionFactory {

    private final RequestService requestService;
    private final FinishAttendanceService finishAttendanceService;

    public ActionOptionFactory(RequestService requestService, FinishAttendanceService finishAttendanceService) {
        this.requestService = requestService;
        this.finishAttendanceService = finishAttendanceService;
    }

    public ActionListener getInstance(Option option) {
        if (option instanceof Request) {
            return requestService;
        }

        if (option instanceof FinishAttendanceOption) {
            return finishAttendanceService;
        }

        return null;
    }

}

package com.ederfmatos.burguerbot.service.executable;

import com.ederfmatos.burguerbot.listener.ActionExecutable;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.options.ActionOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.model.options.request.Drink;
import com.ederfmatos.burguerbot.model.options.request.Portion;
import com.ederfmatos.burguerbot.service.FinishAttendanceService;
import com.ederfmatos.burguerbot.service.OptionService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PortionService extends RequestService {

    private final List<ActionExecutable> listeners;

    public PortionService(OptionService optionService, FinishAttendanceService finishAttendanceService) {
        super(optionService, finishAttendanceService);

        this.listeners = Arrays.asList(
                this::getQuantity,
                this::chooseOptionsFinish,
                this::handleFinishOptions
        );
    }

    @Override
    protected List<ActionExecutable> getListeners() {
        return this.listeners;
    }

    @Override
    protected SelectableOption getFirstItemFinishOption() {
        return new SelectableOption("1", "Pedir outra porção", this::makeOtherPortion);
    }

    @Override
    protected SelectableOption getSecondItemFinishOption() {
        return new SelectableOption("2", "Pedir um lanche", this::makeOtherSnack);
    }

    @Override
    protected SelectableOption getThirdItemFinishOption() {
        return new SelectableOption("3", "Pedir uma bebida", this::makeOtherDrink);
    }

    @Override
    protected String chooseOptionsFinish(MessageRequest messageRequest, Attendance attendance, Option option) {
        this.validateQuantity(messageRequest.getMessage());
        attendance.changeLastProduct(attendance.getLastProduct().setQuantity(Integer.parseInt(messageRequest.getMessage())));
        return super.chooseOptionsFinish(messageRequest, attendance, option);
    }

    @Override
    public boolean isInstanceOf(ActionOption actionOption) {
        return actionOption instanceof Portion;
    }
}
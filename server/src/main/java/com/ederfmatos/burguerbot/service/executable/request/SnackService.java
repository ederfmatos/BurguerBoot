package com.ederfmatos.burguerbot.service.executable.request;

import com.ederfmatos.burguerbot.exception.InvalidOptionException;
import com.ederfmatos.burguerbot.listener.ActionExecutable;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.options.ActionOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.model.options.request.Drink;
import com.ederfmatos.burguerbot.model.options.request.Snack;
import com.ederfmatos.burguerbot.service.FinishAttendanceService;
import com.ederfmatos.burguerbot.service.OptionService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SnackService extends RequestService {

    private final List<ActionExecutable> listeners;

    public SnackService(OptionService optionService, FinishAttendanceService finishAttendanceService) {
        super(optionService, finishAttendanceService);

        this.listeners = Arrays.asList(
                this::getQuantity,
                this::getObservation,
                this::handleObservation,
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
        return new SelectableOption("1", "Pedir outro lanche", this::makeOtherSnack);
    }

    protected String makeOtherSnack(MessageRequest messageRequest, Attendance attendance, Option option) {
        attendance
                .setLastMessage(optionService.getFirstOption().getId())
                .setIndexChildAction(-1);

        messageRequest.setMessage("1");
        return this.botService.getResponseFromMessage(messageRequest, attendance);
    }

    private String getObservation(MessageRequest messageRequest, Attendance attendance, Option option) {
        this.validateQuantity(messageRequest.getMessage());
        attendance.changeLastProduct(attendance.getLastProduct().setQuantity(Integer.parseInt(messageRequest.getMessage())));
        return "Ok, você deseja adicionar alguma observação para o lanche?\n\n" +
                "01 - Sim\n" +
                "02 - Não";
    }

    private String handleObservation(MessageRequest messageRequest, Attendance attendance, Option option) {
        if ("1".equals(messageRequest.getMessage())) {
            attendance.changeLastProduct(attendance.getLastProduct().setHasObservation(true));
            return "Ok, qual observação você deseja inserir?";
        }

        if ("2".equals(messageRequest.getMessage())) {
            attendance.incrementIndexChildAction();
            return this.chooseOptionsFinish(messageRequest, attendance, option);
        }

        throw new InvalidOptionException();
    }

    @Override
    protected String chooseOptionsFinish(MessageRequest messageRequest, Attendance attendance, Option option) {
        if (attendance.getLastProduct().hasObservation()) {
            attendance.changeLastProduct(attendance.getLastProduct().setObservation(messageRequest.getMessage()));
        }

        return super.chooseOptionsFinish(messageRequest, attendance, option);
    }

    @Override
    public boolean isInstanceOf(ActionOption actionOption) {
        return actionOption instanceof Snack;
    }

}

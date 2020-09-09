package com.ederfmatos.burguerbot.service.request;

import com.ederfmatos.burguerbot.listener.ActionExecutable;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.service.OptionService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DrinkService extends RequestService {

    private final List<ActionExecutable> listeners;

    public DrinkService(OptionService optionService) {
        super(optionService);

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
        return new SelectableOption("1", "Pedir outra bebida", this::makeOtherDrink);
    }

    protected String makeOtherDrink(MessageRequest messageRequest, Attendance attendance, Option option) {
        attendance
                .setLastMessage(optionService.getFirstOption().getId())
                .setIndexChildAction(-1);

        messageRequest.setMessage("2");
        return this.botService.getResponseFromMessage(messageRequest, attendance);
    }

    @Override
    protected String chooseOptionsFinish(MessageRequest messageRequest, Attendance attendance, Option option) {
        this.validateQuantity(messageRequest.getMessage());
        attendance.changeLastProduct(attendance.getLastProduct().setQuantity(Integer.parseInt(messageRequest.getMessage())));
        return super.chooseOptionsFinish(messageRequest, attendance, option);
    }
}

package com.ederfmatos.burguerbot.service.executable;

import com.ederfmatos.burguerbot.exception.InvalidOptionException;
import com.ederfmatos.burguerbot.listener.ActionExecutable;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.Product;
import com.ederfmatos.burguerbot.model.options.ActionOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.model.options.request.Request;
import com.ederfmatos.burguerbot.service.FinishAttendanceService;
import com.ederfmatos.burguerbot.service.OptionService;
import com.ederfmatos.burguerbot.utils.BurguerBotUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public abstract class RequestService extends ActionService {

    public RequestService(OptionService optionService, FinishAttendanceService finishAttendanceService) {
        super(optionService, finishAttendanceService);
    }

    protected abstract List<ActionExecutable> getListeners();

    @Override
    public String execute(MessageRequest messageRequest, Attendance attendance, Option option) {
        if (attendance.getIndexChildAction() < 0) {
            option = this.optionService.getOptionFromMessage(messageRequest.getMessage(), option.getOptions());
        }

        return super.execute(messageRequest, attendance, option);
    }

    protected String getQuantity(MessageRequest messageRequest, Attendance attendance, Option option) {
        attendance.addProduct((Product) option);

        return String.format("Ok, você selecionou %s, qual a quantidade?", option.getName());
    }

    protected String chooseOptionsFinish(MessageRequest messageRequest, Attendance attendance, Option option) {
        return "Ok, o que mais você deseja? \n\n" + this.optionService.formatOptions(this.getFinishOptions());
    }

    protected String handleFinishOptions(MessageRequest messageRequest, Attendance attendance, Option option) {
        return this.getFinishOptions().stream()
                .filter(opt -> opt.getValue().equals(messageRequest.getMessage()))
                .findFirst()
                .map(opt -> opt.executeAction(messageRequest, attendance, option))
                .orElseThrow(InvalidOptionException::new);
    }

    protected abstract SelectableOption getFirstItemFinishOption();

    protected List<SelectableOption> getFinishOptions() {
        return Arrays.asList(
                getFirstItemFinishOption(),
                new SelectableOption("2", "Fazer outro pedido", this::makeOtherRequest),
                new SelectableOption("3", "Finalizar atendimento", this::finishAttendance)
        );
    }

    protected String makeOtherRequest(MessageRequest messageRequest, Attendance attendance, Option option) {
        attendance
                .setLastMessage(null)
                .setIndexChildAction(-1);

        messageRequest.setMessage("1");
        return this.botService.getResponseFromMessage(messageRequest, attendance);
    }

    protected void validateQuantity(String message) {
        if (!BurguerBotUtils.isNumber(message)) {
            throw new InvalidOptionException("Desculpe, não entendi, qual a quantidade?");
        }

        if (Integer.parseInt(message) <= 0) {
            throw new InvalidOptionException("Desculpe, mas a quantidade mínima é 1!");
        }
    }

    protected static class SelectableOption extends Option {

        private final ActionExecutable actionExecutable;

        public SelectableOption(String value, String name, ActionExecutable actionExecutable) {
            super(value, name);
            this.actionExecutable = actionExecutable;
        }

        public String executeAction(MessageRequest messageRequest, Attendance attendance, Option option) {
            return this.actionExecutable.execute(messageRequest, attendance, option);
        }
    }

    @Override
    public boolean isInstanceOf(ActionOption actionOption) {
        return actionOption instanceof Request;
    }

}

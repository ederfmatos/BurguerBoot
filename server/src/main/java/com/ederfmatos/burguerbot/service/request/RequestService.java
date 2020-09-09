package com.ederfmatos.burguerbot.service.request;

import com.ederfmatos.burguerbot.exception.InvalidOptionException;
import com.ederfmatos.burguerbot.listener.ActionListener;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.Product;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.service.BotService;
import com.ederfmatos.burguerbot.service.OptionService;
import com.ederfmatos.burguerbot.utils.BurguerBotUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public abstract class RequestService implements ActionListener {

    protected final OptionService optionService;
    protected BotService botService;

    public RequestService(OptionService optionService) {
        this.optionService = optionService;
    }

    protected abstract List<ActionListener> getListeners();

    @Override
    public ActionListener bind(BotService botService) {
        this.botService = botService;
        return this;
    }

    @Override
    public String execute(MessageRequest messageRequest, Attendance attendance, Option option) {
        if (attendance.getIndexChildAction() < 0) {
            option = this.optionService.getOptionFromMessage(messageRequest.getMessage(), option.getOptions());
        }

        attendance.incrementIndexChildAction();
        return getListeners().get(attendance.getIndexChildAction()).execute(messageRequest, attendance, option);
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
                new SelectableOption("3", "Finalizar atendimento", null)
        );
    }

    protected String makeOtherRequest(MessageRequest messageRequest, Attendance attendance, Option option) {
        return null;
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

        private final ActionListener actionListener;

        public SelectableOption(String value, String name, ActionListener actionListener) {
            super(value, name);
            this.actionListener = actionListener;
        }

        public String executeAction(MessageRequest messageRequest, Attendance attendance, Option option) {
            return this.actionListener.execute(messageRequest, attendance, option);
        }
    }

}

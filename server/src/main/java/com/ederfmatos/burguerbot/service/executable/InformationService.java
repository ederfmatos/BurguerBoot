package com.ederfmatos.burguerbot.service.executable;

import com.ederfmatos.burguerbot.listener.ActionExecutable;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.enumeration.EmojiEnum;
import com.ederfmatos.burguerbot.model.options.ActionOption;
import com.ederfmatos.burguerbot.model.options.InformationOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.service.FinishAttendanceService;
import com.ederfmatos.burguerbot.service.OptionService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InformationService extends ActionService {

    public InformationService(OptionService optionService, FinishAttendanceService finishAttendanceService) {
        super(optionService, finishAttendanceService);
    }

    @Override
    protected List<ActionExecutable> getListeners() {
        return Arrays.asList(
                this::showInformations,
                this::handleNewOrder
        );
    }

    public String showInformations(MessageRequest messageRequest, Attendance attendance, Option option) {
        return EmojiEnum.BURGER + " Rémy's Burger - Mais sabor para sua vida\n" +
                "\n" + EmojiEnum.WATCH + " Horário de atendimento:\n" +
                "\nSegunda-feira: \t\t\tFechado" +
                "\nTerça à Sexta: \t\t19:00 - 00:00" +
                "\nSábado e feriados: \t\t19:00 - 01:00" +
                "\nDomingos: \t\t\t19:00 - 00:00\n" +
                "\n" + EmojiEnum.ESTABLISHMENT + " Endereço:" +
                "\nRua Nove de Julho, 456, Centro, Guariba\n" +
                "\n" + EmojiEnum.PHONE + " Telefones:" +
                "\n(16) 3251-4669" +
                "\n(16) 99966-5563\n\n" +
                this.getNewOrderMessage();

    }

    @Override
    public boolean isInstanceOf(ActionOption actionOption) {
        return actionOption instanceof InformationOption;
    }

}

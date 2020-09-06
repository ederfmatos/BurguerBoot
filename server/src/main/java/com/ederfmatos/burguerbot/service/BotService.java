package com.ederfmatos.burguerbot.service;

import com.ederfmatos.burguerbot.exception.BurgerBotException;
import com.ederfmatos.burguerbot.exception.InvalidOptionException;
import com.ederfmatos.burguerbot.exception.OptionNotImplementedException;
import com.ederfmatos.burguerbot.handler.BurgerBotSocketHandler.MessageRequest;
import com.ederfmatos.burguerbot.model.options.AbstractOption;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.Customer;
import com.ederfmatos.burguerbot.model.options.FinishAttendanceOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.model.options.request.Request;
import com.ederfmatos.burguerbot.utils.BurguerBotUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BotService {

    public static final List<Attendance> attendances = new ArrayList<>();
    private final List<Option> options = Arrays.asList(
            new Request(),
            new Option("2", "Saber andamento de pedido"),
            new FinishAttendanceOption()
    );

    public String respondMessage(MessageRequest messageRequest) {
        Attendance attendance = getAttendanceByMessage(messageRequest);

        if (!attendance.isStarted()) {
            attendance.start();
            attendance.addMessage(messageRequest.getMessage());
            return this.getFirstMessage(messageRequest);
        }

        attendance.addMessage(messageRequest.getMessage());

        return this.getResponseFromMessage(messageRequest, attendance);
    }

    private Attendance getAttendanceByMessage(MessageRequest messageRequest) {
        return attendances.stream().filter(attendance -> attendance.getCustomer().getId().equals(messageRequest.getId()) && !attendance.isFinished())
                .findFirst()
                .orElseGet(() -> {
                    Attendance attendance = new Attendance(new Customer(messageRequest.getId(), messageRequest.getName(), messageRequest.getPhoneNumber()));
                    attendances.add(attendance);
                    return attendance;
                });
    }

    private String getFirstMessage(MessageRequest messageRequest) {
        return "\uD83C\uDF55" + BurguerBotUtils.getSalutation() + " " + messageRequest.getName() +
                ", sou Linguini, o chatbot do Rémy`s Burger estarei te atendendo agora \uD83C\uDF55" +
                "\nPara começar digite a opção desejada:"
                + "\n\n" + this.formatOptions(options);
    }

    private String formatOptions(List<Option> options) {
        return options.stream()
                .map(option -> String.format("%s - %s", StringUtils.leftPad(String.valueOf(option.getValue()), 2, "0"), option.getName()))
                .collect(Collectors.joining("\n"));
    }

    private String getResponseFromMessage(MessageRequest messageRequest, Attendance attendance) {
        try {
            if (attendance.getLastMessage() == null) {
                Option option = getOptionFromMessage(messageRequest.getMessage(), this.options);
                attendance.setLastMessage(option);

                if (option.hasOptions()) {
                    return "Escolha uma opção:\n\n" + this.formatOptions(option.getOptions());
                }

                if (option instanceof AbstractOption) {
                    return option.castTo(AbstractOption.class).execute(messageRequest, attendance);
                }

                throw new OptionNotImplementedException();
            }

            return "";
        } catch (BurgerBotException exception) {
            exception.showError();
            return exception.getMessage();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return "Ocorreu um erro desconhecido";
        }
    }

    private Option getOptionFromMessage(String message, List<Option> options) {
        return options.stream().filter(opt -> opt.getValue().equals(message))
                .findFirst()
                .orElseThrow(InvalidOptionException::new);
    }

}

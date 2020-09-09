package com.ederfmatos.burguerbot.service;

import com.ederfmatos.burguerbot.exception.BurgerBotException;
import com.ederfmatos.burguerbot.exception.OptionNotImplementedException;
import com.ederfmatos.burguerbot.listener.ActionOptionFactory;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.options.ActionOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.utils.BurguerBotUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotService {

    private final OptionService optionService;
    private final AttendanceService attendanceService;
    private final ActionOptionFactory actionOptionFactory;
    private final List<? extends Option> options;

    public BotService(OptionService optionService, AttendanceService attendanceService, ActionOptionFactory actionOptionFactory) {
        this.optionService = optionService;
        this.attendanceService = attendanceService;
        this.options = optionService.findAll();
        this.actionOptionFactory = actionOptionFactory;
    }

    public String respondMessage(MessageRequest messageRequest) {
        Attendance attendance = attendanceService.findOrCreate(messageRequest);

        try {
            if (!attendance.isStarted()) {
                attendance.start();
                attendance.addMessage(messageRequest.getMessage());
                return "\uD83C\uDF55" + BurguerBotUtils.getSalutation() + " " + messageRequest.getName() +
                        ", sou Linguini, o chatbot do Rémy`s Burger estarei te atendendo agora \uD83C\uDF55" +
                        "\nPara começar digite a opção desejada:"
                        + "\n\n" + this.formatOptions();
            }

            attendance.addMessage(messageRequest.getMessage());
            return this.getResponseFromMessage(messageRequest, attendance);
        } finally {
            this.attendanceService.save(attendance);
        }
    }

    public String formatOptions() {
        return this.optionService.formatOptions(this.options);
    }

    private String getResponse(MessageRequest messageRequest, Attendance attendance, List<? extends Option> options) {
        Option option = this.optionService.getOptionFromMessage(messageRequest.getMessage(), options);
        attendance.setLastMessage(option.getId());

        if (option.hasOptions()) {
            return "Escolha uma opção:\n\n" + this.optionService.formatOptions(option.getOptions());
        }

        if (option instanceof ActionOption) {
            return this.actionOptionFactory.build(option).execute(messageRequest, attendance, option);
        }

        throw new OptionNotImplementedException();
    }

    public String getResponseFromMessage(MessageRequest messageRequest, Attendance attendance) {
        try {
            if (attendance.getLastMessage() == null) {
                return this.getResponse(messageRequest, attendance, this.options);
            }

            Option lastOption = this.optionService.findById(this.options, attendance.getLastMessage());
            if (lastOption instanceof ActionOption) {
                return this.actionOptionFactory.build(lastOption)
                        .bind(this)
                        .execute(messageRequest, attendance, lastOption);
            }

            return this.getResponse(messageRequest, attendance, lastOption.getOptions());
        } catch (BurgerBotException exception) {
            exception.showError();
            return exception.getMessage();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return "Ocorreu um erro desconhecido";
        }
    }


}

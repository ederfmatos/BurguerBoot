package com.ederfmatos.burguerbot.service;

import com.ederfmatos.burguerbot.listener.ActionExecutable;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.Product;
import com.ederfmatos.burguerbot.model.options.ActionOption;
import com.ederfmatos.burguerbot.model.options.FinishAttendanceOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.utils.BurguerBotUtils;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;


@Service
public class FinishAttendanceService implements ActionExecutable {

    @Override
    public String execute(MessageRequest messageRequest, Attendance attendance, Option option) {
        attendance.finish();

        if (attendance.hasProducts()) {
            long timeToPrepare = attendance.getTimeToPrepare();

            String waitTimeMessage = getWaitMessage(timeToPrepare);

            return "Informações do pedido\n\n" +
                    "Iniciado às " + attendance.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
                    "\nFinalizado às " + attendance.getFinishedAt().format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
                    "\n\n" +
                    "Itens:\n\n" +
                    attendance.getProducts().stream()
                            .map(Product::toString)
                            .collect(Collectors.joining("\n\n")) +
                    "\n\n" +
                    "Valor total do pedido: R$ " + BurguerBotUtils.formatPrice(attendance.getTotalValue()) +
                    "\n" + waitTimeMessage;
        }

        return "Finalizando atendimento \uD83D\uDD96\uD83C\uDF74, muito obrigado por entrar em contato com Rémy`s Burger. " + BurguerBotUtils.getSalutation();
    }

    private String getWaitMessage(long time) {
        if (time == 0) {
            return "Olha, já pode vir buscar, o tempo de espera é de 0 minutos, então só separar tudo aqui e te entregar";
        }

        if (time < 15) {
            return "Olha que coisa boa, vai demorar apenas " + time + " minutos. Mas assim que tiver alguma novidade te aviso";
        }

        return "Olha, o tempo de espera é de " + time + " minutos, mas fique tranquilo, estarei entrando em contato se for preciso";
    }

    @Override
    public boolean isInstanceOf(ActionOption actionOption) {
        return actionOption instanceof FinishAttendanceOption;
    }
}

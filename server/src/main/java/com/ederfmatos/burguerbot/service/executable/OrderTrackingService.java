package com.ederfmatos.burguerbot.service.executable;

import com.ederfmatos.burguerbot.exception.InvalidOptionException;
import com.ederfmatos.burguerbot.listener.ActionExecutable;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.enumeration.AttendanceStateEnum;
import com.ederfmatos.burguerbot.model.options.ActionOption;
import com.ederfmatos.burguerbot.model.options.Option;
import com.ederfmatos.burguerbot.model.options.OrderTrackingOption;
import com.ederfmatos.burguerbot.repository.AttendanceRepository;
import com.ederfmatos.burguerbot.service.BotService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderTrackingService implements ActionExecutable {

    private BotService botService;
    private final AttendanceRepository attendanceRepository;

    public OrderTrackingService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public String execute(MessageRequest messageRequest, Attendance attendance, Option option) {
        List<Attendance> attendances = this.attendanceRepository
                .findByCustomerIdAndFinishedAtNotNull(attendance.getCustomer().getId())
                .filter(attendance1 -> !Arrays.asList(AttendanceStateEnum.CANCELED, AttendanceStateEnum.OPENED).contains(attendance1.getState()))
                .collect(Collectors.toList());

        attendance.setLastMessage(null);

        if (attendances.isEmpty()) {
            attendance
                    .setLastMessage(null)
                    .setIndexChildAction(-1);

            messageRequest.setMessage("1");
            return "Desculpe, não existe nenhum pedido pendente para você\n\n" + this.botService.getResponseFromMessage(messageRequest, attendance);
//            throw new InvalidOptionException("Desculpe, não existe nenhum pedido pendente para você");
        }

        if(attendances.size() == 1) {
            return this.handleTrackingForAttendance(attendances.get(0));
        }

        return "Ainda não implementado";
    }

    private String handleTrackingForAttendance(Attendance attendance) {
        return "Xivi";
    }

    @Override
    public ActionExecutable configure(BotService botService) {
        this.botService = botService;
        return this;
    }

    @Override
    public boolean isInstanceOf(ActionOption actionOption) {
        return actionOption instanceof OrderTrackingOption;
    }

}

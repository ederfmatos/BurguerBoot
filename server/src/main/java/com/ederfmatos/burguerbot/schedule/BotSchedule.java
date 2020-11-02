package com.ederfmatos.burguerbot.schedule;

import com.ederfmatos.burguerbot.handler.BurgerBotSocketHandler;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.service.AttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@Slf4j
public class BotSchedule {

    private static final String CRON_LATE_BOT = "0/30 * * * * *";

    private final AttendanceService attendanceService;

    public BotSchedule(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Scheduled(cron = CRON_LATE_BOT)
    public void logAttendances() {
        attendanceService
                .findAttendancesToCancel()
                .filter(attendance -> BurgerBotSocketHandler.send(attendance, String.format("Hey %s, estamos finalizando seu atendimento por falta de interação, todos os pedidos feitos serão desconsiderados!", attendance.getCustomer().getName())))
                .peek(attendance -> log.warn("Atendimento [{}] sendo cancelado por falta de interação", attendance))
                .peek(Attendance::cancel)
                .forEach(attendanceService::save);
    }

}

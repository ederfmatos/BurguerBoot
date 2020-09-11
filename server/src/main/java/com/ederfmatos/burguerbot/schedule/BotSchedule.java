package com.ederfmatos.burguerbot.schedule;

import com.ederfmatos.burguerbot.handler.BurgerBotSocketHandler;
import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.enumeration.AttendanceStateEnum;
import com.ederfmatos.burguerbot.repository.AttendanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@EnableScheduling
@Component
@Slf4j
public class BotSchedule {

    private static final String CRON_LATE_BOT = "0/30 * * * * *";

    private final AttendanceRepository attendanceRepository;

    @Value("${bot.responseWaitLimitInMinutes}")
    private int responseWaitLimitInMinutes;

    public BotSchedule(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Scheduled(cron = CRON_LATE_BOT)
    public void logAttendances() {
        List<Attendance> attendances = attendanceRepository.findByFinishedAtNull();

        final Predicate<Attendance> exceededWaitingTime = attendance ->
                AttendanceStateEnum.CANCELED != attendance.getState()
                        && ((attendance.getMessages().isEmpty() && attendance.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(responseWaitLimitInMinutes)))
                        || attendance.getMessages().get(attendance.getMessages().size() - 1).getDateTime().isBefore(LocalDateTime.now().minusMinutes(responseWaitLimitInMinutes)));

        final List<Attendance> stoppedAttendances = attendances.stream()
                .filter(exceededWaitingTime)
                .peek(attendance -> log.warn("Atendimento [{}] sendo cancelado por falta de interação", attendance))
                .peek(Attendance::cancel)
                .collect(Collectors.toList());

        if (!stoppedAttendances.isEmpty()) {
            log.warn("{} atendimento(s) estão sendo cancelado(s) por falta de interação", stoppedAttendances.size());
            stoppedAttendances.forEach(attendance -> {
                if (BurgerBotSocketHandler.send("Atendimento sendo finalizado por falta de interação, todos os pedidos feitos serão desconsiderados!")) {
                    attendanceRepository.save(attendance);
                }
            });
        }
    }

}

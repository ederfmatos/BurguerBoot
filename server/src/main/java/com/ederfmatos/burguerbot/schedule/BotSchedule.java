package com.ederfmatos.burguerbot.schedule;

import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.service.BotService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@EnableScheduling
@Slf4j
@Component
public class BotSchedule {

    Gson gson = new Gson();

    private static final String CRON_LATE_BOT = "0/30 * * * * *";

    @Scheduled(cron = CRON_LATE_BOT)
    public void logAttendances() {
        log.info(gson.toJson(BotService.attendances.stream().filter(Attendance::isNotFinished).collect(Collectors.toList())));
    }

}

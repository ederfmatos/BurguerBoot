package com.ederfmatos.burguerbot.service;

import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.Customer;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.model.enumeration.AttendanceStateEnum;
import com.ederfmatos.burguerbot.repository.AttendanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
@Slf4j
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Value("${bot.responseWaitLimitInMinutes}")
    private int responseWaitLimitInMinutes;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance create(Attendance attendance) {
        attendance.setId(UUID.randomUUID().toString());
        return attendanceRepository.save(attendance);
    }

    public Attendance findOrCreate(MessageRequest messageRequest) {
        return attendanceRepository.findAll().stream().filter(attendance -> attendance.getCustomer().getId().equals(messageRequest.getId()) && !attendance.isFinished())
                .findFirst()
                .orElseGet(() -> create(new Attendance(new Customer(messageRequest.getId(), messageRequest.getName(), messageRequest.getPhoneNumber()))));
    }

    public void save(Attendance attendance) {
        this.attendanceRepository.save(attendance);
    }

    public Stream<Attendance> findAttendancesToCancel() {
        final Predicate<Attendance> exceededWaitingTime = attendance ->
                (!Arrays.asList(AttendanceStateEnum.CANCELED, AttendanceStateEnum.PREPARING, AttendanceStateEnum.FINISHING).contains(attendance.getState()))
                        && ((attendance.getMessages().isEmpty() && attendance.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(responseWaitLimitInMinutes)))
                        || attendance.getMessages().get(attendance.getMessages().size() - 1).getDateTime().isBefore(LocalDateTime.now().minusMinutes(responseWaitLimitInMinutes)));

        return attendanceRepository.findByFinishedAtNull()
                .filter(exceededWaitingTime);
    }

}

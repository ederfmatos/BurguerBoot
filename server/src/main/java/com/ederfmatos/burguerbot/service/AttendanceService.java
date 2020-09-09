package com.ederfmatos.burguerbot.service;

import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.Customer;
import com.ederfmatos.burguerbot.model.MessageRequest;
import com.ederfmatos.burguerbot.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public Attendance create(Attendance attendance) {
        attendance
                .setId(UUID.randomUUID().toString())
                .setCreatedAt(LocalDateTime.now());

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

}

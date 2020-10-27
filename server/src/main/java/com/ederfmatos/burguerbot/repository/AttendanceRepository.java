package com.ederfmatos.burguerbot.repository;

import com.ederfmatos.burguerbot.model.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {

    Stream<Attendance> findByFinishedAtNull();

}

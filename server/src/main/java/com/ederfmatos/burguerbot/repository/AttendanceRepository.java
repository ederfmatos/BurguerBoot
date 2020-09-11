package com.ederfmatos.burguerbot.repository;

import com.ederfmatos.burguerbot.model.Attendance;
import com.ederfmatos.burguerbot.model.options.Option;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {

    Stream<Attendance> findByFinishedAtNull();

    Stream<Attendance> findByCustomerIdAndFinishedAtNotNull(String customerId);

}

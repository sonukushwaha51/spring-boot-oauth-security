package com.spring.boot.labs.oauth.security.repository;

import com.spring.boot.labs.oauth.security.entity.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, String> {

    List<Appointment> findAllByDoctorIdAndCreatedDate(String doctorId, LocalDate date);

    Optional<Appointment> findByUserId(String userId);


    List<Appointment> findAllByDoctorId(String doctorId);

    List<Appointment> findAllByDoctorIdAndStatus(String doctorId, String status);

    List<Appointment> findAllByCreatedDate(LocalDate date);
}

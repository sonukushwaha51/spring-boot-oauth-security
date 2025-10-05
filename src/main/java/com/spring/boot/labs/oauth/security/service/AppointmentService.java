package com.spring.boot.labs.oauth.security.service;

import com.spring.boot.labs.oauth.security.entity.Appointment;
import com.spring.boot.labs.oauth.security.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PreAuthorize("hasRole('DOCTOR') OR hasRole('ADMIN') OR hasRole('USER')")
    public Appointment getAppointmentByAppointmentId(String appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        return appointment.orElse(null);
    }
    @PreAuthorize("hasRole('ADMIN') OR hasRole('DOCTOR')")
    public List<Appointment> getAllAppointmentsByAppointmentDate(LocalDate date) {
        return appointmentRepository.findAllByCreatedDate(date);
    }

    @PreAuthorize("hasRole('DOCTOR') OR hasRole('ADMIN') || hasRole('USER')")
    public Appointment getAppointmentByUserId(String userId) {
        Optional<Appointment> appointment = appointmentRepository.findByUserId(userId);
        return appointment.orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('DOCTOR')")
    public List<Appointment> getAllAppointmentForDoctorAndDate(String doctorId, LocalDate date) {
        return appointmentRepository.findAllByDoctorIdAndCreatedDate(doctorId, date);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('DOCTOR')")
    public List<Appointment> getAllAppointmentForDoctor(String doctorId) {
        return appointmentRepository.findAllByDoctorId(doctorId);
    }

    public List<Appointment> getAllAppointmentForDoctorByStatus(String doctorId, String status) {
        return appointmentRepository.findAllByDoctorIdAndStatus(doctorId, status);
    }

    public Appointment createOrUpdateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointmentById(String appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

}

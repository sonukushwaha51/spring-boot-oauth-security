package com.spring.boot.labs.oauth.security.controller;

import com.spring.boot.labs.oauth.security.entity.Appointment;
import com.spring.boot.labs.oauth.security.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PutMapping("/save-appointment")
    public ResponseEntity<Appointment> createOrUpdateAppointment(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointmentService.createOrUpdateAppointment(appointment), HttpStatus.CREATED);
    }

    @GetMapping("/appointment-by-date/{date}")
    public ResponseEntity<List<Appointment>> getAllAppointments(@PathVariable LocalDate date) {
        return new ResponseEntity<>(appointmentService.getAllAppointmentsByAppointmentDate(date), HttpStatus.OK);
    }

    @GetMapping("/get-appointment/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable String id) {
        return new ResponseEntity<>(appointmentService.getAppointmentByAppointmentId(id), HttpStatus.OK);
    }

    @GetMapping("/get-appointment-by-user/{id}")
    public ResponseEntity<Appointment> getAppointmentByUserId(@PathVariable String id) {
        return new ResponseEntity<>(appointmentService.getAppointmentByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/get-appointment-by-doctor/{id}")
    public ResponseEntity<List<Appointment>> getAllAppointments(@PathVariable String id) {
        return new ResponseEntity<>(appointmentService.getAllAppointmentForDoctor(id), HttpStatus.OK);
    }
}

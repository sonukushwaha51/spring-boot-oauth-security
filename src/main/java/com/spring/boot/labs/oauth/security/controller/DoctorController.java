package com.spring.boot.labs.oauth.security.controller;

import com.spring.boot.labs.oauth.security.entity.Doctor;
import com.spring.boot.labs.oauth.security.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PutMapping("/update-doctor")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctorService.createOrUpdateDoctor(doctor), HttpStatus.CREATED);
    }

    @GetMapping("/retrieve-doctor/{doctorId}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable String doctorId) {
        return new ResponseEntity<>(doctorService.getDoctorById(doctorId), HttpStatus.OK);
    }

    @GetMapping("/doctors-by-speciality/{specialities}")
    public ResponseEntity<List<Doctor>> getAllDoctorsBySpeciality(@PathVariable String specialities) {
        return new ResponseEntity<>(doctorService.getAllDoctorBySpeciality(specialities), HttpStatus.OK);
    }

    @GetMapping("/all-doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }

    @DeleteMapping("/remove-doctor/{doctorId}")
    public ResponseEntity<Void> removeDoctor(@PathVariable String doctorId) {
        doctorService.removeDoctor(doctorId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

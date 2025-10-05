package com.spring.boot.labs.oauth.security.service;

import com.spring.boot.labs.oauth.security.entity.Doctor;
import com.spring.boot.labs.oauth.security.entity.enumFiles.RoleType;
import com.spring.boot.labs.oauth.security.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('ADMIN') OR hasRole('DOCTOR')")
    public Doctor createOrUpdateDoctor(Doctor doctor) {
        userService.createDoctor(doctor, RoleType.DOCTOR);
        return doctorRepository.save(doctor);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void removeDoctor(String doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('DOCTOR')")
    public List<Doctor> getAllDoctors() {
        return (List<Doctor>) doctorRepository.findAll();
    }

    public List<Doctor> getAllDoctorBySpeciality(String speciality) {
        List<String> specialities = Arrays.stream(speciality.split(",")).toList();
        return doctorRepository.findAllBySpecialityIn(specialities);
    }

    public Doctor getDoctorById(String id) {
        return doctorRepository.findById(id).orElse(null);
    }

}

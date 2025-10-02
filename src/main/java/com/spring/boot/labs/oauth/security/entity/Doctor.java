package com.spring.boot.labs.oauth.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {

    @Column(name = "doctor_id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String doctorId;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    private float experience;

    private String speciality;

}

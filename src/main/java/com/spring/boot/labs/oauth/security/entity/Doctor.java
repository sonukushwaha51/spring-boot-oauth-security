package com.spring.boot.labs.oauth.security.entity;

import com.spring.boot.labs.oauth.security.entity.enumFiles.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {@Index(name = "idx_created_date", columnList = "created_date")})
public class Doctor {

    @Column(name = "doctor_id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JoinColumn(unique = true, name = "user_name")
    private String userName;

    private String password;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    @Builder.Default
    private Date updatedDate = Date.from(Instant.now());

    private float experience;

    @Email
    private String email;

    private String speciality;

    private RoleType role = RoleType.DOCTOR;

}

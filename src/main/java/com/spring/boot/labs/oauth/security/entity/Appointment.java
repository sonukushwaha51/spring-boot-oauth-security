package com.spring.boot.labs.oauth.security.entity;

import com.spring.boot.labs.oauth.security.entity.enumFiles.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {@Index(name = "idx_created_date", columnList = "created_date")})
public class Appointment {

    @Id
    @Column(name = "appointment_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "appointment_date")
    private Date appointmentDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "appointment_updated_at")
    @Builder.Default
    private Date appointmentUpdatedAt = Date.from(Instant.now());

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}

package com.spring.boot.labs.oauth.security.entity;

import ch.qos.logback.core.testUtil.RandomUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @Column(name = "appointment_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String appointmentId;

    @Column(name = "appointment_date")
    private Date appointMentDate;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    private Doctor doctor;

}

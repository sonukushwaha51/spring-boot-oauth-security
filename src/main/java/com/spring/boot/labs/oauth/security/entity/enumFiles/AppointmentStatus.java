package com.spring.boot.labs.oauth.security.entity.enumFiles;

import lombok.Getter;

@Getter
public enum AppointmentStatus {

    CREATED,
    UPDATED,
    CANCELLED,
    COMPLETED;

    private String status;
}

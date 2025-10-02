package com.spring.boot.labs.oauth.security.entity.enumFiles;

import lombok.Getter;

import java.util.Map;
import java.util.Set;

import static com.spring.boot.labs.oauth.security.entity.enumFiles.RoleType.*;

@Getter
public class RoleToAuthorityMapping {

    public static final Map<RoleType, Set<String>> authorityMap = Map.of(
            ADMIN, Set.of("USER.CREATE","USER.READ","USER.DELETE","ADMIN.DELETE","ADMIN.CREATE","ADMIN.READ","DOCTOR.CREATE","DOCTOR.READ","DOCTOR.DELETE"),
            DOCTOR, Set.of("APPOINTMENT.CREATE","APPOINTMENT.DELETE","APPOINTMENT.UPDATE","APPOINTMENT.READ"),
            USER, Set.of("USER.CREATE","USER.READ","USER.DELETE","APPOINTMENT.READ","APPOINTMENT.DELETE")
    );
}

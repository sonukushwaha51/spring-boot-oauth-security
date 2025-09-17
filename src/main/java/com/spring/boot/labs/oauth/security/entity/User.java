package com.spring.boot.labs.oauth.security.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Column(name = "user_name")
    @Id
    private String userName;

    private String password;

    private String role;

    @Column(name = "user_id")
    @Builder.Default
    private String userId = UUID.randomUUID().toString();

    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

}

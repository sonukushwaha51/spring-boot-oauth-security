package com.spring.boot.labs.oauth.security.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.spring.boot.labs.oauth.security.entity.enumFiles.RoleType;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {@Index(name = "idx_provider_id_provider_type", columnList = "provider_id,provider_type"),
                    @Index(name = "idx_created_date", columnList = "created_date")})
public class User implements UserDetails {

    @Column(name = "user_id")
    @Builder.Default
    @Id
    private String id = UUID.randomUUID().toString();

    @JoinColumn(name = "user_name", unique = true)
    private String userName;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "provider_type")
    private String providerType;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    @Builder.Default
    private Date updatedDate = Date.from(Instant.now());

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

}

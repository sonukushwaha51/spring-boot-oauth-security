package com.spring.boot.labs.oauth.security.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(name = "user_id")
    @Builder.Default
    @Id
    private String userId = UUID.randomUUID().toString();

    @JoinColumn(name = "user_name", unique = true)
    private String userName;

    private String password;

    private String role;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority admin = new SimpleGrantedAuthority("ROLE_ADMIN");
        GrantedAuthority user = new SimpleGrantedAuthority("ROLE_USER");
        return List.of(admin, user);
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

}

package com.spring.boot.labs.oauth.security.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.spring.boot.labs.oauth.security.entity.Doctor;
import com.spring.boot.labs.oauth.security.entity.SignupRequest;
import com.spring.boot.labs.oauth.security.entity.enumFiles.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.boot.labs.oauth.security.entity.LoginRequest;
import com.spring.boot.labs.oauth.security.entity.User;
import com.spring.boot.labs.oauth.security.repository.UserRepository;

import static com.spring.boot.labs.oauth.security.entity.enumFiles.RoleToAuthorityMapping.authorityMap;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElse(null);
    }

    public User loadUser(String userIdentifier, String userIdentifierType) {
        if (Objects.equals(userIdentifierType, LoginRequest.EMAIL)) {
            return userRepository.findByEmail(userIdentifier).orElse(null);
        } else {
            return userRepository.findByUserName(userIdentifier).orElse(null);
        }
    }

    public User createCustomer(SignupRequest request, RoleType role) {
        User userDetail =  User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .dateOfBirth(request.getDateOfBirth())
                .createdDate(LocalDate.now())
                .authorities(getAuthoritiesForUser(role))
                .build();
        return userRepository.save(userDetail);
    }

    public void createDoctor(Doctor doctor, RoleType role) {
        User userDetail =  User.builder()
                .userName(doctor.getUserName())
                .email(doctor.getEmail())
                .firstName(doctor.getDoctorName())
                .password(passwordEncoder.encode(doctor.getPassword()))
                .role(role)
                .dateOfBirth(doctor.getDateOfBirth())
                .createdDate(LocalDate.now())
                .authorities(getAuthoritiesForUser(role))
                .build();
        userRepository.save(userDetail);
    }

    private Set<String> getAuthoritiesForUser(RoleType role) {
        Set<String> authorities = new HashSet<>(authorityMap.get(role));
        authorities.add("ROLE_" + role.name());
        return authorities;
    }

}

package com.spring.boot.labs.oauth.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.boot.labs.oauth.security.entity.LoginRequest;
import com.spring.boot.labs.oauth.security.entity.User;
import com.spring.boot.labs.oauth.security.repository.UserRepository;

@Service
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
        return user.isPresent() ? user.get() : null;
    }

    public User loadUser(String userIdentifier, String userIdentifierType) {
        if (userIdentifierType == LoginRequest.EMAIL) {
            return userRepository.findByEmail(userIdentifier).orElse(null);
        } else {
            return userRepository.findByUserName(userIdentifier).orElse(null);
        }
    }

    public User createCustomer(User user) {
        User userDetail = User.builder()
                            .email(user.getEmail())
                            .password(passwordEncoder.encode(user.getPassword()))
                            .userName(user.getUsername())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .role(user.getRole())
                            .build();
        
        return userRepository.save(userDetail);
    }

}

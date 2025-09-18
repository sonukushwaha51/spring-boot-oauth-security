package com.spring.boot.labs.oauth.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Service;

import com.spring.boot.labs.oauth.security.entity.User;
import com.spring.boot.labs.oauth.security.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    AuthenticationManager manager;

    AuthenticationFilter filter;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(username);
        return user.isPresent() ? user.get() : null;
    }

    public void createCustomer(User user) {
        User userDetail = User.builder()
                            .email(user.getEmail())
                            .password(passwordEncoder.encode(user.getPassword()))
                            .userName(user.getUsername())
                            .role(user.getRole())
                            .build();
        
        userRepository.save(userDetail);
    }

}

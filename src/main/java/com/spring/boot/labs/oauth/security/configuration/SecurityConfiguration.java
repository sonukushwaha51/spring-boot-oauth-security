package com.spring.boot.labs.oauth.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("unused")
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(request -> 
            request
            .requestMatchers("/v1/users/customer-signup","/v1/users/home").permitAll()
            .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Implementation of providing UserDetailsService Bean using InMemoryUserDetailsManager
     */

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user1 = User.withUsername("test")
    //                             .password("{noop}test123")
    //                             .roles("USER")
    //                             .build();

    //     UserDetails user2 = User.withUsername("test2")
    //                             .password("{noop}test1234")
    //                             .roles("USER")
    //                             .build();
    //     return new InMemoryUserDetailsManager(user1, user2);

    // }

}

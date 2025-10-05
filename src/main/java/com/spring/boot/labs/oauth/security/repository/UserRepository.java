package com.spring.boot.labs.oauth.security.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.labs.oauth.security.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByUserName(String username);

    Optional<User> findByEmail(String email);

}

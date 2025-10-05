package com.spring.boot.labs.oauth.security.repository;

import com.spring.boot.labs.oauth.security.entity.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, String> {

    List<Doctor> findAllBySpecialityIn(List<String> specialities);

}

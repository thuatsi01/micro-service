package com.example.authservice.repository;

import com.example.authservice.entities.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByPhoneNumber(@Param("phone") String phone);
}

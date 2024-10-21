package com.coms309.demo2.repository;

import com.coms309.demo2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query method to find user by email
    Optional<User> findByEmail(String email);
}

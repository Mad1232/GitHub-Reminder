package com.coms309.cydrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coms309.cydrop.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

package com.coms309.demo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coms309.demo2.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

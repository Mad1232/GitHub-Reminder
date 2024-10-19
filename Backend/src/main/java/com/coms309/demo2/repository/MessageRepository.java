package com.coms309.demo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coms309.demo2.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}

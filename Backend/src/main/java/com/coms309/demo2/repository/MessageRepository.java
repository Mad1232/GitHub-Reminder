package com.coms309.demo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coms309.demo2.entity.Conversation;
import com.coms309.demo2.entity.Message;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversation(Conversation conversation);
}

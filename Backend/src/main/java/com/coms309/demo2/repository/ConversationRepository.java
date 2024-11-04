package com.coms309.demo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coms309.demo2.entity.Conversation;
import com.coms309.demo2.entity.ConversationKey;
import com.coms309.demo2.entity.User;
import com.coms309.demo2.entity.Vet;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, ConversationKey> {
    List<Conversation> findByUser(User user);
    List<Conversation> findByVet(Vet vet);
}

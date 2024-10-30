package com.coms309.demo2.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Entity
@IdClass(ConversationKey.class)
public class Conversation {
    // many conversations to one user
    @ManyToOne(cascade = CascadeType.MERGE)
    @Id
    @Getter
    User user;

    // many conversations to one vet
    @ManyToOne(cascade = CascadeType.MERGE)
    @Id
    @Getter
    Vet vet;

    // One conversation to many messages
    @OneToMany
    @Getter
    List<Message> messages;

    protected Conversation() {}

    public Conversation(ConversationKey key) {
        user = key.user;
        vet = key.vet;
        messages = Collections.emptyList();
    }
}

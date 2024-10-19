package com.coms309.demo2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import java.util.List;

@Entity
@IdClass(ConversationKey.class)
public class Conversation {
    //many conversations to one user
    @ManyToOne
    @Id
    @Getter
    User user;

    //many conversations to one vet
    @ManyToOne
    @Id
    @Getter
    Vet vet;

    //One conversation to many messages
    @OneToMany
    @Getter
    List<Message> messages;
}

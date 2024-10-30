package com.coms309.demo2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Message {
    @Id
    @Getter
    @Setter 
    Long time;

    @Getter
    @Setter
    String content;

    @Getter
    @Setter
    //true = vet, false = user
    boolean vetOrUser;

    //many messages to one conversation
    @ManyToOne
    @Getter
    Conversation conversation;

    protected Message() {}

    public Message(String content, Long time, boolean vetOrUser, Conversation conversation) {
        this.content = content;
        this.time = time;
        this.vetOrUser = vetOrUser;
        this.conversation = conversation;
    }
}

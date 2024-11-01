package com.coms309.demo2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
    private Long time;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    //true = vet, false = user
    private boolean vetOrUser;

    //many messages to one conversation
    @ManyToOne
    @Getter(onMethod = @__(@JsonBackReference("conversation-message")))
    private Conversation conversation;

    protected Message() {}

    public Message(String content, Long time, boolean vetOrUser, Conversation conversation) {
        this.content = content;
        this.time = time;
        this.vetOrUser = vetOrUser;
        this.conversation = conversation;
    }
}

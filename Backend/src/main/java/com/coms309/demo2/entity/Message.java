package com.coms309.demo2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @ManyToOne
    @JoinColumn(nullable = false)
    User user;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    Vet vet;

    @Getter
    @Setter
    boolean vetOrUser;
}

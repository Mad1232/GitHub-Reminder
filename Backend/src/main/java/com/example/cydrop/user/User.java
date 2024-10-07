package com.example.cydrop.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    Long id;

    @Getter
    @Setter
    String username;

    @Getter
    @Setter
    String password;

    @Getter
    @Setter
    String name;

    // TODO: add user type

    // Used by Jakarta
    protected User() {}

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
}

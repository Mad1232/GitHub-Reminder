package com.example.cydrop.user;

import java.util.List;

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

   // @OneToMany
   // private List<Pet> pets;
    
    // TODO: add user type

    // Used by Jakarta
    protected User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

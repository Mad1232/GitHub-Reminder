package com.coms309.demo2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    @Getter
    private Long id;

    @Column(name = "email")
    @Getter
    @Setter
    private String email;

    @Column(name = "password")
    @Getter
    @Setter
    private String password;

    // One-to-many relationship with Pet
    // owner is in pet.java
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private List<Pet> pets;
}

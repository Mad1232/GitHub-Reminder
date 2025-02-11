package com.coms309.demo2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    // One-to-many relationship with Pet
    //owner is in pet.java
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<Pet> pets;

    //One-to-many relationship with conversations with vets
    //user is in conversation.java
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Conversation> conversations;

    // Many-to-Many with Vet (customer-of)
    @ManyToMany(mappedBy = "customers")
    @Getter
    @JsonBackReference
    private List<Vet> vets;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonManagedReference("conversation-user")
    public List<Conversation> getConversations() {
        return conversations;
    }

    //does this user equal this other user
    @Override
    public boolean equals(Object other) {
        if (other instanceof User) {
            return ((User) other).id == this.id;
        }
        return false;
    }

    //override default version
    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}

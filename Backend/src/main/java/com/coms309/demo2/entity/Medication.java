package com.coms309.demo2.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Medication {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY) //had to do this to make it work
    Long id; //changed from id to avoid confusion

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    Integer stock;

    // One-to-many relationship with Pet (one medication can be assigned to many pets)
    @JsonManagedReference("medication-pet")
    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets; // This will hold all pets assigned to the medication

//    @OneToOne
//    @JoinColumn(name = "pet_id", referencedColumnName = "pet_id", nullable = true)
//    @JsonManagedReference
//    private Pet pet2;

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Events> events;


}

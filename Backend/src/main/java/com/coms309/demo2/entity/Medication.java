package com.coms309.demo2.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Medication {
    @Id
    @Getter
    //@GeneratedValue(strategy = GenerationType.IDENTITY) //had to do this to make it work
    Long medication_id; //changed from id to avoid confusion

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    Integer stock;

    // One-to-many relationship with Pet
    @OneToOne(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pet pet;

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

}

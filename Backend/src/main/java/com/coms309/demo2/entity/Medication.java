package com.coms309.demo2.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    // One-to-many relationship with Pet
    @JsonManagedReference
    @OneToOne(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pet pet;

//    @OneToOne
//    @JoinColumn(name = "pet_id", referencedColumnName = "pet_id", nullable = true)
//    @JsonManagedReference
//    private Pet pet2;



    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

}

package com.coms309.demo2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //had to do this to make it work
    @Column(name = "pet_id")
    private int pet_id;

    @Column(name = "pet_name")
    private String pet_name;

    @Column(name = "pet_type")
    private String pet_type;

    @Column(name = "pet_breed")
    private String pet_breed;

    @Column(name = "pet_diagnosis")
    private String pet_diagnosis;

    @Column(name = "pet_age")
    private Integer pet_age;

    @Column(name = "pet_gender")
    private String pet_gender;

//    @Column(name = "vet_recommendation" , nullable = true)
//    private String vet_recommendation;

    // Many-to-one relationship to User (owner)
    @ManyToOne
    @JsonBackReference("pet-owner")
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    //One-to-One relationship to Medications(medicine)
//    @JsonBackReference("medication-pet")
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "medication_id", referencedColumnName = "id", nullable = true)
//    private Medication medication;

    @ManyToOne
    @JsonBackReference("medication-pet")
    @JoinColumn(name = "medication_id", referencedColumnName = "id", nullable = true)
    private Medication medication;

    // Many-to-Many with Vet
    @JsonBackReference("vet-pet")
    @ManyToMany
    @JoinTable(
            name = "pet_vet",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "vet_id")
    )
    private List<Vet> veterinarians;

    // Getters and setters
    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getPet_type() {
        return pet_type;
    }

    public void setPet_type(String pet_type) {
        this.pet_type = pet_type;
    }

    public String getPet_breed() {
        return pet_breed;
    }

    public void setPet_breed(String pet_breed) {
        this.pet_breed = pet_breed;
    }

    public String getPet_diagnosis() {
        return pet_diagnosis;
    }

    public void setPet_diagnosis(String pet_diagnosis) {
        this.pet_diagnosis = pet_diagnosis;
    }

    public Integer getPet_age() {
        return pet_age;
    }

    public void setPet_age(Integer pet_age) {
        this.pet_age = pet_age;
    }

    public String getPet_gender() {
        return pet_gender;
    }

    public void setPet_gender(String pet_gender) {
        this.pet_gender = pet_gender;
    }

    // for getting the users(owners)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Medication getMedication(){
        return medication;
    }

    public void setMedication(Medication medication){
        this.medication = medication;
    }

    // Getter and Setter for Veterinarians
    public List<Vet> getVeterinarians() {
        return veterinarians;
    }

    public void setVeterinarians(List<Vet> veterinarians) {
        this.veterinarians = veterinarians;
    }

    //public String getVet_recommendation() {return vet_recommendation;}

    //public void setVet_recommendation(String vet_recommendation) {this.vet_recommendation = vet_recommendation;}
}



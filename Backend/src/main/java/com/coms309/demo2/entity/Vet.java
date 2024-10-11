package com.coms309.demo2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Vet")
public class Vet {

    @Id
    @Column(name = "vet_id")
    private int vet_id;

    @Column(name = "vet_name")
    private String vet_name;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "vet_email")
    private String vetEmail;

    // Getters and setters
    public int getVet_id() {
        return vet_id;
    }

    public void setVet_id(int vet_id) {  // Fixed setter method
        this.vet_id = vet_id;
    }

    public String getVet_name() {
        return vet_name;
    }

    public void setVet_name(String vet_name) {
        this.vet_name = vet_name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getVetEmail() {return vetEmail;}

    public void setVetEmail(String vetEmail) {this.vetEmail = vetEmail;}
}

package com.coms309.demo2.entity;

import java.util.List;
import jakarta.persistence.*;

import java.util.List;

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

    @Column(name = "license_num")
    private String licenseNum;

    @Column(name = "clinic_address")
    private String clinicAddress;

    @Column(name = "phone")
    private String phone;

    //One-to-many relationship with conversations with users
    //vet is in conversation.java
    @OneToMany(mappedBy = "vet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversation> conversations;
    // Many-to-Many with Pet
    @ManyToMany(mappedBy = "veterinarians")
    private List<Pet> pets;

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

    public String getLicenseNum() {return licenseNum;}

    public void setLicenseNum(String licenseNum) {this.licenseNum = licenseNum;}

    public String getClinicAddress() {return clinicAddress;}

    public void setClinicAddress(String clinicAddress) {this.clinicAddress = clinicAddress;}

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    // Getter and setter for pets
    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }


}

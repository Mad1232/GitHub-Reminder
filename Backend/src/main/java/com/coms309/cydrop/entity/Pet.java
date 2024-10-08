package com.coms309.cydrop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Pet")
public class Pet {

    @Id
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
}
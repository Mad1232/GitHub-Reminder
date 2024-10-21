package com.coms309.demo2.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Medication {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    Integer stock;
}

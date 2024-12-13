package com.coms309.demo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coms309.demo2.entity.Medication;

import java.util.Optional;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Optional<Medication> findByName(String name); // Method to find medication by name

}

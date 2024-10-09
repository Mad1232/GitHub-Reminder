package com.coms309.demo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coms309.demo2.entity.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    
}

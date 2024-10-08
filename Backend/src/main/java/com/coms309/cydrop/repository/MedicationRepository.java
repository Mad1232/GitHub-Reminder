package com.coms309.cydrop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coms309.cydrop.entity.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    
}

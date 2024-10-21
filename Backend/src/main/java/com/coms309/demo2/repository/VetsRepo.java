package com.coms309.demo2.repository;

import com.coms309.demo2.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VetsRepo extends JpaRepository<Vet, Integer> {
    // Custom method to find Vet by email using the correct naming convention
    Optional<Vet> findByVetEmail(String vetEmail);
}
